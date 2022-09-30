package kata.academy.eurekanotificationservice.scheduler;

import kata.academy.eurekanotificationservice.repository.NotificationRepository;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class ScheduledTasks {

    private final NotificationRepository notificationRepository;

    @Value("${days.to.delete}")
    private Long DAYS_TO_DELETE;

    public ScheduledTasks(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Scheduled(cron = "${delete.notifications.cron}")
    @Transactional
    public void deleteOldNotification() {
        notificationRepository.deleteAllByTimeIsBefore(LocalDateTime.now().minusDays(DAYS_TO_DELETE));
    }
}
