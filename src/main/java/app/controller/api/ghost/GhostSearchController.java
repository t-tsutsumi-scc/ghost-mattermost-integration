package app.controller.api.ghost;

import app.data.connectivity.web.ghost.search.SearchResult;
import app.service.GhostSearchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/ghost/search")
public class GhostSearchController {

    private static final Logger logger = LoggerFactory.getLogger(GhostSearchController.class);

    @Autowired
    GhostSearchService ghostSearchService;

    @PostMapping
    @CrossOrigin
    @Validated
    public SearchResult get(@RequestParam("q") @NotEmpty final String searchQuery) {
        logger.debug("Search: q={}", searchQuery);

        return ghostSearchService.searchPosts(searchQuery);
    }

}
