package app.controller.auth;

import app.connectivity.db.UsersDao;
import app.data.connectivity.db.app.ImmutableUserPreferences;
import app.data.connectivity.db.app.UserPreferences;
import app.data.connectivity.web.ghost.content.response.AuthorsResponse;
import app.data.connectivity.web.ghost.content.response.TagsResponse;
import app.data.controller.request.ImmutableNotificationPreferencesForm;
import app.data.controller.request.NotificationPreferencesForm;
import app.service.GhostApiService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth/notification-preferences")
public class NotificationPreferencesController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationPreferencesController.class);

    @Autowired
    GhostApiService ghostApiService;

    @Autowired
    UsersDao usersDao;

    @GetMapping
    public String get(@AuthenticationPrincipal final OAuth2User principal, final Model model) {
        final var tags = ghostApiService.getTags();
        final var authors = ghostApiService.getAuthors();

        final Optional<UserPreferences.ValueType> userPreferences = usersDao
                .getPreferences(principal.getAttribute("id"));
        final NotificationPreferencesForm form = userPreferences
                .map(prefs -> ImmutableNotificationPreferencesForm.builder().all(prefs.all()).tags(prefs.tags())
                        .authors(prefs.authors()).build())
                .orElseGet(() -> ImmutableNotificationPreferencesForm.builder().build());

        model.addAttribute("username", principal.getAttribute("username"));
        model.addAttribute("tags", tags.tags());
        model.addAttribute("authors", authors.authors());
        model.addAttribute("form", form);

        if (logger.isDebugEnabled()) {
            logger.debug("#get model={}", model);
        }

        return "auth/notification-preferences";
    }

    @PostMapping
    public String post(@AuthenticationPrincipal final OAuth2User principal,
            @Validated ImmutableNotificationPreferencesForm baseForm, final Model model) {
        final var tags = ghostApiService.getTags();
        final var authors = ghostApiService.getAuthors();

        final NotificationPreferencesForm form = syncFormWithGhostData(baseForm, tags, authors);
        usersDao.upsertPreferences(principal.getAttribute("id"), ImmutableUserPreferences.ValueType.builder()
                .all(form.all()).tags(form.tags()).authors(form.authors()).build());

        model.addAttribute("username", principal.getAttribute("username"));
        model.addAttribute("tags", tags.tags());
        model.addAttribute("authors", authors.authors());
        model.addAttribute("form", form);
        model.addAttribute("updated", Boolean.TRUE);

        if (logger.isDebugEnabled()) {
            logger.debug("#post model={}", model);
        }

        return "auth/notification-preferences";
    }

    @ModelAttribute(name = "returnUrl")
    String bindReturnUrl(@RequestParam final String returnUrl) {
        return returnUrl;
    }

    NotificationPreferencesForm syncFormWithGhostData(final NotificationPreferencesForm form, final TagsResponse tags,
            final AuthorsResponse authors) {
        return ImmutableNotificationPreferencesForm.builder().all(form.all())
                .tags(form.tags().stream()
                        .filter(tag -> tags.tags().stream().anyMatch(tagMaster -> tag.equals(tagMaster.id())))
                        .collect(Collectors.toList()))
                .authors(form.authors().stream().filter(
                        author -> authors.authors().stream().anyMatch(authorMaster -> author.equals(authorMaster.id())))
                        .collect(Collectors.toList()))
                .build();
    }

}
