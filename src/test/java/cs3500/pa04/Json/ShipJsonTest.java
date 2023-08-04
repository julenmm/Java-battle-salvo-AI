package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.objects.CoordJson;
import cs3500.pa04.json.objects.ShipJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShipJsonTest {
  private final ObjectMapper mapper = new ObjectMapper();
  private ShipJson ship1;
  private ShipJson ship2;

  @BeforeEach
  public void setup() {
    ship1 = new ShipJson(new CoordJson(10, 20), 30, "VERTICAL");
    ship2 = new ShipJson(new CoordJson(40, 50), 60, "HORIZONTAL");
  }

  @Test
  public void testFields() {
    try {
      assertEquals(10, ship1.coord().x());
      assertEquals(20, ship1.coord().y());
      assertEquals(30, ship1.length());
      assertEquals("VERTICAL", ship1.direction());

      assertEquals(40, ship2.coord().x());
      assertEquals(50, ship2.coord().y());
      assertEquals(60, ship2.length());
      assertEquals("HORIZONTAL", ship2.direction());
    } catch (Exception e) {
      System.out.println("Field verification failed: " + e.getMessage());
    }
  }

  @Test
  public void testSerializationAndDeserialization() {
    try {
      String ship1Json = mapper.writeValueAsString(ship1);
      String ship2Json = mapper.writeValueAsString(ship2);

      assertEquals("{\"coord\":{\"x\":10,\"y\":20},\"length\":30,\"direction\":\"VERTICAL\"}",
          ship1Json);
      assertEquals("{\"coord\":{\"x\":40,\"y\":50},\"length\":60,\"direction\":\"HORIZONTAL\"}",
          ship2Json);

      ShipJson deserializedShip1 = mapper.readValue(ship1Json, ShipJson.class);
      ShipJson deserializedShip2 = mapper.readValue(ship2Json, ShipJson.class);

      assertEquals(ship1, deserializedShip1);
      assertEquals(ship2, deserializedShip2);
    } catch (Exception e) {
      System.out.println("Serialization or deserialization failed: " + e.getMessage());
    }
  }
}

