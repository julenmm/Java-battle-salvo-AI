package cs3500.pa04.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A record representing the arguments of a join response with the player's name
 * and the game type in a JSON object.
 */
public record JoinResponseArguments(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gameType
) {
}
