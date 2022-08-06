package app.data.connectivity.db.app;

import org.immutables.value.Value;

@Value.Immutable
public interface PostStarRate {

    String postId();

    String postTitle();

    String uid();

    int score();

}
