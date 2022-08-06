package app.service;

import app.connectivity.web.ghost.GhostContentApiClient;
import app.data.connectivity.web.ghost.content.request.ImmutableContentApiParameters;
import app.data.connectivity.web.ghost.content.response.AuthorsResponse;
import app.data.connectivity.web.ghost.content.response.TagsResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GhostApiService {

    @Autowired
    GhostContentApiClient apiClient;

    public TagsResponse getTags() {
        return apiClient.getTags(ImmutableContentApiParameters.builder().include("count.posts").limit("all")
                .order("count.posts%20desc").build()).getBody();
    }

    public AuthorsResponse getAuthors() {
        return apiClient.getAuthors(ImmutableContentApiParameters.builder().include("count.posts").limit("all")
                .order("count.posts%20desc").build()).getBody();
    }

}
