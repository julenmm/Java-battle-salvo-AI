package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.game.GameResult;
import cs3500.pa04.model.player.BotPlayer;
import cs3500.pa04.model.ship.Battleship;
import cs3500.pa04.model.ship.Carrier;
import cs3500.pa04.model.ship.Destroyer;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.ShipType;
import cs3500.pa04.model.ship.Submarine;
import cs3500.pa04.model.utils.Coord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for the BotPlayer class.
 * It ensures that the functionality the actions of a bot player work appropriately.
 */
public class BotPlayerTest {

  private BotPlayer player2;
  private BotPlayer player3;
  private BotPlayer botPlayer;
  private Map<ShipType, Integer> shipSpecs;
  private BotPlayer botPlayer2;
  private Map<ShipType, Integer> shipSpecs4;
  private Map<ShipType, Integer> shipSpecs2;

  @BeforeEach
  public void setup() {
    shipSpecs2 = new HashMap<>();
    shipSpecs2.put(ShipType.Submarine, 1);
    shipSpecs2.put(ShipType.Carrier, 1);
    shipSpecs2.put(ShipType.BattleShip, 1);
    shipSpecs2.put(ShipType.Destoyer, 1);
    player2 = new BotPlayer(10, 10, shipSpecs2);
    shipSpecs = new HashMap<>();
    player3 = new BotPlayer(6, 6, shipSpecs);
    shipSpecs.put(ShipType.BattleShip, 1);
    botPlayer = new BotPlayer(5, 5, shipSpecs);
    shipSpecs4 = new HashMap<>();
    shipSpecs4.put(ShipType.Submarine, 2);
    botPlayer2 = new BotPlayer(6, 6, shipSpecs);
  }

  @Test
  public void setupTest() {
    HashMap<ShipType, Integer> shipSpecs = new HashMap<>();
    shipSpecs.put(ShipType.Submarine, 2);
    shipSpecs.put(ShipType.Destoyer, 3);
    shipSpecs.put(ShipType.Carrier, 1);
    shipSpecs.put(ShipType.BattleShip, 2);
    BotPlayer player = new BotPlayer(10, 10, shipSpecs);
    List<Ship> fleet = player.getFleet();

    int submarineCount = 0;
    int destroyerCount = 0;
    int carrierCount = 0;
    int battleshipCount = 0;

    for (Ship ship : fleet) {
      if (ship instanceof Submarine) {
        submarineCount++;
      } else if (ship instanceof Destroyer) {
        destroyerCount++;
      } else if (ship instanceof Carrier) {
        carrierCount++;
      } else if (ship instanceof Battleship) {
        battleshipCount++;
      }
    }

    assertEquals(2, submarineCount);
    assertEquals(3, destroyerCount);
    assertEquals(1, carrierCount);
    assertEquals(2, battleshipCount);
  }

  @Test
  public void takeShotsTest() {
    HashMap<ShipType, Integer> shipSpecs = new HashMap<>();
    shipSpecs.put(ShipType.Submarine, 1);
    BotPlayer player = new BotPlayer(10, 10, shipSpecs);

    List<Coord> shots = player.takeShots();
    assertEquals(player.getFleet().size(), shots.size());

    assertTrue(player.getAlreadyShot().containsAll(shots));
  }

  @Test
  public void reportDamageTest() {
    HashMap<ShipType, Integer> shipSpecs = new HashMap<>();
    shipSpecs.put(ShipType.Submarine, 1);

    BotPlayer player = new BotPlayer(10, 10, shipSpecs);
    Ship ship = player.getFleet().get(0);
    List<Coord> cords = new ArrayList<>(ship.getCoordinates());

    List<Coord> hits = player.reportDamage(cords);

    assertEquals(cords, hits);
    assertTrue(player.getFleet().isEmpty());
  }

  @Test
  public void reportDamageTestNoShotsHit() {
    HashMap<ShipType, Integer> shipSpecs = new HashMap<>();
    shipSpecs.put(ShipType.Submarine, 1);
    BotPlayer player = new BotPlayer(10, 10, shipSpecs);
    ArrayList<Coord> shots = new ArrayList<>();
    List<Coord> hits = player.reportDamage(shots);
    assertEquals(shots, hits);
    assertEquals(1, player.getFleet().size());
  }

  @Test
  public void reportDamageTestWhenShotHits1() {
    HashMap<ShipType, Integer> shipSpecs = new HashMap<>();
    shipSpecs.put(ShipType.Submarine, 1);

    BotPlayer player = new BotPlayer(10, 10, shipSpecs);
    Ship ship = player.getFleet().get(0);
    List<Coord> cords = new ArrayList<>(ship.getCoordinates());

    List<Coord> hits = player.reportDamage(cords);

    assertEquals(cords, hits);
    assertTrue(player.getFleet().isEmpty());
  }

  @Test
  public void reportDamageTestWhenShotHit2() {
    HashMap<ShipType, Integer> shipSpecs = new HashMap<>();
    shipSpecs.put(ShipType.Carrier, 1);

    BotPlayer player = new BotPlayer(10, 10, shipSpecs);
    Ship ship = player.getFleet().get(0);
    Coord cord = ship.getCoordinates().get(0);

    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(cord);

    List<Coord> hits = player.reportDamage(shots);

    assertEquals(shots, hits);
    assertEquals(1, player.getFleet().size());
  }


  @Test
  public void successfulHitsTest() {
    HashMap<ShipType, Integer> shipSpecs = new HashMap<>();
    shipSpecs.put(ShipType.Submarine, 1);

    BotPlayer player = new BotPlayer(10, 10, shipSpecs);
    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(new Coord(1, 1));

    player.successfulHits(shots);

    assertEquals("#", player.getEnemyBoard()[1][1]);
  }

  @Test
  public void testEndGame() {
    assertDoesNotThrow(() -> player2.endGame(GameResult.WIN, "You win!"));
    assertDoesNotThrow(() -> player2.endGame(GameResult.LOSE, "You lose!"));
    assertDoesNotThrow(() -> player2.endGame(GameResult.DRAW, "Draw"));
    assertDoesNotThrow(() -> player2.endGame(GameResult.WIN, ""));
    assertDoesNotThrow(() -> player2.endGame(GameResult.LOSE, " "));
    assertDoesNotThrow(() -> player2.endGame(GameResult.DRAW, "1234567890"));
  }


  @Test
  public void testShootAnyRemaindingCoord_unShotCoord() {
    List<Coord> toShoot = new ArrayList<>();
    player2.shootAnyRemaindingCoord((ArrayList<Coord>) toShoot);
    assertEquals(1, toShoot.size()); // a single shot is expected
    assertTrue(player2.getAlreadyShot()
        .contains(toShoot.get(0))); // shot coordinate should be marked as already shot
  }

  @Test
  public void testShootAnyRemaindingCoord_allCoordsShot() {
    for (int x = 0; x < 10; x++) {
      for (int y = 0; y < 10; y++) {
        player2.getAlreadyShot().add(new Coord(x, y));
      }
    }
    List<Coord> toShoot = new ArrayList<>();
    player2.shootAnyRemaindingCoord((ArrayList<Coord>) toShoot);
    assertEquals(0,
        toShoot.size()); // no shots are expected as all coordinates are marked as already shot
  }

  @Test
  public void testFindAndShootSecondaryOptions_whenAlreadyShotIsEmpty() {
    List<Coord> shots = botPlayer.takeShots();

    assertTrue(shots.size() > 0);

    assertEquals(shots.size(), botPlayer.getAlreadyShot().size());
  }

  @Test
  public void testFindAndShootSecondaryOptions_whenAlreadyShotIsNotEmpty() {
    botPlayer.getAlreadyShot().add(new Coord(1, 1));
    List<Coord> cc = new ArrayList<>();
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        cc.add(new Coord(x, y));
      }
    }
    player2.successfulHits(cc);
    List<Coord> shots = botPlayer.takeShots();
    assertTrue(shots.size() > 0);
  }

  @Test
  public void testFindAndShootSecondaryOptions_whenAllCoordsAreAlreadyShot() {
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        botPlayer.getAlreadyShot().add(new Coord(x, y));
      }
    }

    List<Coord> shots = botPlayer.takeShots();

    assertEquals(0, shots.size());
  }

  @Test
  public void testTakeShotsWhenNoPriorHits() {
    List<Coord> shots = player3.takeShots();
    for (Coord shot : shots) {
      assertTrue(player3.getAlreadyShot().contains(shot));
    }
  }

  @Test
  public void testTakeShotsWhenSomePriorHits() {
    List<Coord> shots = player3.takeShots();
    player3.successfulHits(Collections.singletonList(new Coord(0, 0)));
    player3.successfulHits(Collections.singletonList(new Coord(1, 1)));
    for (Coord shot : shots) {
      assertTrue(player3.getAlreadyShot().contains(shot));
    }
  }

  @Test
  public void testTakeShotsWhenNoDiagonalCords() {
    List<Coord> shots = player3.takeShots();
    for (Coord shot : shots) {
      assertTrue(player3.getAlreadyShot().contains(shot));
    }
  }

  @Test
  public void testTakeShotsWhenNoShipRemainingInFleet() {
    player3.getFleet().clear();
    List<Coord> shots = player3.takeShots();
    assertTrue(shots.isEmpty());
  }


  @Test
  public void testTakeShotsConstantHits() {
    List<Coord> shotsRecorded = new ArrayList<>();
    for (int i = 0; i < 30; i++) {
      List<Coord> shots = player2.takeShots();
      shotsRecorded.addAll(shots);
      player2.successfulHits(shots);
    }
    assertTrue(shotsRecorded.size() > 20);
  }


  @Test
  public void testMutateVariablesSuccessfulShotsOutsideDiagonalCordsNonRemoved() {
    List<Coord> successfulShots = new ArrayList<>(Arrays.asList(new Coord(0, 0), new Coord(1, 0)));
    botPlayer2.successfulHits(successfulShots);

    assertEquals(0, botPlayer2.getAlreadyShot().size());
  }

  @Test
  public void testMutateVariablesSuccessfulShotsInsideDiagonalCordsNonRemoved() {
    List<Coord> successfulShots = new ArrayList<>(List.of(new Coord(0, 0)));
    botPlayer2.getAlreadyShot().add(new Coord(0, 0));
    botPlayer2.successfulHits(successfulShots);

    assertEquals(1, botPlayer2.getAlreadyShot().size());
  }

  @Test
  public void testMutateVariablesSuccessfulShotsOnEdgeOfBoard() {
    List<Coord> successfulShots = new ArrayList<>(List.of(new Coord(0, 0)));
    botPlayer2.getAlreadyShot().add(new Coord(0, 0));
    botPlayer2.successfulHits(successfulShots);

    assertEquals(1, botPlayer2.getAlreadyShot().size());
  }

  @Test
  public void testMutateVariablesSuccessfulShotsOnCornerOfBoard() {
    List<Coord> successfulShots = new ArrayList<>(List.of(new Coord(5, 5)));
    botPlayer2.getAlreadyShot().add(new Coord(2, 2));
    botPlayer2.successfulHits(successfulShots);

    assertEquals(1, botPlayer2.getAlreadyShot().size());
  }

  @Test
  public void testGetName() {
    assertEquals("AI Human Destroyer", player3.name());
  }


  @Test
  public void testAddShot() {
    Coord shot = new Coord(5, 5);
    ArrayList<Coord> shotList = new ArrayList<>();

    // Test when shot is not already in the list
    player2.addShot(shotList, shot);
    assertTrue(shotList.contains(shot));
    assertTrue(player2.getAlreadyShot().contains(shot));

    // Test when shot is already in the list
    int originalSize = shotList.size();
    player2.addShot(shotList, shot);
    assertEquals(originalSize, shotList.size());
  }

  @Test
  public void testAddShotBoundary() {
    Coord shot = new Coord(0, 0);
    ArrayList<Coord> shotList = new ArrayList<>();

    // Test for shot at boundary
    player2.addShot(shotList, shot);
    assertTrue(shotList.contains(shot));
    assertTrue(player2.getAlreadyShot().contains(shot));
  }

}
