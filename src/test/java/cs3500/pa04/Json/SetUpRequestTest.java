package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.request.setup.FleetSpec;
import cs3500.pa04.json.request.setup.SetUpRequest;
import cs3500.pa04.json.request.setup.SetUpRequestArguments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SetUpRequestTest {
  private final ObjectMapper mapper = new ObjectMapper();
  private SetUpRequest request;

  @BeforeEach
  public void setup() {
    FleetSpec specs = new FleetSpec(1, 1, 1, 1);
    SetUpRequestArguments args = new SetUpRequestArguments(10, 10, specs);
    request = new SetUpRequest("setup", args);
  }

  @Test
  public void testFields() {
    try {
      assertEquals("setup", request.name());
      assertEquals(new SetUpRequestArguments(10, 10, new FleetSpec(1, 1, 1, 1)), request.args());
    } catch (Exception e) {
      System.out.println("Field verification failed: " + e.getMessage());
    }
  }

  @Test
  public void testSerializationAndDeserialization() {
    try {
      String requestJson = mapper.writeValueAsString(request);
      SetUpRequest deserializedRequest = mapper.readValue(requestJson, SetUpRequest.class);
      assertEquals(request, deserializedRequest);
    } catch (Exception e) {
      System.out.println("Serialization or deserialization failed: " + e.getMessage());
    }
  }
}
