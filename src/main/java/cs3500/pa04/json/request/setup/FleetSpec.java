package cs3500.pa04.json.request.setup;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A record representing a fleet specification with the number of each type of ship in a a
 * JSON object.
 */
public record FleetSpec(
    @JsonProperty("CARRIER") int cnum,
    @JsonProperty("SUBMARINE") int snum,
    @JsonProperty("BATTLESHIP") int bnum,
    @JsonProperty("DESTROYER") int dnum

) {
}
