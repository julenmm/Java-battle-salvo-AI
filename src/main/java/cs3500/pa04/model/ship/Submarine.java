package cs3500.pa04.model.ship;

import cs3500.pa04.model.utils.Coord;
import java.util.ArrayList;

/**
 * This class represents a Submarine, a specific type of ship with a size of 3.
 */
public class Submarine extends Ship {

  /**
   * Constructs a new Submarine with no initial coordinates.
   */
  public Submarine() {
    super(3, new ArrayList<Coord>());
  }
}
