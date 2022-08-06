package app.data.connectivity.web.ghost;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableCount.class)
public interface Count {

    @JsonProperty("posts")
    int posts();

}
