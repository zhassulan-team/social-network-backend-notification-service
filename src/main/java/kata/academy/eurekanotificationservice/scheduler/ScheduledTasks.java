package kata.academy.eurekanotificationservice.scheduler;

import kata.academy.eurekanotificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ScheduledTasks {

    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 1 * * *")
    public void deleteNotificationsOlderThanSixMonths() {
        LocalDateTime now = LocalDateTime.now();
        notificationService.deleteByTimeBetween(LocalDateTime.from(now.minusMonths(6)), now);
    }
}
