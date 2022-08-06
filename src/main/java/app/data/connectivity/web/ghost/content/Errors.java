package app.data.connectivity.web.ghost.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonDeserialize(as = ImmutableErrors.class)
public interface Errors {

    @JsonProperty("errors")
    List<Error> errors();

    @Value.Immutable
    @JsonDeserialize(as = ImmutableError.class)
    interface Error {
        @JsonProperty("message")
        String message();

        @JsonProperty("errorType")
        String errorType();
    }

}
