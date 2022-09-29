package kata.academy.eurekanotificationservice.rest.inner;

import kata.academy.eurekanotificationservice.api.Response;
import kata.academy.eurekanotificationservice.model.entity.Notification;
import kata.academy.eurekanotificationservice.repository.NotificationRepository;
import kata.academy.eurekanotificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/internal/v1/notifications")
public class NotificationInternalRestController {
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    @PostMapping("/{recipientId}")
    Response<Void> sendNotification(@PathVariable @Positive Long recipientId, @RequestParam String text) {
        notificationRepository.save(new Notification(recipientId, text, LocalDateTime.now(), false));
        return Response.ok();
    }

    @PostMapping
    public ResponseEntity<Void> addNotification(@RequestParam @NotBlank String text,
                                                @RequestParam @Positive Long recipientId) {
        notificationService.addNotification(text, recipientId);
        return ResponseEntity.ok().build();
    }
}
