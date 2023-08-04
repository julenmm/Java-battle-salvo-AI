package cs3500.pa04.json.request.end;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.game.GameResult;

/**
 * A record representing the end game response with a game result and reason in a JSON object.
 */
public record EndGameResponse(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason
) {
}
