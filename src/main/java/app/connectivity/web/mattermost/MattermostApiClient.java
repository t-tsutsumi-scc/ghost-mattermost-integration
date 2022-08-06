package app.connectivity.web.mattermost;

import app.config.MattermostFeignConfiguration;
import app.data.connectivity.web.mattermost.request.CreatePostRequest;
import app.data.connectivity.web.mattermost.response.CreateDirectMessageChannelResponse;
import app.data.connectivity.web.mattermost.response.CreatePostResponse;
import app.data.connectivity.web.mattermost.response.GetMeResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.validation.Valid;

@FeignClient(name = "mattermost", url = "${app.mattermost.api.url}/api/v4",
        configuration = MattermostFeignConfiguration.class)
@Validated
public interface MattermostApiClient {

    @GetMapping("/users/me")
    ResponseEntity<GetMeResponse> getMe();

    @PostMapping(value = "/channels/direct", consumes = "application/json")
    ResponseEntity<CreateDirectMessageChannelResponse> createDirectMessageChannel(
            @Valid @RequestBody List<String> twoUserIds);

    @PostMapping(value = "/posts", consumes = "application/json")
    ResponseEntity<CreatePostResponse> createPost(@Valid @RequestBody CreatePostRequest body);

}
