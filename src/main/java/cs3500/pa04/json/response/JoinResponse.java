package cs3500.pa04.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A record representing a join response with a method name and arguments in a JSON object.
 */
public record JoinResponse(
    @JsonProperty("method-name") String join,
    @JsonProperty("arguments") JoinResponseArguments args
) {
}
