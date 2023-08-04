package cs3500.pa04.json.request.setup;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A record representing the set up request with a method name and arguments in a JSON object.
 */
public record SetUpRequest(
    @JsonProperty("method-name") String name,
    @JsonProperty("arguments") SetUpRequestArguments args
) {
}
