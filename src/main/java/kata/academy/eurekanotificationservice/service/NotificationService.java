package kata.academy.eurekanotificationservice.service;

import kata.academy.eurekanotificationservice.model.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    Notification addNotification(Notification notification);

    Notification updateNotification(Notification notification);

    void deleteById(Long notificationId);

    Page<Notification> findAll(Pageable pageable);

    Page<Notification> findById(Long userId, Pageable pageable);
}
