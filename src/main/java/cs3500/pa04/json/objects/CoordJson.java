package cs3500.pa04.json.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A record representing a coordinate with 'x' and 'y' values in a JSON object.
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {
}
