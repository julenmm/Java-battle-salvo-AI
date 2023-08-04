package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.objects.CoordJson;
import org.junit.jupiter.api.Test;

public class CoordJsonTest {
  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testFields() {
    CoordJson coord = new CoordJson(10, 20);
    assertEquals(10, coord.x());
    assertEquals(20, coord.y());
  }

  @Test
  public void testSerialization() throws Exception {
    CoordJson coord = new CoordJson(10, 20);
    String jsonString = mapper.writeValueAsString(coord);
    assertEquals("{\"x\":10,\"y\":20}", jsonString);
  }

  @Test
  public void testDeserialization() throws Exception {
    String jsonString = "{\"x\":10,\"y\":20}";
    CoordJson coord = mapper.readValue(jsonString, CoordJson.class);
    assertEquals(10, coord.x());
    assertEquals(20, coord.y());
  }
}
