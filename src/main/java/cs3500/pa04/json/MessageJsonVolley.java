package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.objects.VolleyJson;

/**
 * A record representing a message with a method name and volley as arguments in a JSON object.
 */
public record MessageJsonVolley(
    @JsonProperty("method-name") String name,
    @JsonProperty("arguments") VolleyJson args
) {
}