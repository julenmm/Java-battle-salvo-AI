package cs3500.pa04.model.player;

import cs3500.pa04.model.game.BattleBoard;
import cs3500.pa04.model.game.GameResult;
import cs3500.pa04.model.ship.Battleship;
import cs3500.pa04.model.ship.Carrier;
import cs3500.pa04.model.ship.Destroyer;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;
import cs3500.pa04.model.ship.Submarine;
import cs3500.pa04.model.utils.Coord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class represents a BotPlayer that implements the Player interface.
 * It provides methods to initialize the game board, place ships, take shots,
 * report damage, end the game, and get the current state of the game.
 */
public class MediumBotPlayer implements Player {

  private final BattleBoard myBoard;
  private final BattleBoard enemyBoard;
  private final List<Ship> fleet;
  private final int xlength;
  private final int ylength;
  private final List<Coord> diagonalCords;
  private final List<Coord> diagonalCordsNonRemoved;
  private final ArrayList<ArrayList<Coord>> shotsToBoatSuccessful;
  private final List<Coord> alreadyShot;

  /**
   * Constructs a new BotPlayer with a specified board size and fleet configuration.
   * Initializes the bot's own board and the enemy's board.
   *
   * @param x         the width of the game board.
   * @param y         the height of the game board.
   * @param shipSpecs a map where the key is a Ship object and the value is the count of that ship
   *                  type.
   */
  public MediumBotPlayer(int x, int y, Map<ShipType, Integer> shipSpecs) {
    this.xlength = x;
    this.ylength = y;
    this.fleet = this.setup(y, x, shipSpecs);
    myBoard = new BattleBoard(x, y, this.fleet);
    enemyBoard = new BattleBoard(x, y);
    this.diagonalCords = this.getCoordsFromDiagonals();
    this.alreadyShot = new ArrayList<>();
    this.shotsToBoatSuccessful = new ArrayList<>();
    diagonalCordsNonRemoved = this.getCoordsFromDiagonals();
  }

  /**
   * Returns the bot's name.
   *
   * @return A string representing the bot's name.
   */
  @Override
  public String name() {
    return "AI Human Destroyer";
  }

  /**
   * Sets up the bot's fleet according to the provided map of ship types and their counts.
   *
   * @param height    the height of the game board.
   * @param width     the width of the game board.
   * @param shipSpecs a map where the key is a Ship object and the value is the count of that ship
   *                  type.
   * @return An ArrayList of Ship objects representing the bot's fleet.
   */
  @Override
  public ArrayList<Ship> setup(int height, int width, Map<ShipType, Integer> shipSpecs) {
    ArrayList<Ship> fleetSelection = new ArrayList<>();
    for (ShipType key : shipSpecs.keySet()) {
      if (key.equals(ShipType.Submarine)) {
        for (int i = 0; i < shipSpecs.get(key); i++) {
          fleetSelection.add(new Submarine());
        }
      } else if (key.equals(ShipType.Carrier)) {
        for (int i = 0; i < shipSpecs.get(key); i++) {
          fleetSelection.add(new Carrier());
        }
      } else if (key.equals(ShipType.BattleShip)) {
        for (int i = 0; i < shipSpecs.get(key); i++) {
          fleetSelection.add(new Battleship());
        }
      } else if (key.equals(ShipType.Destoyer)) {
        for (int i = 0; i < shipSpecs.get(key); i++) {
          fleetSelection.add(new Destroyer());
        }
      }
    }
    return fleetSelection;
  }

  /**
   * Decides which coordinates the bot should shoot at in the current round.
   *
   * @return An ArrayList of Coord objects representing the coordinates to be shot.
   */
  @Override
  public ArrayList<Coord> takeShots() {
    ArrayList<Coord> toShoot = new ArrayList<>();
    for (int i = 0; i < this.fleet.size(); i++) {
      if (this.shotsToBoatSuccessful.size() > i
          && this.shotsToBoatSuccessful.get(i).size() > 0
          && !(this.alreadyShot.contains(this.shotsToBoatSuccessful.get(i).get(0)))) {
        this.addShot(toShoot, this.shotsToBoatSuccessful.get(i).remove(0));
      } else if (this.diagonalCords.size() > 0
          && !this.alreadyShot.contains(this.diagonalCords.get(0))) {
        if (this.shotsToBoatSuccessful.size() > i
            && this.shotsToBoatSuccessful.get(i).size() > 0) {
          this.shotsToBoatSuccessful.get(i).remove(0);
        }
        Coord ss = this.diagonalCords.remove(0);
        addShot(toShoot, ss);
      } else {
        if (this.shotsToBoatSuccessful.size() > i
            && this.shotsToBoatSuccessful.get(i).size() > 0) {
          this.shotsToBoatSuccessful.get(i).remove(0);
        }
        if (this.diagonalCords.size() > 0) {
          this.diagonalCords.remove(0);
        }
        findAndShootSecondaryOptions(toShoot);
      }
    }
    return toShoot;
  }

  /**
   * Helper function to find and shoot secondary options.
   *
   * @param toShoot The list of coordinates to shoot at.
   */
  private void findAndShootSecondaryOptions(ArrayList<Coord> toShoot) {
    boolean found = false;
    for (ArrayList<Coord> a : shotsToBoatSuccessful) {
      for (int m = 0; m < a.size(); m++) {
        if (!this.alreadyShot.contains(a.get(m)) && !found) {
          addShot(toShoot, a.remove(m));
          found = true;
          break;
        }
      }
    }
    if (!found) {
      this.shootAnyRemaindingCoord(toShoot);
    }
  }

  /**
   * Helper function to shoot any remaining coordinate.
   *
   * @param toShoot The list of coordinates to shoot at.
   */
  public void shootAnyRemaindingCoord(ArrayList<Coord> toShoot) {
    for (int x = 0; x < this.xlength; x++) {
      for (int y = 0; y < this.ylength; y++) {
        Coord coord = new Coord(x, y);
        if (!this.alreadyShot.contains(coord)) {
          this.alreadyShot.add(coord);
          toShoot.add(coord);
          return;
        }
      }
    }
  }

  /**
   * Adds a shot to the list of shots and the list of already shot coordinates.
   *
   * @param shotList The list of coordinates to be shot at.
   * @param shot     The coordinate to be shot at.
   */
  public void addShot(List<Coord> shotList, Coord shot) {
    if (!alreadyShot.contains(shot)) {
      shotList.add(shot);
      alreadyShot.add(shot);
    }
  }

  /**
   * Generates a list of coordinates from diagonals.
   *
   * @return A list of coordinates representing the diagonals of the game board.
   */
  public List<Coord> getCoordsFromDiagonals() {
    List<Coord> coords = new ArrayList<>();
    int height = this.xlength;
    int width = this.ylength;
    // Iterate over all diagonals with a step of 3
    for (int diag = 0; diag < width + height - 1; diag += 3) {
      for (int x = 0; x < height; x++) {
        int y = diag - x;
        if (y >= 0 && y < width) {
          coords.add(new Coord(x, y));
        }
      }
    }
    Collections.shuffle(coords);
    return coords;
  }


  /**
   * Reports the damage of opponent's shots on the bot's board.
   *
   * @param opponentShotsOnBoard An ArrayList of Coord objects representing the coordinates of the
   *                             opponent's shots.
   * @return An ArrayList of Coord objects representing the coordinates of successful hits.
   */
  @Override
  public ArrayList<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    ArrayList<Coord> hits = new ArrayList<>();
    ArrayList<Ship> shipsToRemove = new ArrayList<>();
    ArrayList<Coord> opponentShotsOnBoardCopy = new ArrayList<>(opponentShotsOnBoard);
    for (Coord cord : opponentShotsOnBoardCopy) {
      for (Ship s : this.fleet) {
        if (s.shipInCoord(cord)) {
          hits.add(cord);
          s.hitShip(cord);
          if (s.getCoordinates().size() == 0) {
            shipsToRemove.add(s);
          }
        }
      }
    }
    this.fleet.removeAll(shipsToRemove);
    for (Coord cord : opponentShotsOnBoardCopy) {
      this.myBoard.hitMyBoard(cord.getX(), cord.getY());
    }
    return hits;
  }

  /**
   * Reports the bot's successful hits on the opponent's board.
   *
   * @param shotsThatHitOpponentShips An ArrayList of Coord objects representing the coordinates of
   *                                  successful hits.
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord c : shotsThatHitOpponentShips) {
      this.enemyBoard.successfulEnemyHit(c.getX(), c.getY());
    }
    this.mutateVariables(shotsThatHitOpponentShips);
  }

  /**
   * Modifies internal variables relevant to the shooting process based on the given list of
   * successful shots.
   *
   * @param shot The list of coordinates of successful shots.
   */
  private void mutateVariables(List<Coord> shot) {
    int boardSizeX = this.xlength;
    int boardSizeY = this.ylength;
    for (Coord c : shot) {
      if (this.diagonalCordsNonRemoved.contains(c)) {
        ArrayList<Coord> directHitList = new ArrayList<>();
        int x = c.getX();
        int y = c.getY();
        // Up
        if (x - 1 >= 0 && !this.alreadyShot.contains(new Coord(x - 1, y))) {
          directHitList.add(new Coord(x - 1, y));
        }
        if (x + 1 < boardSizeX && !this.alreadyShot.contains(new Coord(x + 1, y))) {
          directHitList.add(new Coord(x + 1, y));
        }
        if (y - 1 >= 0 && !this.alreadyShot.contains(new Coord(x, y - 1))) {
          directHitList.add(new Coord(x, y - 1));
        }
        if (y + 1 < boardSizeY && !this.alreadyShot.contains(new Coord(x, y + 1))) {
          directHitList.add(new Coord(x, y + 1));
        }
        if (x - 2 >= 0 && !this.alreadyShot.contains(new Coord(x - 2, y))) {
          directHitList.add(new Coord(x - 2, y));
        }
        if (x + 2 < boardSizeX && !this.alreadyShot.contains(new Coord(x + 2, y))) {
          directHitList.add(new Coord(x + 2, y));
        }
        if (y - 2 >= 0 && !this.alreadyShot.contains(new Coord(x, y - 2))) {
          directHitList.add(new Coord(x, y - 2));
        }
        if (y + 2 < boardSizeY && !this.alreadyShot.contains(new Coord(x, y + 2))) {
          directHitList.add(new Coord(x, y + 2));
        }
        Collections.shuffle(this.shotsToBoatSuccessful);
        this.shotsToBoatSuccessful.add(directHitList);
      }
    }
  }

  /**
   * Ends the game and reports the result.
   *
   * @param result A GameResult object representing the result of the game.
   * @param reason A string explaining why the game ended.
   */
  @Override
  public void endGame(GameResult result, String reason) {
    System.out.println(result.toString());
    System.out.println(reason);
  }

  /**
   * Returns the bot's current fleet.
   *
   * @return An ArrayList of Ship objects representing the bot's current fleet.
   */
  public List<Ship> getFleet() {
    return this.fleet;
  }

  /**
   * METHOD EXCLUSIVELY USED FOR TESTING
   * Returns the list of coordinates that have been shot at.
   *
   * @return A list of Coord objects that have been shot at.
   */
  public List<Coord> getAlreadyShot() {
    return this.alreadyShot;
  }

  /**
   * METHOD EXCLUSIVELY USED FOR TESTING
   * Returns the current state of the enemy's board.
   *
   * @return A 2D array representing the enemy's board state.
   */
  public String[][] getEnemyBoard() {
    return this.enemyBoard.getBoard();
  }

  public String[][] getMyBoard() {
    return this.myBoard.getBoard();
  }
}
