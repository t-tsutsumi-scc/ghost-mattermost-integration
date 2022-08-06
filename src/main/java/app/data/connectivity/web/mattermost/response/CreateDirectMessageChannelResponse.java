package app.data.connectivity.web.mattermost.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableCreateDirectMessageChannelResponse.class)
public interface CreateDirectMessageChannelResponse {

    @JsonProperty("id")
    String id();

}
