package app.data.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableGhostStarRatingResponse.class)
public interface GhostStarRatingResponse {

    @JsonProperty("score")
    int score();

}
