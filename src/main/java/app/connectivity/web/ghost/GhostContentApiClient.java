package app.connectivity.web.ghost;

import app.config.GhostFeignConfiguration;
import app.data.connectivity.web.ghost.content.request.ContentApiParameters;
import app.data.connectivity.web.ghost.content.response.AuthorsResponse;
import app.data.connectivity.web.ghost.content.response.TagsResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@FeignClient(name = "ghost", url = "${app.ghost.api.url}/ghost/api/v3/content",
        configuration = GhostFeignConfiguration.class)
@Validated
public interface GhostContentApiClient {

    /**
     * GET /tags/ : Browse tags
     *
     * @param parameters Query parameters Map
     * @return OK (with Meta)
     */
    @GetMapping("/tags/")
    ResponseEntity<TagsResponse> getTags(@Valid @SpringQueryMap ContentApiParameters parameters);

    /**
     * GET /tags/{id} : Read a tag by ID
     *
     * @param id         The tag ID (required)
     * @param parameters Query parameters Map
     * @return OK (without Meta)
     */
    @GetMapping("/tags/{id}")
    ResponseEntity<TagsResponse> getTagsById(@PathVariable("id") String id,
            @Valid @SpringQueryMap ContentApiParameters parameters);

    /**
     * GET /tags/slug/{slug}/ : Read a tag by slug
     *
     * @param slug       The tag slug (required)
     * @param parameters Query parameters Map
     * @return OK (without Meta)
     */
    @GetMapping("/tags/slug/{slug}/")
    ResponseEntity<TagsResponse> getTagsBySlug(@PathVariable("slug") String slug,
            @Valid @SpringQueryMap ContentApiParameters parameters);

    /**
     * GET /authors/ : Browse authors
     *
     * @param parameters Query parameters Map
     * @return OK (with Meta)
     */
    @GetMapping("/authors/")
    ResponseEntity<AuthorsResponse> getAuthors(@Valid @SpringQueryMap ContentApiParameters parameters);

    /**
     * GET /authors/{id} : Read an author by ID
     *
     * @param id         The author ID (required)
     * @param parameters Query parameters Map
     * @return OK (without Meta)
     */
    @GetMapping("/authors/{id}")
    ResponseEntity<AuthorsResponse> getAuthorsById(@PathVariable("id") String id,
            @Valid @SpringQueryMap ContentApiParameters parameters);

    /**
     * GET /authors/slug/{slug}/ : Read an author by slug
     *
     * @param slug       The author slug (required)
     * @param parameters Query parameters Map
     * @return OK (without Meta)
     */
    @GetMapping("/authors/slug/{slug}/")
    ResponseEntity<AuthorsResponse> getAuthorsBySlug(@PathVariable("slug") String slug,
            @Valid @SpringQueryMap ContentApiParameters parameters);

}
