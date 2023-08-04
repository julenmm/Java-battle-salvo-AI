package cs3500.pa04.model.ship;

import cs3500.pa04.model.utils.Coord;
import java.util.ArrayList;

/**
 * This class represents a Carrier, a specific type of ship with a size of 6.
 */
public class Carrier extends Ship {

  /**
   * Constructs a new Carrier with no initial coordinates.
   */
  public Carrier() {
    super(6, new ArrayList<Coord>());
  }
}
