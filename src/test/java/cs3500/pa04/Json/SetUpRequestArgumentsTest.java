package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.request.setup.FleetSpec;
import cs3500.pa04.json.request.setup.SetUpRequestArguments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SetUpRequestArgumentsTest {
  private final ObjectMapper mapper = new ObjectMapper();
  private SetUpRequestArguments args;

  @BeforeEach
  public void setup() {
    FleetSpec specs = new FleetSpec(1, 1, 1, 1);
    args = new SetUpRequestArguments(10, 10, specs);
  }

  @Test
  public void testFields() {
    try {
      assertEquals(10, args.width());
      assertEquals(10, args.height());
      assertEquals(new FleetSpec(1, 1, 1, 1), args.specs());
    } catch (Exception e) {
      System.out.println("Field verification failed: " + e.getMessage());
    }
  }

  @Test
  public void testSerializationAndDeserialization() {
    try {
      String argsJson = mapper.writeValueAsString(args);
      SetUpRequestArguments deserializedArgs =
          mapper.readValue(argsJson, SetUpRequestArguments.class);
      assertEquals(args, deserializedArgs);
    } catch (Exception e) {
      System.out.println("Serialization or deserialization failed: " + e.getMessage());
    }
  }
}
