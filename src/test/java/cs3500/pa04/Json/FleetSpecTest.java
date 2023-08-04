package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.request.setup.FleetSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FleetSpecTest {
  private final ObjectMapper mapper = new ObjectMapper();
  private FleetSpec specs;

  @BeforeEach
  public void setup() {
    specs = new FleetSpec(1, 1, 1, 1);
  }

  @Test
  public void testFields() {
    try {
      assertEquals(1, specs.cnum());
      assertEquals(1, specs.snum());
      assertEquals(1, specs.bnum());
      assertEquals(1, specs.dnum());
    } catch (Exception e) {
      System.out.println("Field verification failed: " + e.getMessage());
    }
  }

  @Test
  public void testSerializationAndDeserialization() {
    try {
      String specsJson = mapper.writeValueAsString(specs);
      FleetSpec deserializedSpecs = mapper.readValue(specsJson, FleetSpec.class);
      assertEquals(specs, deserializedSpecs);
    } catch (Exception e) {
      System.out.println("Serialization or deserialization failed: " + e.getMessage());
    }
  }
}
