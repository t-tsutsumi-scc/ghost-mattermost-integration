package app.service;

import app.connectivity.db.ghost.SearchDao;
import app.data.connectivity.db.ghost.Post;
import app.data.connectivity.web.ghost.search.ImmutableMatchedPost;
import app.data.connectivity.web.ghost.search.ImmutableSearchResult;
import app.data.connectivity.web.ghost.search.MatchedPost;
import app.data.connectivity.web.ghost.search.SearchResult;
import app.util.SimpleAbbreviator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class GhostSearchService {

    private static final Pattern searchQueryPattern = Pattern.compile("\"([^\"]+)\"|([^\\s]+)");

    private static final int MAX_SUMMARY_LENGTH = 200;

    @Autowired
    SearchDao searchDao;

    public SearchResult searchPosts(final String searchQuery) {
        final var searchTexts = new ArrayList<String>();
        final var searchQueryMatcher = searchQueryPattern.matcher(searchQuery);
        while (searchQueryMatcher.find()) {
            final String searchText = StringUtils
                    .defaultString(searchQueryMatcher.group(1), searchQueryMatcher.group(2)).trim();
            if (!searchText.isEmpty()) {
                searchTexts.add(searchText);
            }
        }
        return searchPosts(searchTexts);
    }

    SearchResult searchPosts(final List<String> searchTexts) {
        final var searchKeywords = new ArrayList<String>();
        final var searchAuthors = new ArrayList<String>();
        final var searchTags = new ArrayList<String>();

        final Map<String, String> authorsNameIdMap = searchDao
                .getAuthorsWithNameIdMap(() -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
        final Map<String, String> tagsNameIdMap = searchDao
                .getTagsWithNameIdMap(() -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER));

        new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        searchTexts.forEach(searchText -> {
            final String searchAuthorId = authorsNameIdMap.get(searchText);
            if (searchAuthorId != null) {
                searchAuthors.add(searchAuthorId);
                return;
            }
            final String searchTagId = tagsNameIdMap.get(searchText);
            if (searchTagId != null) {
                searchTags.add(searchTagId);
                return;
            }
            searchKeywords.add(searchText);
        });

        final List<Post> posts = searchDao.getPosts(searchKeywords, searchAuthors, searchTags);

        final List<MatchedPost> matchedPosts = posts.stream()
                .map(post -> ImmutableMatchedPost.builder().title(post.title()).slug(post.slug())
                        .summary(SimpleAbbreviator.abbreviate(StringUtils.remove(post.plaintext(), '\n'),
                                MAX_SUMMARY_LENGTH, searchKeywords))
                        .updatedAt(post.updatedAt()).build())
                .collect(Collectors.toList());
        final var keywords = new ArrayList<String>(searchKeywords.size() + searchAuthors.size() + searchTags.size());
        keywords.addAll(searchKeywords);
        keywords.addAll(searchAuthors);
        keywords.addAll(searchTags);
        return ImmutableSearchResult.builder().posts(matchedPosts).keywords(keywords).build();
    }

}
