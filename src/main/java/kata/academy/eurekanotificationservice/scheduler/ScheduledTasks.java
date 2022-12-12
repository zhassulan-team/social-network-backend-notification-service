package kata.academy.eurekanotificationservice.scheduler;

import kata.academy.eurekanotificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 1 * * *")
    public void reportCurrentTime() {
        notificationService.deleteNotificationsByDate();
    }
}
