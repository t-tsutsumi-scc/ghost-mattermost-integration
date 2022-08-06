package app.data.connectivity.web.mattermost.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Map;
import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableCreatePostRequest.class)
public interface CreatePostRequest {

    @JsonProperty("channel_id")
    String channelId();

    @JsonProperty("message")
    String message();

    @JsonProperty("root_id")
    Optional<String> rootId();

    @JsonProperty("file_ids")
    Optional<String> fileIds();

    @JsonProperty("props")
    Optional<Map<String, Object>> props();

}
