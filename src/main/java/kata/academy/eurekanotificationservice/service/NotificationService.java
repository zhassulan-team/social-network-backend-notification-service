package kata.academy.eurekanotificationservice.service;

import kata.academy.eurekanotificationservice.model.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    Page<Notification> findAllByIsViewedAndRecipientId(Boolean isViewed, Long recipientId, Pageable pageable);

    void addNotification(String text, Long recipientId);

    void viewNotification(Long notificationId, Long recipientId);

    void viewAllNotifications(Long recipientId);
}
