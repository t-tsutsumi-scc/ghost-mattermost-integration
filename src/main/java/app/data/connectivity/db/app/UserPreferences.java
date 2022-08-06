package app.data.connectivity.db.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@Value.Enclosing
public interface UserPreferences {

    String id();

    String value();

    @Value.Immutable
    @JsonDeserialize(as = ImmutableUserPreferences.ValueType.class)
    interface ValueType {
        @JsonProperty("all")
        boolean all();

        @JsonProperty("tags")
        List<String> tags();

        @JsonProperty("authors")
        List<String> authors();
    }

}
