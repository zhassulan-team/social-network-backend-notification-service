package kata.academy.eurekanotificationservice.rest.outer;

import kata.academy.eurekanotificationservice.entity.Notification;
import kata.academy.eurekanotificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationRestController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<Page<Notification>> getNotificationPage(@RequestParam(required = false) Boolean isViewed,
                                                                  @RequestHeader @Positive Long userId,
                                                                  Pageable pageable) {
        return ResponseEntity.ok(notificationService.findAllByIsViewedAndRecipientId(isViewed, userId, pageable));
    }

    @PutMapping
    public ResponseEntity<Void> viewAllNotifications(@RequestHeader @Positive Long userId) {
        notificationService.viewAllNotifications(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<Void> viewNotification(@PathVariable @Positive Long notificationId,
                                                 @RequestHeader @Positive Long userId) {
        notificationService.viewNotification(notificationId, userId);
        return ResponseEntity.ok().build();
    }
}
