package cs3500.pa04.controller;

import static cs3500.pa04.model.utils.Utils.coordinateDirection;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.EmptyArgs;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.MessageJsonEmpy;
import cs3500.pa04.json.MessageJsonFleet;
import cs3500.pa04.json.MessageJsonVolley;
import cs3500.pa04.json.objects.CoordJson;
import cs3500.pa04.json.objects.FleetJson;
import cs3500.pa04.json.objects.ShipJson;
import cs3500.pa04.json.objects.VolleyJson;
import cs3500.pa04.json.request.end.EndGame;
import cs3500.pa04.json.request.end.EndGameResponse;
import cs3500.pa04.json.request.setup.FleetSpec;
import cs3500.pa04.json.request.setup.SetUpRequestArguments;
import cs3500.pa04.json.response.JoinResponse;
import cs3500.pa04.json.response.JoinResponseArguments;
import cs3500.pa04.model.player.BotPlayer;
import cs3500.pa04.model.player.MediumBotPlayer;
import cs3500.pa04.model.player.Player;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;
import cs3500.pa04.model.utils.Coord;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that manages the communication between an AI player and the game server,
 * parsing incoming messages and delegating actions based on their types.
 */
public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private Player aiPlayer;
  private final ObjectMapper mapper = new ObjectMapper();
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");
  int x;
  int y;

  /**
   * Initializes a new instance of the ProxyController class, setting up the
   * input and output streams for the server connection.
   *
   * @param server the socket connected to the game server
   * @throws RuntimeException if an I/O error occurs when creating the input
   *                          or output streams
   */
  public ProxyController(Socket server) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.x = x;
    this.y = y;
  }

  /**
   * Listens for incoming messages from the server and delegates handling based
   * on message type until the server connection is closed.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      System.out.println("disconnected from server");
    }
  }

  /**
   * Delegates handling of a message based on its name.
   *
   * @param message the incoming message to be processed
   */
  public void delegateMessage(MessageJson message) {
    String name = message.name();

    if ("join".equals(name)) {
      handleJoin();
    } else if ("setup".equals(name)) {
      handleSetup(message);
    } else if ("take-shots".equals(name)) {
      handleTakeShots();
    } else if ("report-damage".equals(name)) {
      handleReportDamage(message);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(message);
    } else if ("end-game".equals(name)) {
      handleWin(message);
    }
  }

  /**
   * Handles a join message by responding with a message containing the
   * player's name and game type.
   */
  public void handleJoin() {
    JoinResponseArguments joinResponseArguments = new JoinResponseArguments(
        "espana", "SINGLE");
    JoinResponse message = new JoinResponse("join", joinResponseArguments);
    JsonNode jsonResponse = JsonUtils.serializeRecord(message);
    this.out.println(jsonResponse);
  }

  /**
   * Handles a setup message by setting up the AI player's board and fleet
   * based on the message parameters and responding with a message containing
   * the fleet layout.
   *
   * @param message the incoming setup message
   */
  public void handleSetup(MessageJson message) {
    SetUpRequestArguments requestArgs = this.mapper.convertValue(
        message.args(), SetUpRequestArguments.class);
    final int y = requestArgs.height();
    final int x = requestArgs.width();
    FleetSpec specs = this.mapper.convertValue(requestArgs.specs(), FleetSpec.class);
    int carrier = specs.cnum();
    int submarine = specs.snum();
    int battleship = specs.bnum();
    int destroyer = specs.dnum();
    HashMap<ShipType, Integer> ships = new HashMap<>();
    ships.put(ShipType.Carrier, carrier);
    ships.put(ShipType.Submarine, submarine);
    ships.put(ShipType.BattleShip, battleship);
    ships.put(ShipType.Destoyer, destroyer);
    this.aiPlayer = new MediumBotPlayer(x, y, ships);
    List<Ship> fleet = this.aiPlayer.getFleet();
    FleetJson fleetJson = new FleetJson(new ArrayList<>());
    for (Ship ship : fleet) {
      String direction = coordinateDirection(ship.getCoordinates());
      CoordJson cord = new CoordJson(ship.getCoordinates().get(0).getXcord(),
          ship.getCoordinates().get(0).getYcord());
      ShipJson currentJsonShip = new ShipJson(cord, ship.getSize(), direction);
      fleetJson.ships().add(currentJsonShip);
      System.out.println(ship);
    }
    MessageJsonFleet response = new MessageJsonFleet("setup", fleetJson);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Handles a take-shots message by having the AI player take shots and
   * responding with a message containing the coordinates of the shots.
   */
  public void handleTakeShots() {
    List<Coord> shots = this.aiPlayer.takeShots();
    List<CoordJson> shotJsons = shots.stream()
        .map(shot -> new CoordJson(shot.getX(), shot.getY()))
        .collect(Collectors.toList());

    VolleyJson responseArgs = new VolleyJson(shotJsons);

    MessageJsonVolley response = new MessageJsonVolley("take-shots", responseArgs);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Handles a report-damage message by updating the AI player's state based on
   * the received damage report and responding with a message containing the
   * coordinates of the shots that resulted in hits.
   *
   * @param message the incoming report-damage message
   */
  public void handleReportDamage(MessageJson message) {
    MessageJsonVolley request = this.mapper.convertValue(message, MessageJsonVolley.class);
    VolleyJson requestArguments = request.args();

    // Convert VolleyJson to List<CoordJson>
    List<CoordJson> coordJsons = requestArguments.coords();

    // Convert CoordJson to Coord
    List<Coord> shotsTaken = coordJsons.stream()
        .map(coordJson -> new Coord(coordJson.x(), coordJson.y()))
        .collect(Collectors.toList());

    List<Coord> shotsThatHit = this.aiPlayer.reportDamage(shotsTaken);

    // Convert shotsThatHit into a VolleyJson
    List<CoordJson> hitsJson = shotsThatHit.stream()
        .map(shot -> new CoordJson(shot.getXcord(), shot.getYcord()))
        .collect(Collectors.toList());

    VolleyJson responseArguments = new VolleyJson(hitsJson);

    MessageJsonVolley response = new MessageJsonVolley("report-damage", responseArguments);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Handles a successful-hits message by updating the AI player's state based
   * on the successful hits reported and responding with a void message.
   *
   * @param message the incoming successful-hits message
   */
  public void handleSuccessfulHits(MessageJson message) {
    MessageJsonVolley request = this.mapper.convertValue(message, MessageJsonVolley.class);
    VolleyJson requestArguments = request.args();

    // Convert VolleyJson to List<CoordJson>
    List<CoordJson> coordJsons = requestArguments.coords();

    // Convert  CoordJson to Coord
    List<Coord> shotsThatHitEnemy = coordJsons.stream()
        .map(coordJson -> new Coord(coordJson.x(), coordJson.y()))
        .collect(Collectors.toList());

    this.aiPlayer.successfulHits(shotsThatHitEnemy);
    MessageJsonEmpy response = new MessageJsonEmpy("successful-hits", new EmptyArgs()
    );
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }



  /**
   * Handles an end-game message by updating the AI player's state based on
   * the game result and responding with a void message.
   *
   * @param message the incoming end-game message
   */
  public void handleWin(MessageJson message) {
    EndGame request = this.mapper.convertValue(message, EndGame.class);
    EndGameResponse args = request.args();
    this.aiPlayer.endGame(args.result(), args.reason());
    MessageJsonEmpy response = new MessageJsonEmpy("end-game", new EmptyArgs());
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

}