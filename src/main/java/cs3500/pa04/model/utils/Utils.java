package cs3500.pa04.model.utils;

import cs3500.pa04.model.ship.ShipDirection;
import java.util.List;

/**
 * This utility class provides helper methods mathematical operations.
 */
public class Utils {
  /**
   * Creates string from the coordinates and the direction given.
   *
   * @param coords gets the list of coordinates
   * @return gives a string of the coordinates
   */
  public static String coordinateDirection(List<Coord> coords) {
    if (coords.get(0).getXcord() == coords.get(1).getXcord()) {
      return ShipDirection.VERTICAL.toString();
    } else if (coords.get(0).getYcord() == coords.get(1).getYcord()) {
      return ShipDirection.HORIZONTAL.toString();
    } else {
      throw new RuntimeException("Coords are not adjacent");
    }
  }

  /**
   * Returns the smaller of two integer values.
   *
   * @param int1 the first integer to compare.
   * @param int2 the second integer to compare.
   * @return the smallest of int1 and int2.
   */
  public static int smallerInt(int int1, int int2) {
    if (int1 <= int2) {
      return int1;
    } else {
      return int2;
    }
  }
}
