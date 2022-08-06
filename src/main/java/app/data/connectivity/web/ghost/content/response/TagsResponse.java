package app.data.connectivity.web.ghost.content.response;

import app.data.connectivity.web.ghost.Tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonDeserialize(as = ImmutableTagsResponse.class)
public interface TagsResponse {

    @JsonProperty("tags")
    List<Tag> tags();

}
