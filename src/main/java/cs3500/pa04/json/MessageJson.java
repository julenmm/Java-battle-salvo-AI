package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * A record representing a generic message with a method name and arguments in a JSON object.
 * The arguments are of type JsonNode to allow for different argument types.
 */
public record MessageJson(
    @JsonProperty("method-name") String name,
    @JsonProperty("arguments") JsonNode args
) {
}
