package cs3500.pa04.json.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * A record representing a fleet with a list of ships in a JSON object.
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipJson> ships
) {
}
