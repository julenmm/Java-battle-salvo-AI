package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.ship.Carrier;
import cs3500.pa04.model.ship.Ship;
import cs3500.pa04.model.utils.Coord;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


/**
 * This class contains unit tests for the Carrier class.
 * It ensures that the functionality for Carrier work as expected.
 */
public class CarrierTest {
  @Test
  public void testGetSize() {
    Ship ship = new Carrier();
    assertEquals(6, ship.getSize());
  }

  @Test
  public void testGetCoordinates() {
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    coords.add(new Coord(1, 0));
    coords.add(new Coord(2, 0));
    coords.add(new Coord(3, 0));
    coords.add(new Coord(4, 0));
    coords.add(new Coord(5, 0));
    Ship ship = new Carrier();
    ship.setCoordinates(coords);
    assertEquals(coords, ship.getCoordinates());
  }

  @Test
  public void testSetCoordinates() {
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    coords.add(new Coord(1, 0));
    coords.add(new Coord(2, 0));
    coords.add(new Coord(3, 0));
    coords.add(new Coord(4, 0));
    coords.add(new Coord(5, 0));
    Ship ship = new Carrier();
    ship.setCoordinates(coords);
    assertEquals(coords, ship.getCoordinates());
  }

  @Test
  public void testShipInCoord_Found() {
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    coords.add(new Coord(1, 0));
    coords.add(new Coord(2, 0));
    coords.add(new Coord(3, 0));
    coords.add(new Coord(4, 0));
    coords.add(new Coord(5, 0));
    Ship ship = new Carrier();
    ship.setCoordinates(coords);
    assertTrue(ship.shipInCoord(new Coord(1, 0)));
  }

  @Test
  public void testShipInCoord_NotFound() {
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    coords.add(new Coord(1, 0));
    coords.add(new Coord(2, 0));
    coords.add(new Coord(3, 0));
    coords.add(new Coord(4, 0));
    coords.add(new Coord(5, 0));
    Ship ship = new Carrier();
    ship.setCoordinates(coords);
    assertFalse(ship.shipInCoord(new Coord(1, 1)));
  }

  @Test
  public void testHitShip() {
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    coords.add(new Coord(1, 0));
    coords.add(new Coord(2, 0));
    coords.add(new Coord(3, 0));
    coords.add(new Coord(4, 0));
    coords.add(new Coord(5, 0));
    Ship ship = new Carrier();
    ship.setCoordinates(coords);
    ship.hitShip(new Coord(1, 0));
    assertEquals(5, ship.getCoordinates().size());
    assertFalse(ship.getCoordinates().contains(new Coord(1, 0)));
  }
}
