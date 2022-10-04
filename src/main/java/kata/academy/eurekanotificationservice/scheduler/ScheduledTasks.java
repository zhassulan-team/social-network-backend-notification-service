package kata.academy.eurekanotificationservice.scheduler;

import kata.academy.eurekanotificationservice.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class ScheduledTasks {


    private final NotificationService notificationService;

    private static final int daysToDelete = 180;

    public ScheduledTasks(NotificationService notificationService) {

        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 1 * * * ")
    @Transactional
    public void deleteOldNotification() {
        notificationService.findAll(LocalDateTime.now().minusDays(daysToDelete));
    }
}
