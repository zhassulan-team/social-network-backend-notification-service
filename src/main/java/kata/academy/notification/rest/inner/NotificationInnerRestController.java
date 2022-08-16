package kata.academy.notification.rest.inner;

import kata.academy.notification.model.dto.NotificationPersistRequestDto;
import kata.academy.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/inner/v1/notifications")
public class NotificationInnerRestController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Void> persistNotification(@RequestBody @Valid NotificationPersistRequestDto dto) {
        //Метод должен сохранить уведомление в базе данных
        //Для перевода из dto в entity использовать NotificationMapper::toEntity(NotificationPersistRequestDto dto)
        //Метод должен находится в NotificationService::persistNotification(NotificationPersistRequestDto dto)
        return null;
    }
}
