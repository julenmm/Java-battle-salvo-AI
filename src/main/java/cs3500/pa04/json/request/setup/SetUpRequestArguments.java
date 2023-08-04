package cs3500.pa04.json.request.setup;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A record representing the set up request arguments with board width, height and fleet
 * specification in a JSON object.
 */
public record SetUpRequestArguments(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") FleetSpec specs
) {
}
