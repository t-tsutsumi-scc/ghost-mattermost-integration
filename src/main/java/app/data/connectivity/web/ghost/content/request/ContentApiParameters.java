package app.data.connectivity.web.ghost.content.request;

import org.immutables.value.Value;

import java.util.Optional;
import java.util.OptionalInt;

@Value.Immutable
public interface ContentApiParameters {

    /** Tells the API to return additional data related to the resource you have requested. (optional) */
    Optional<String> include();

    /** Limit the fields returned in the response object. (optional) */
    Optional<String> fields();

    /** Apply fine-grained filters to target specific data. (optional) */
    Optional<String> filter();

    /** By default, only 15 records are returned at once. (optional, default to 15) */
    Optional<String> limit();

    /** By default, the first 15 records are returned. (optional, default to 1) */
    OptionalInt page();

    /** Different resources have a different default sort order. (optional) */
    Optional<String> order();

}
