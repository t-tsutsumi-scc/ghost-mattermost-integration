package app.data.controller.request;

import org.immutables.value.Value;
import org.immutables.value.Value.Default;

import java.util.Collections;
import java.util.List;

@Value.Immutable
public interface NotificationPreferencesForm {

    @Default
    default boolean all() {
        return false;
    }

    @Default
    default List<String> tags() {
        return Collections.emptyList();
    }

    @Default
    default List<String> authors() {
        return Collections.emptyList();
    }

}
