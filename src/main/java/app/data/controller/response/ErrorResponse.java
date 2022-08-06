package app.data.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableErrorResponse.class)
public interface ErrorResponse {

    @JsonProperty("id")
    String id();

    @JsonProperty("message")
    String message();

}
