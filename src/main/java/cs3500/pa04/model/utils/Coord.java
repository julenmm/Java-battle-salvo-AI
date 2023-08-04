package cs3500.pa04.model.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a coordinate (xcord,ycord).
 */
public class Coord {
  private final int x;
  private final int y;

  /**
   * Constructs a new coordinate with the specified xcord and ycord values.
   *
   * @param x the x value of this coordinate.
   * @param y the y value of this coordinate.
   */
  @JsonCreator
  public Coord(@JsonProperty("x") int x, @JsonProperty("y") int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns the xcord value of this coordinate.
   *
   * @return the xcord value of this coordinate.
   */
  public int getX() {
    return this.x;
  }

  /**
   * Returns the ycord value of this coordinate.
   *
   * @return the ycord value of this coordinate.
   */
  public int getY() {
    return this.y;
  }

  /**
   * Determines whether this coordinate is equal to a specified object.
   * The result is true if and only if the object is a Coord object with the same coordinates.
   *
   * @param obj the object to compare this coordinate against.
   * @return true if the given object represents a Coord equivalent to this coordinate.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Coord coord = (Coord) obj;
    return x == coord.x
        && y == coord.y;
  }

  /**
   * Returns a hash code value for this coordinate.
   *
   * @return a hash code value for this coordinate.
   */
  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + x;
    result = 31 * result + y;
    return result;
  }

}
