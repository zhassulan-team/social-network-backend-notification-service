package kata.academy.eurekanotificationservice.rest.outer;

import kata.academy.eurekanotificationservice.api.Response;
import kata.academy.eurekanotificationservice.model.entity.Notification;
import kata.academy.eurekanotificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationRestController {

    private final NotificationService notificationService;

    @GetMapping
    public Response<Page<Notification>> getNotificationPage(Pageable pageable,
                                                            @RequestParam(required = false) Boolean isViewed,
                                                            @RequestParam @Positive Long userId){
        if (isViewed == null) {
            return Response.ok(notificationService.findAll(pageable));
        } else {
            return Response.ok(notificationService.findById(userId, pageable));
        }
    }

}