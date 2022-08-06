package app.data.connectivity.web.ghost;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@JsonDeserialize(as = ImmutableAuthor.class)
public interface Author {

    @JsonProperty("id")
    String id();

    @JsonProperty("name")
    String name();

    @JsonProperty("slug")
    String slug();

    @JsonProperty("profile_image")
    Optional<String> profileImage();

    @JsonProperty("cover_image")
    Optional<String> coverImage();

    @JsonProperty("bio")
    Optional<String> bio();

    @JsonProperty("website")
    Optional<String> website();

    @JsonProperty("location")
    Optional<String> location();

    @JsonProperty("facebook")
    Optional<String> facebook();

    @JsonProperty("twitter")
    Optional<String> twitter();

    @JsonProperty("meta_title")
    Optional<String> metaTitle();

    @JsonProperty("meta_description")
    Optional<String> metaDescription();

    @JsonProperty("url")
    Optional<String> url();

    @JsonProperty("count")
    Optional<Count> count();

}
