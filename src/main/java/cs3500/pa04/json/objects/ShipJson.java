package cs3500.pa04.json.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A record representing a ship with coordinate, length, and direction in a JSON object.
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction
) {
}
