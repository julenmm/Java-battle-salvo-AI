package cs3500.pa04.json.request.end;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A record representing the end game status with a method name and arguments in a JSON object.
 */
public record EndGame(
    @JsonProperty("method-name") String name,
    @JsonProperty("arguments") EndGameResponse args
) {

}
