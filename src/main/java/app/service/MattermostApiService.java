package app.service;

import app.connectivity.web.mattermost.MattermostApiClient;
import app.data.connectivity.web.ghost.webhook.PostEvent;
import app.data.connectivity.web.mattermost.request.ImmutableCreatePostRequest;
import app.data.connectivity.web.mattermost.response.CreateDirectMessageChannelResponse;
import app.data.connectivity.web.mattermost.response.CreatePostResponse;
import app.data.connectivity.web.mattermost.response.GetMeResponse;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class MattermostApiService {

    private static final Logger logger = LoggerFactory.getLogger(MattermostApiService.class);

    @Autowired
    MattermostApiClient apiClient;

    public List<String> notifyUpdates(final List<String> targetUsers, final PostEvent postEvent) {
        final String botUserId = getMe().id();

        final var retryableUsers = new ArrayList<String>();
        for (final String targetUser : targetUsers) {
            String dmChannelId = null;
            try {
                dmChannelId = createDirectMessageChannel(botUserId, targetUser).id();
                final String message = postEvent.post().current().url();
                createPost(dmChannelId, message);
            } catch (FeignException ex) {
                logger.error("Error occurred in notify updates. dmChannelId=" + dmChannelId, ex);
                final boolean retry = !Optional.ofNullable(HttpStatus.resolve(ex.status()))
                        .map(HttpStatus::is4xxClientError).orElse(false);
                if (retry) {
                    retryableUsers.add(targetUser);
                }
            }
        }
        return retryableUsers;
    }

    @Cacheable("mattermostBotUserId")
    public GetMeResponse getMe() {
        return apiClient.getMe().getBody();
    }

    public CreateDirectMessageChannelResponse createDirectMessageChannel(final String userId1, final String userId2) {
        return apiClient.createDirectMessageChannel(Arrays.asList(userId1, userId2)).getBody();
    }

    public CreatePostResponse createPost(final String channelId, final String message) {
        return apiClient.createPost(ImmutableCreatePostRequest.builder().channelId(channelId).message(message).build())
                .getBody();
    }

}
