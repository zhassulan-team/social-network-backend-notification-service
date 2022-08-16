package kata.academy.notification.rest.outer;

import kata.academy.notification.api.Data;
import kata.academy.notification.model.dto.NotificationResponseDto;
import kata.academy.notification.pagination.Page;
import kata.academy.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/notification/v1/notifications")
public class NotificationRestController {

    private final NotificationService notificationService;

    @GetMapping("/{pageNumber}")
    public ResponseEntity<Data<Page<NotificationResponseDto>>> getPageNotificationResponseDto(
            @PathVariable @NotNull @Positive Integer pageNumber,
            @RequestParam(defaultValue = "20") @NotNull @Positive Integer items,
            @RequestHeader @NotNull @Positive Long accountId) {
        //Метод должен вернуть уведомления текущего юзера с пагинацией
        //Метод должен собрать дто на уровне Dao, кроме поля senderUsername
        //senderUsername - заполнять на уровне сервиса, для этого необходимо отправить запрос в content-service через FeignClient
        //ContentFeignClient::getProfileUsernamesByProfileIds
        //Метод должен находится в NotificationResponseDtoService::getPageNotificationResponseDto(Long accountId, Map<String, Object> parameters)
        return null;
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> removeNotification(@PathVariable @Positive Long notificationId,
                                                   @RequestHeader @NotNull @Positive Long accountId) {
        //Метод должен удалить уведомление
        //Метод должен провалидировать, что уведомление действительно принадлежит пользователю, который отправил запрос (recipientId)
        //иначе выбросить исключение AccessDeniedException("Нет прав, доступ запрещен") и обработать в AdviceRestController
        //Метод должен находится в NotificationService::removeNotification(Long notificationId, Long accountId)
        return null;
    }
}
