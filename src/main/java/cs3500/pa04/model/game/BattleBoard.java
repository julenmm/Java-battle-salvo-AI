package cs3500.pa04.model.game;

import cs3500.pa04.model.ship.Battleship;
import cs3500.pa04.model.ship.Carrier;
import cs3500.pa04.model.ship.Destroyer;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.ship.Submarine;
import cs3500.pa04.model.utils.Coord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class represents a BattleBoard used for the game.
 * Each BattleBoard instance is initialized with a set of ships and a 2D array representing the game
 * state.
 */
public class BattleBoard {
  private final String[][] board;

  /**
   * Constructor that initializes the BattleBoard with a set of ships.
   *
   * @param x     The width of the board.
   * @param y     The height of the board.
   * @param ships The ships to be placed on the board.
   */
  public BattleBoard(int x, int y, List<Ship> ships) {
    board = new String[y][x];
    this.innitBoard(x, y);
    this.placeShips(ships);
  }

  /**
   * Constructor that initializes the BattleBoard without any ships.
   *
   * @param x The width of the board.
   * @param y The height of the board.
   */
  public BattleBoard(int x, int y) {
    board = new String[y][x];
    this.innitBoard(x, y);
  }

  /**
   * Initializes the board with a default value for all cells.
   *
   * @param x The width of the board.
   * @param y The height of the board.
   */
  public void innitBoard(int x, int y) {
    for (int i = 0; i < y; i++) {
      for (int j = 0; j < x; j++) {
        this.board[i][j] = "0";
      }
    }
  }

  /**
   * Places the provided ships on the board.
   *
   * @param ships The ships to be placed on the board.
   */
  public void placeShips(List<Ship> ships) {
    List<Coord> usedCoordinates = new ArrayList();
    for (Ship ship : ships) {
      boolean horizontal;
      Random rand1 = new Random();
      int shipLength = ship.getSize();
      horizontal = rand1.nextDouble() > 0.5;
      List<Coord> currentCoords =
          this.createShipCoords(horizontal, shipLength, usedCoordinates, ship);
      for (Coord cord : currentCoords) {
        if (ship instanceof Submarine) {
          this.board[cord.getYcord()][cord.getXcord()] = "S";
        } else if (ship instanceof Battleship) {
          this.board[cord.getYcord()][cord.getXcord()] = "B";
        } else if (ship instanceof Carrier) {
          this.board[cord.getYcord()][cord.getXcord()] = "C";
        } else if (ship instanceof Destroyer) {
          this.board[cord.getYcord()][cord.getXcord()] = "D";
        } else {
          throw new RuntimeException("instance of ship not found");
        }
      }
    }
  }

  /**
   * Creates and returns a list of Coordinates where a ship will be placed.
   *
   * @param horizontal      Whether the ship will be placed horizontally.
   * @param shipLength      The length of the ship.
   * @param usedCoordinates The coordinates already occupied by other ships.
   * @param ship            The ship to be placed.
   * @return A list of Coordinates where the ship will be placed.
   */
  public List<Coord> createShipCoords(boolean horizontal, int shipLength,
                                      List<Coord> usedCoordinates, Ship ship) {
    // Create a list of all possible starting points
    List<Coord> allCoords = new ArrayList<>();
    for (int x = 0; x < this.board[0].length; x++) {
      for (int y = 0; y < this.board.length; y++) {
        allCoords.add(new Coord(x, y));
      }
    }
    Collections.shuffle(allCoords);
    for (Coord coord : allCoords) {
      List<Coord> horizontalCoords = new ArrayList<>();
      List<Coord> verticalCoords = new ArrayList<>();

      for (int i = 0; i < shipLength; i++) {
        horizontalCoords.add(new Coord(coord.getXcord() + i, coord.getYcord()));
        verticalCoords.add(new Coord(coord.getXcord(), coord.getYcord() + i));
      }
      List<Coord> primaryOption;
      List<Coord> secondaryOption;
      if (horizontal) {
        primaryOption = horizontalCoords;
        secondaryOption = verticalCoords;
      } else {
        primaryOption = verticalCoords;
        secondaryOption = horizontalCoords;
      }
      if (isValidPlacement(primaryOption, usedCoordinates)) {
        ship.setCoordinates(primaryOption);
        usedCoordinates.addAll(primaryOption);
        return primaryOption;
      } else if (isValidPlacement(secondaryOption, usedCoordinates)) {
        ship.setCoordinates(secondaryOption);
        usedCoordinates.addAll(secondaryOption);
        return secondaryOption;
      }
    }
    throw new RuntimeException("no valid coordinate was found to place a ship");
  }

  /**
   * Returns whether the provided list of Coordinates is a valid placement for a ship.
   *
   * @param coords          The potential coordinates for a ship.
   * @param usedCoordinates The coordinates already occupied by other ships.
   * @return Whether the potential coordinates do not conflict with other ship placementss.
   */
  private boolean isValidPlacement(List<Coord> coords, List<Coord> usedCoordinates) {
    for (Coord coord : coords) {
      if (usedCoordinates.contains(coord)
          || coord.getXcord() >= this.board[0].length
          || coord.getXcord() < 0
          || coord.getYcord() >= this.board.length
          || coord.getYcord() < 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the current state of the given cell.
   *
   * @param x The xcord-coordinate of the cell.
   * @param y The ycord-coordinate of the cell.
   * @return The current state of the cell.
   */
  public String getCoord(int x, int y) {
    return this.board[y][x];
  }

  /**
   * Updates the state of the given cell to reflect a hit.
   *
   * @param x The xcord-coordinate of the cell.
   * @param y The ycord-coordinate of the cell.
   */
  public void hitMyBoard(int x, int y) {
    if (this.board[y][x].equals("0")) {
      this.board[y][x] = "M";
    } else {
      this.board[y][x] = "H";
    }
  }

  /**
   * Returns the current state of the board.
   *
   * @return The current state of the board.
   */
  public String[][] getBoard() {
    return this.board;
  }

  /**
   * Updates the state of the given cell in the enemy's board to reflect an attempted hit.
   *
   * @param x The xcord-coordinate of the cell.
   * @param y The ycord-coordinate of the cell.
   */
  public void hitEnemyBoardAttempts(int x, int y) {
    this.board[y][x] = "X";
  }

  /**
   * Updates the state of the given cell in the enemy's board to reflect a successful hit.
   *
   * @param x The xcord-coordinate of the cell.
   * @param y The ycord-coordinate of the cell.
   */
  public void successfulEnemyHit(int x, int y) {
    this.board[y][x] = "#";
  }
}
