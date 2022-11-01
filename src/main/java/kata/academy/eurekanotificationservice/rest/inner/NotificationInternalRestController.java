package kata.academy.eurekanotificationservice.rest.inner;

import kata.academy.eurekanotificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Map;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/internal/v1/notifications")
public class NotificationInternalRestController {

    private final NotificationService notificationService;

    @PostMapping
    public void addNotification(@RequestBody @NotBlank String text,
                                                @RequestParam @Positive Long recipientId) {
        notificationService.addNotification(text, recipientId);
    }

    @PostMapping("/map")
    public void addNotificationsByMap(@RequestBody Map<Long, String> notificationMap) {
        notificationService.addNotificationsByMap(notificationMap);
    }
}
