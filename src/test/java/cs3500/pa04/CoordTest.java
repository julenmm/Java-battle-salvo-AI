package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.utils.Coord;
import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for the Coord class.
 * It ensures that the functionality coordinates work as expected.
 */
public class CoordTest {
  @Test
  public void testGetX() {
    Coord coord = new Coord(3, 4);
    assertEquals(3, coord.getXcord());
  }

  @Test
  public void testGetY() {
    Coord coord = new Coord(3, 4);
    assertEquals(4, coord.getYcord());
  }

  @Test
  public void testEquals_SameObject() {
    Coord coord = new Coord(3, 4);
    assertEquals(coord, coord);
  }

  @Test
  public void testEquals_NullObject() {
    Coord coord = new Coord(3, 4);
    assertNotEquals(null, coord);
  }

  @Test
  public void testEquals_DifferentClass() {
    Coord coord = new Coord(3, 4);
    Object obj = new Object();
    assertNotEquals(coord, obj);
  }

  @Test
  public void testEquals_SameValues() {
    Coord coord1 = new Coord(3, 4);
    Coord coord2 = new Coord(3, 4);
    assertEquals(coord1, coord2);
  }

  @Test
  public void testEquals_DifferentValues() {
    Coord coord1 = new Coord(3, 4);
    Coord coord2 = new Coord(5, 6);
    assertNotEquals(coord1, coord2);
    Coord coord3 = new Coord(3, 4);
    Coord coord4 = new Coord(3, 6);
    assertNotEquals(coord3, coord4);
    Coord coord5 = new Coord(3, 4);
    Coord coord6 = new Coord(5, 4);
    assertNotEquals(coord5, coord6);
  }

  @Test
  public void testHashCode_SameValues() {
    Coord coord1 = new Coord(3, 4);
    Coord coord2 = new Coord(3, 4);
    assertEquals(coord1.hashCode(), coord2.hashCode());
  }

  @Test
  public void testHashCode_DifferentValues() {
    Coord coord1 = new Coord(3, 4);
    Coord coord2 = new Coord(5, 6);
    assertNotEquals(coord1.hashCode(), coord2.hashCode());
  }
}
