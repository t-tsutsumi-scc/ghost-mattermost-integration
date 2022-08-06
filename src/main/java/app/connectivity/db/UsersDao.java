package app.connectivity.db;

import app.data.connectivity.db.app.UserPreferences;
import app.util.JsonMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class UsersDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    JsonMapper jsonMapper;

    public Optional<UserPreferences.ValueType> getPreferences(final String userId) {
        String jsonPreferences = null;
        try {
            jsonPreferences = jdbcTemplate.queryForObject("SELECT value FROM users WHERE id=?", String.class, userId);
        } catch (EmptyResultDataAccessException ignore) {
            // fall through
        }
        return Optional.ofNullable(
                jsonPreferences != null ? jsonMapper.readValue(jsonPreferences, UserPreferences.ValueType.class)
                        : null);
    }

    public List<String> getUserIds(final Predicate<UserPreferences.ValueType> filter) {
        return jdbcTemplate.query("SELECT id, value FROM users", (ResultSet rs) -> {
            final var userIds = new ArrayList<String>();
            while (rs.next()) {
                final String jsonPreferences = rs.getString(2);
                final UserPreferences.ValueType preferences = jsonMapper.readValue(jsonPreferences,
                        UserPreferences.ValueType.class);
                if (filter.test(preferences)) {
                    final String userId = rs.getString(1);
                    userIds.add(userId);
                }
            }
            return userIds;
        });
    }

    public void upsertPreferences(final String userId, final UserPreferences.ValueType preferences) {
        final var jsonPreferences = jsonMapper.writeValueAsString(preferences);
        jdbcTemplate.update("INSERT INTO users VALUES (?, ?) ON DUPLICATE KEY UPDATE value=?", userId, jsonPreferences,
                jsonPreferences);
    }

}
