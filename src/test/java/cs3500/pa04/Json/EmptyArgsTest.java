package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import cs3500.pa04.json.EmptyArgs;
import org.junit.jupiter.api.Test;

public class EmptyArgsTest {

  @Test
  public void testEmptyArgs() {
    EmptyArgs emptyArgs = new EmptyArgs();
    assertNotNull(emptyArgs);
  }
}
