package app.data.connectivity.web.ghost.content.response;

import app.data.connectivity.web.ghost.Author;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonDeserialize(as = ImmutableAuthorsResponse.class)
public interface AuthorsResponse {

    @JsonProperty("authors")
    List<Author> authors();

}
