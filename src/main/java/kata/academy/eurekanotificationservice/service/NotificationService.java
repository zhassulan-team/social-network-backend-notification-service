package kata.academy.eurekanotificationservice.service;

import kata.academy.eurekanotificationservice.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface NotificationService {

    Page<Notification> findAllByIsViewedAndRecipientId(Boolean isViewed, Long recipientId, Pageable pageable);

    void addNotification(String text, Long recipientId);

    void viewNotification(Long notificationId, Long recipientId);

    void viewAllNotifications(Long recipientId);

    void addNotificationsByMap(Map<Long, String> notificationMap);
}
