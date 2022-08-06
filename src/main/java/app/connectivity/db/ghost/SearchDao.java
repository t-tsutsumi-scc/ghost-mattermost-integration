package app.connectivity.db.ghost;

import app.config.GhostDataSourceConfig;
import app.data.connectivity.db.ghost.ImmutablePost;
import app.data.connectivity.db.ghost.Post;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class SearchDao {

    private static final Logger logger = LoggerFactory.getLogger(SearchDao.class);

    @Autowired
    @Qualifier(GhostDataSourceConfig.JDBC_TEMPLATE_NAME)
    JdbcTemplate jdbcTemplate;

    public List<Post> getPosts(final List<String> keywords, final List<String> authors, final List<String> tags) {
        final StringBuilder sql = new StringBuilder();
        final List<Object> args = new ArrayList<>();
        sql.append("SELECT id, title, slug, plaintext, updated_at FROM posts");
        sql.append(" WHERE status='published' AND type='post'");
        authors.forEach(author -> {
            sql.append(" AND id IN (SELECT post_id FROM posts_authors WHERE author_id=?)");
            args.add(author);
        });
        tags.forEach(tag -> {
            sql.append(" AND id IN (SELECT post_id FROM posts_tags WHERE tag_id=?)");
            args.add(tag);
        });
        if (!keywords.isEmpty()) {
            keywords.forEach(keyword -> {
                final String likeKeyword = "%"
                        + keyword.replace("\\", "\\\\\\\\").replace("%", "\\%").replace("_", "\\_") + "%";
                sql.append(" AND (title LIKE ? OR custom_excerpt LIKE ? OR plaintext LIKE ?)");
                args.add(likeKeyword);
                args.add(likeKeyword);
                args.add(likeKeyword);
            });
        }
        sql.append(" ORDER BY updated_at DESC");
        sql.append(" LIMIT ").append("15");
        if (logger.isDebugEnabled()) {
            logger.debug("Get posts query: sql={}, args={}", sql, args);
        }

        return jdbcTemplate.query(sql.toString(), (ResultSet rs) -> {
            final var posts = new ArrayList<Post>();
            while (rs.next()) {
                final Post post = ImmutablePost.builder().id(rs.getString(1)).title(rs.getString(2))
                        .slug(rs.getString(3)).plaintext(StringUtils.defaultString(rs.getString(4)))
                        .updatedAt(rs.getTimestamp(5)).build();
                posts.add(post);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Get posts result: " + posts);
            }
            return posts;
        }, args.toArray());
    }

    public Map<String, String> getAuthorsWithNameIdMap(final Supplier<Map<String, String>> mapFactory) {
        return jdbcTemplate.query("SELECT id, name FROM users", (ResultSet rs) -> {
            final var authorsNameIdMap = mapFactory.get();
            while (rs.next()) {
                authorsNameIdMap.put(rs.getString(2), rs.getString(1));
            }
            return authorsNameIdMap;
        });
    }

    public Map<String, String> getTagsWithNameIdMap(final Supplier<Map<String, String>> mapFactory) {
        return jdbcTemplate.query("SELECT id, name FROM tags", (ResultSet rs) -> {
            final var tagsNameIdMap = mapFactory.get();
            while (rs.next()) {
                tagsNameIdMap.put(rs.getString(2), rs.getString(1));
            }
            return tagsNameIdMap;
        });
    }

}
