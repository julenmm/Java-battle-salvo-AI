package cs3500.pa04.model.ship;

import cs3500.pa04.model.utils.Coord;
import java.util.ArrayList;

/**
 * This class represents a Battleship, a specific type of ship with a size of 5.
 */
public class Battleship extends Ship {

  /**
   * Constructs a new Battleship with no initial coordinates.
   */
  public Battleship() {
    super(5, new ArrayList<Coord>());
  }
}
