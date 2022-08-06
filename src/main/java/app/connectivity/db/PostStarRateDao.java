package app.connectivity.db;

import app.config.GhostDataSourceConfig;
import app.data.connectivity.db.app.ImmutablePostStarRate;
import app.data.connectivity.db.app.PostStarRate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class PostStarRateDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier(GhostDataSourceConfig.JDBC_TEMPLATE_NAME)
    JdbcTemplate jdbcTemplateGhost;

    public List<PostStarRate> getAll() {
        final Map<String, String> postNames = new HashMap<>();
        jdbcTemplateGhost.query("SELECT id, title FROM posts", (ResultSet rs) -> {
            while (rs.next()) {
                postNames.put(rs.getString(1), rs.getString(2));
            }
        });

        return jdbcTemplate.query("SELECT * FROM post_star_rate ORDER BY 1, 2", (ResultSet rs, int rowNum) -> {
            final String postId = rs.getString(1);
            final String postTitle = postNames.getOrDefault(postId, "");
            final String uid = rs.getString(2);
            final int score = rs.getInt(3);
            return ImmutablePostStarRate.builder().postId(postId).postTitle(postTitle).uid(uid).score(score).build();
        });
    }

    public Optional<Integer> getScore(final String postId, final String uid) {
        Integer score = null;
        try {
            score = jdbcTemplate.queryForObject("SELECT score FROM post_star_rate WHERE post_id=? AND uid = ?",
                    Integer.class, postId, uid);
        } catch (EmptyResultDataAccessException ignore) {
            // fall through
        }
        return Optional.ofNullable(score);
    }

    public void upsertScore(final String postId, final String uid, final int score) {
        jdbcTemplate.update("INSERT INTO post_star_rate VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE score=?", postId, uid,
                score, score);
    }

}
