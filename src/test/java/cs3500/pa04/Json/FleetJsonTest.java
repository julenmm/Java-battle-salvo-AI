package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.objects.CoordJson;
import cs3500.pa04.json.objects.FleetJson;
import cs3500.pa04.json.objects.ShipJson;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FleetJsonTest {
  private final ObjectMapper mapper = new ObjectMapper();
  private ShipJson ship;
  private FleetJson fleet;

  @BeforeEach
  public void setup() {
    ship = new ShipJson(new CoordJson(10, 20), 30, "VERTICAL");
    fleet = new FleetJson(List.of(ship));
  }

  @Test
  public void testFields() {
    try {
      assertEquals(10, ship.coord().x());
      assertEquals(20, ship.coord().y());
      assertEquals(30, ship.length());
      assertEquals("VERTICAL", ship.direction());
      assertEquals(1, fleet.ships().size());
      assertEquals(ship, fleet.ships().get(0));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testSerializationAndDeserialization() {
    try {
      String shipJson = mapper.writeValueAsString(ship);
      String fleetJson = mapper.writeValueAsString(fleet);

      assertEquals(
          "{\"coord\":{\"x\":10,\"y\":20},\"length\":30,\"direction\":\"VERTICAL\"}", shipJson);
      assertEquals(
          "{\"fleet\":[{\"coord\":{\"x\":10,\"y\":20},\""
              + "length\":30,\"direction\":\"VERTICAL\"}]}", fleetJson);

      ShipJson deserializedShip = mapper.readValue(shipJson, ShipJson.class);
      FleetJson deserializedFleet = mapper.readValue(fleetJson, FleetJson.class);

      assertEquals(ship, deserializedShip);
      assertEquals(fleet, deserializedFleet);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}

