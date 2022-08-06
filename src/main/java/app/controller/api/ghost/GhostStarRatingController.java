package app.controller.api.ghost;

import app.connectivity.db.PostStarRateDao;
import app.data.connectivity.db.app.PostStarRate;
import app.data.controller.response.GhostStarRatingResponse;
import app.data.controller.response.ImmutableGhostStarRatingResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/ghost/star-rating")
@Validated
public class GhostStarRatingController {

    private static final Logger logger = LoggerFactory.getLogger(GhostStarRatingController.class);

    @Autowired
    PostStarRateDao postStarRateDao;

    @GetMapping
    @CrossOrigin
    public GhostStarRatingResponse get(@RequestParam("postId") @NotNull @Size(min = 24, max = 24) String postId,
            @RequestParam("uid") @NotNull @Size(min = 36, max = 36) final String uid) {
        logger.debug("StarRating: postId={}, uid={}", postId, uid);

        final Optional<Integer> score = postStarRateDao.getScore(postId, uid);
        if (score.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ImmutableGhostStarRatingResponse.builder().score(score.get()).build();
    }

    @PutMapping
    @CrossOrigin
    public void put(@RequestParam("postId") @NotNull @Size(min = 24, max = 24) String postId,
            @RequestParam("uid") @NotNull @Size(min = 36, max = 36) final String uid,
            @RequestParam("score") @NotNull @Min(0) @Max(10) final int score) {
        logger.debug("StarRating: postId={}, uid={}, score={}", postId, uid, score);

        postStarRateDao.upsertScore(postId, uid, score);
    }

    @GetMapping(path="/getAllRates", produces = "text/plain")
    public String getAllRates() {
        final List<PostStarRate> allRates = postStarRateDao.getAll();
        return allRates.stream()
                .map(rate -> StringUtils.joinWith("\t", rate.postId(), rate.postTitle(), rate.uid(), rate.score()))
                .collect(Collectors.joining("\n"));
    }

}
