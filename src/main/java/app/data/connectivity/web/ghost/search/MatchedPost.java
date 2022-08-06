package app.data.connectivity.web.ghost.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Date;

@Value.Immutable
@JsonSerialize(as = ImmutableMatchedPost.class)
public interface MatchedPost {

    @JsonProperty("title")
    String title();

    @JsonProperty("slug")
    String slug();

    @JsonProperty("summary")
    String summary();

    @JsonProperty("updatedAt")
    Date updatedAt();

}
