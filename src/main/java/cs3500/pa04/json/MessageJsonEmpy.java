package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A record representing a message with a method name and empty arguments in a JSON object.
 */
public record MessageJsonEmpy(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") EmptyArgs arguments) {
}