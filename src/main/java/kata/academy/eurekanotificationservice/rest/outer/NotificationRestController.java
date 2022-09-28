package kata.academy.eurekanotificationservice.rest.outer;

import kata.academy.eurekanotificationservice.api.Response;
import kata.academy.eurekanotificationservice.model.entity.Notification;
import kata.academy.eurekanotificationservice.repository.NotificationRepository;
import kata.academy.eurekanotificationservice.service.NotificationService;
import kata.academy.eurekanotificationservice.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationRestController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @GetMapping
    public Response<Page<Notification>> getNotificationPage(@RequestParam(required = false) Boolean isViewed,
                                                            @RequestParam @Positive Long userId,
                                                            Pageable pageable) {
        if (isViewed != null) {
            return Response.ok(notificationService.findAllByIsViewedAndRecipientId(isViewed, userId, pageable));
        }
        return Response.ok(notificationService.findAllByRecipientId(userId, pageable));
    }

    @PutMapping()
    Response<Void> viewAllNotifications(@RequestParam @Positive Long userId) {
        Boolean isViewed = true;
        List<Notification> notificationList = notificationService.findAllByRecipientId(userId);
        for (Notification notification : notificationList) {
            notification.setIsViewed(isViewed);
            notificationRepository.save(notification);
        }
        return Response.ok();
    }

    @PutMapping("/{notificationId}")
    Response<Void> viewNotification(@PathVariable @Positive Long notificationId,
                                    @RequestParam @Positive Long userId) {
        Optional<Notification> notificationOptional = notificationService.findByIdAndRecipientId(notificationId, userId);
        ApiValidationUtil.requireTrue(notificationOptional.isPresent(), String.format("Уведомление с notificationId %d и userId %d нет в базе данных", notificationId, userId));
        Notification notification = notificationOptional.get();
        notification.setIsViewed(true);
        notificationRepository.save(notification);
        return Response.ok();
    }
}
