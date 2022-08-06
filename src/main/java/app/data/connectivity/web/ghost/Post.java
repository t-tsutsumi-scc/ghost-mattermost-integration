package app.data.connectivity.web.ghost;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Value.Immutable
@JsonDeserialize(as = ImmutablePost.class)
public interface Post {

    @JsonProperty("id")
    String id();

    @JsonProperty("uuid")
    String uuid();

    @JsonProperty("title")
    String title();

    @JsonProperty("slug")
    String slug();

    @JsonProperty("html")
    Optional<String> html();

    @JsonProperty("comment_id")
    Optional<String> commentId();

    @JsonProperty("plaintext")
    Optional<String> plaintext();

    @JsonProperty("feature_image")
    Optional<String> featureImage();

    @JsonProperty("featured")
    Optional<Boolean> featured();

    @JsonProperty("visibility")
    Optional<String> visibility();

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    Optional<OffsetDateTime> createdAt();

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    Optional<OffsetDateTime> updatedAt();

    @JsonProperty("published_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    Optional<OffsetDateTime> publishedAt();

    @JsonProperty("custom_excerpt")
    Optional<String> customExcerpt();

    @JsonProperty("codeinjection_head")
    Optional<String> codeinjectionHead();

    @JsonProperty("codeinjection_foot")
    Optional<String> codeinjectionFoot();

    @JsonProperty("custom_template")
    Optional<String> customTemplate();

    @JsonProperty("canonical_url")
    Optional<String> canonicalUrl();

    @JsonProperty("tags")
    Optional<List<Tag>> tags();

    @JsonProperty("authors")
    Optional<List<Author>> authors();

    @JsonProperty("primary_author")
    Optional<Author> primaryAuthor();

    @JsonProperty("primary_tag")
    Optional<Tag> primaryTag();

    @JsonProperty("url")
    String url();

    @JsonProperty("excerpt")
    Optional<String> excerpt();

    @JsonProperty("reading_time")
    OptionalInt readingTime();

    @JsonProperty("access")
    Optional<Boolean> access();

    @JsonProperty("send_email_when_published")
    Optional<Boolean> sendEmailWhenPublished();

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

    @JsonProperty("email_subject")
    Optional<String> emailSubject();

}
