package kata.academy.eurekanotificationservice.rest.inner;

import kata.academy.eurekanotificationservice.api.Response;
import kata.academy.eurekanotificationservice.model.entity.Notification;
import kata.academy.eurekanotificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/internal/v1/notification")
public class NotificationInternalRestController {
    private final NotificationRepository notificationRepository;

    @PostMapping("/{recipientId}")
    Response<Void> sendNotification(@PathVariable @Positive Long recipientId, @RequestParam String text) {
        notificationRepository.save(new Notification(recipientId, text, LocalDateTime.now(), false));
        return Response.ok();
    }
}
