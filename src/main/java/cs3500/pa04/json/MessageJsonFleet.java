package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.objects.FleetJson;

/**
 * A record representing a message with a method name and fleet as arguments in a JSON object.
 */
public record MessageJsonFleet(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") FleetJson arguments) {
}

