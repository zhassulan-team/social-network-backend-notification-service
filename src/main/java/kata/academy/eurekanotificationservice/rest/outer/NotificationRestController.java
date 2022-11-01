package kata.academy.eurekanotificationservice.rest.outer;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "NotificationRestController", description = "Операции с нотификациями")
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationRestController {

    private final NotificationService notificationService;

    @ApiOperation(value = "getNotificationPage", notes = "Получение страницы нотификаций пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное получение страницы нотификаций пользователя"),
            @ApiResponse(code = 400, message = "Ошибка при получении страницы нотификаций пользователя с указанным userId")})
    @GetMapping
    public ResponseEntity<Page<Notification>> getNotificationPage(@RequestParam(required = false) Boolean isViewed,
                                                                  @RequestHeader @Positive Long userId,
                                                                  Pageable pageable) {
        return ResponseEntity.ok(notificationService.findAllByIsViewedAndRecipientId(isViewed, userId, pageable));
    }

    @ApiOperation(value = "viewAllNotification", notes = "Просмотр всех нотификаций пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный просмотр всех нотификаций пользователя"),
            @ApiResponse(code = 400, message = "Нотификаций, принадлежащих пользователю с указанным userId, нет в базе данных")})
    @PutMapping
    public ResponseEntity<Void> viewAllNotifications(@RequestHeader @Positive Long userId) {
        notificationService.viewAllNotifications(userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "viewNotification", notes = "Просмотр нотификации пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный просмотр нотификации пользователя"),
            @ApiResponse(code = 400, message = "Нотификации с указанным notificationId, " +
                    "принадлежащей пользователю с указанным userId, нет в базе данных")})
    @PutMapping("/{notificationId}")
    public ResponseEntity<Void> viewNotification(@PathVariable @Positive Long notificationId,
                                                 @RequestHeader @Positive Long userId) {
        notificationService.viewNotification(notificationId, userId);
        return ResponseEntity.ok().build();
    }
}
