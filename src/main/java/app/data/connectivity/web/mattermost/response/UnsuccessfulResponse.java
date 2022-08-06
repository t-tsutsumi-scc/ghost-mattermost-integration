package app.data.connectivity.web.mattermost.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableUnsuccessfulResponse.class)
public interface UnsuccessfulResponse {

    @JsonProperty("status_code")
    String statusCode();

    @JsonProperty("id")
    String id();

    @JsonProperty("message")
    String message();

    @JsonProperty("request_id")
    String requestId();

}
