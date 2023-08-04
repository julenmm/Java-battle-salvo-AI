package cs3500.pa04.model.ship;

import cs3500.pa04.model.utils.Coord;
import java.util.List;

/**
 * This abstract class represents a ship in a board game such as Battleship.
 * A ship has a size and a list of coordinates representing its location on the game board.
 */
public abstract class Ship {
  private final int size;
  private List<Coord> coordinates;

  /**
   * Constructs a new Ship with a specified size and initial coordinates.
   *
   * @param size        the size of the ship.
   * @param coordinates a list of Coord objects representing the ship's location where it
   *                    hasn't been shot.
   */
  public Ship(int size, List<Coord> coordinates) {
    this.size = size;
    this.coordinates = coordinates;
  }

  /**
   * Returns the size of this ship.
   *
   * @return the size of this ship.
   */
  public int getSize() {
    return this.size;
  }

  /**
   * Returns the current coordinates of this ship.
   *
   * @return a list of Coord objects representing the current coordinates of this ship.
   */
  public List<Coord> getCoordinates() {
    return this.coordinates;
  }

  /**
   * Sets new coordinates for this ship.
   *
   * @param toSet a list of Coord objects representing the new coordinates to be set.
   */
  public void setCoordinates(List<Coord> toSet) {
    this.coordinates = toSet;
  }

  /**
   * Checks if the ship is located at a specific coordinate.
   *
   * @param coord a Coord object representing the coordinate to check.
   * @return true if the ship is located at the specified coordinate, false otherwise.
   */
  public boolean shipInCoord(Coord coord) {
    for (Coord c : this.coordinates) {
      if ((c.getXcord() == coord.getXcord()) && (c.getYcord() == coord.getYcord())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Removes a coordinate from the ship's list of coordinates, simulating a hit on the ship.
   *
   * @param cord a Coord object representing the coordinate to remove.
   */
  public void hitShip(Coord cord) {
    this.coordinates.remove(cord);
  }
}
