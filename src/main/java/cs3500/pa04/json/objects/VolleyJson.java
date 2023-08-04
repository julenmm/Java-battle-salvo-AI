package cs3500.pa04.json.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * A record representing a volley with a list of coordinates in a JSON object.
 */
public record VolleyJson(
    @JsonProperty("coordinates") List<CoordJson> coords
) {
}
