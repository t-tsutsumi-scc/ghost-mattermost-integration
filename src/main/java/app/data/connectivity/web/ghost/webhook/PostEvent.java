package app.data.connectivity.web.ghost.webhook;

import app.data.connectivity.web.ghost.Post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.time.OffsetDateTime;
import java.util.Optional;

@Value.Immutable
@Value.Enclosing
@JsonDeserialize(as = ImmutablePostEvent.class)
public interface PostEvent {

    @JsonProperty("post")
    WrappedPost post();

    @Value.Immutable
    @JsonDeserialize(as = ImmutablePostEvent.WrappedPost.class)
    interface WrappedPost {
        @JsonProperty("current")
        Post current();

        @JsonProperty("previous")
        Previous previous();
    }

    @Value.Immutable
    @JsonDeserialize(as = ImmutablePostEvent.Previous.class)
    interface Previous {
        @JsonProperty("mobiledoc")
        Optional<String> mobiledoc();

        @JsonProperty("status")
        Optional<String> status();

        @JsonProperty("updated_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        Optional<OffsetDateTime> updatedAt();

        @JsonProperty("html")
        Optional<String> html();

        @JsonProperty("plaintext")
        Optional<String> plaintext();

        @JsonProperty("published_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        Optional<OffsetDateTime> publishedAt();
    }

}
