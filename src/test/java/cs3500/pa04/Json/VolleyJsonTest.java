package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.objects.CoordJson;
import cs3500.pa04.json.objects.VolleyJson;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VolleyJsonTest {
  private final ObjectMapper mapper = new ObjectMapper();
  private VolleyJson volley1;
  private VolleyJson volley2;

  @BeforeEach
  public void setup() {
    List<CoordJson> coords1 = Arrays.asList(new CoordJson(10, 20), new CoordJson(30, 40));
    List<CoordJson> coords2 = Arrays.asList(new CoordJson(50, 60), new CoordJson(70, 80));

    volley1 = new VolleyJson(coords1);
    volley2 = new VolleyJson(coords2);
  }

  @Test
  public void testFields() {
    try {
      assertEquals(10, volley1.coords().get(0).x());
      assertEquals(20, volley1.coords().get(0).y());
      assertEquals(30, volley1.coords().get(1).x());
      assertEquals(40, volley1.coords().get(1).y());

      assertEquals(50, volley2.coords().get(0).x());
      assertEquals(60, volley2.coords().get(0).y());
      assertEquals(70, volley2.coords().get(1).x());
      assertEquals(80, volley2.coords().get(1).y());
    } catch (Exception e) {
      System.out.println("Field verification failed: " + e.getMessage());
    }
  }

  @Test
  public void testSerializationAndDeserialization() {
    try {
      String volley1Json = mapper.writeValueAsString(volley1);
      String volley2Json = mapper.writeValueAsString(volley2);

      assertEquals("{\"coordinates\":[{\"x\":10,\"y\":20},{\"x\":30,\"y\":40}]}", volley1Json);
      assertEquals("{\"coordinates\":[{\"x\":50,\"y\":60},{\"x\":70,\"y\":80}]}", volley2Json);

      VolleyJson deserializedVolley1 = mapper.readValue(volley1Json, VolleyJson.class);
      VolleyJson deserializedVolley2 = mapper.readValue(volley2Json, VolleyJson.class);

      assertEquals(volley1, deserializedVolley1);
      assertEquals(volley2, deserializedVolley2);
    } catch (Exception e) {
      System.out.println("Serialization or deserialization failed: " + e.getMessage());
    }
  }
}

