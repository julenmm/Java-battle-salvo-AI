package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.game.BattleBoard;
import cs3500.pa04.model.ship.Battleship;
import cs3500.pa04.model.ship.Carrier;
import cs3500.pa04.model.ship.Destroyer;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.Submarine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for the BattleBoard class.
 * It ensures that the functionality for creating and manipulating boards works appropriately.
 */
public class BattleBoardTest {
  private final int BOARD_WIDTH = 10;
  private final int BOARD_HEIGHT = 10;
  private BattleBoard board;
  private BattleBoard board2;
  private ArrayList<Ship> ships;

  @BeforeEach
  public void setup() {
    ships = new ArrayList<>();
    Map<Ship, Integer> shipSpecs = new HashMap<>();
    shipSpecs.put(new Carrier(), 1);
    shipSpecs.put(new Battleship(), 1);
    shipSpecs.put(new Destroyer(), 1);
    shipSpecs.put(new Submarine(), 1);

    for (Ship key : shipSpecs.keySet()) {
      for (int i = 0; i < shipSpecs.get(key); i++) {
        ships.add(key);
      }
    }
    board = new BattleBoard(BOARD_WIDTH, BOARD_HEIGHT, ships);
    board2 = new BattleBoard(BOARD_WIDTH, BOARD_HEIGHT);
  }

  @Test
  public void testInitBoard() {
    String[][] boardArray = board2.getBoard();
    for (int i = 0; i < BOARD_HEIGHT; i++) {
      for (int j = 0; j < BOARD_WIDTH; j++) {
        assertEquals("0", (boardArray[i][j]));
      }
    }
  }

  @Test
  public void testPlaceShips() {
    String[][] boardArray = board.getBoard();
    int shipCells = 0;
    for (int i = 0; i < BOARD_HEIGHT; i++) {
      for (int j = 0; j < BOARD_WIDTH; j++) {
        if (!boardArray[i][j].equals("0")) {
          shipCells++;
        }
      }
    }
    assertEquals(ships.stream().mapToInt(Ship::getSize).sum(), shipCells);
  }

  @Test
  public void testGetCoord() {
    assertEquals("0", board2.getCoord(0, 0));
  }


  @Test
  public void testHitEnemyBoardAttempts() {
    BattleBoard enemyBoard = new BattleBoard(BOARD_WIDTH, BOARD_HEIGHT);
    enemyBoard.hitEnemyBoardAttempts(0, 0);
    assertEquals("X", enemyBoard.getCoord(0, 0));
  }

  @Test
  public void testSuccessfulEnemyHit() {
    BattleBoard enemyBoard = new BattleBoard(BOARD_WIDTH, BOARD_HEIGHT);
    enemyBoard.successfulEnemyHit(0, 0);
    assertEquals("#", enemyBoard.getCoord(0, 0));
  }
}
