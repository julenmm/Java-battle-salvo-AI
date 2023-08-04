package cs3500.pa04.model.ship;

import cs3500.pa04.model.utils.Coord;
import java.util.ArrayList;

/**
 * This class represents a Destroyer, a specific type of ship with a size of 4.
 */
public class Destroyer extends Ship {

  /**
   * Constructs a new Destroyer with no initial coordinates.
   */
  public Destroyer() {
    super(4, new ArrayList<Coord>());
  }
}
