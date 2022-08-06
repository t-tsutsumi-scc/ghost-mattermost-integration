package app.data.connectivity.web.ghost.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableSearchResult.class)
public interface SearchResult {

    @JsonProperty("posts")
    List<MatchedPost> posts();

    @JsonProperty("keywords")
    List<String> keywords();

}
