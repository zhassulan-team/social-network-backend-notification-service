package kata.academy.eurekanotificationservice.rest.outer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kata.academy.eurekanotificationservice.api.Response;
import kata.academy.eurekanotificationservice.model.entity.Notification;
import kata.academy.eurekanotificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notification", description = "The Notification API")
public class NotificationRestController {

    private final NotificationService notificationService;

    @Operation(summary = "Get page with notifications",
            parameters = {
                    @Parameter(name = "isViewed", description = "Marker that notification has been already read"),
                    @Parameter(name = "userId", description = "User id", required = true),
                    @Parameter(name = "pageable", description = "Pagination information", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notifications page received successfully",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Request is invalid",
                            content = @Content)
            }
    )
    @GetMapping
    public Response<Page<Notification>> getNotificationPage(@RequestParam(required = false) Boolean isViewed,
                                                            @RequestParam @Positive Long userId,
                                                            Pageable pageable) {
        return Response.ok(notificationService.findAllByIsViewedAndRecipientId(isViewed, userId, pageable));
    }

    @Operation(summary = "View all notifications",
            parameters = {
                    @Parameter(name = "userId", description = "User id", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "All notifications viewed successfully",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Request is invalid",
                            content = @Content)
            }
    )
    @PutMapping
    public Response<Void> viewAllNotifications(@RequestParam @Positive Long userId) {
        notificationService.viewAllNotifications(userId);
        return Response.ok();
    }

    @Operation(summary = "View specific notification",
            parameters = {
                    @Parameter(name = "notificationId", description = "Notification id", required = true),
                    @Parameter(name = "userId", description = "User id", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notification viewed successfully",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Request is invalid",
                            content = @Content)
            }
    )
    @PutMapping("/{notificationId}")
    public Response<Void> viewNotification(@PathVariable @Positive Long notificationId,
                                           @RequestParam @Positive Long userId) {
        notificationService.viewNotification(notificationId, userId);
        return Response.ok();
    }
}
