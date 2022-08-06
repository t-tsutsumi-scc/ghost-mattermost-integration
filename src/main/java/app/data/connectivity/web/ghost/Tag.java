package app.data.connectivity.web.ghost;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@JsonDeserialize(as = ImmutableTag.class)
public interface Tag {

    @JsonProperty("id")
    String id();

    @JsonProperty("name")
    String name();

    @JsonProperty("slug")
    String slug();

    @JsonProperty("description")
    Optional<String> description();

    @JsonProperty("feature_image")
    Optional<String> featureImage();

    @JsonProperty("visibility")
    Optional<String> visibility();

    @JsonProperty("og_image")
    Optional<String> ogImage();

    @JsonProperty("og_title")
    Optional<String> ogTitle();

    @JsonProperty("og_description")
    Optional<String> ogDescription();

    @JsonProperty("twitter_image")
    Optional<String> twitterImage();

    @JsonProperty("twitter_title")
    Optional<String> twitterTitle();

    @JsonProperty("twitter_description")
    Optional<String> twitterDescription();

    @JsonProperty("meta_title")
    Optional<String> metaTitle();

    @JsonProperty("meta_description")
    Optional<String> metaDescription();

    @JsonProperty("codeinjection_head")
    Optional<String> codeinjectionHead();

    @JsonProperty("codeinjection_foot")
    Optional<String> codeinjectionFoot();

    @JsonProperty("canonical_url")
    Optional<String> canonicalUrl();

    @JsonProperty("accent_color")
    Optional<String> accentColor();

    @JsonProperty("url")
    Optional<String> url();

    @JsonProperty("count")
    Optional<Count> count();

}
