package kata.academy.eurekanotificationservice.service.impl;

import kata.academy.eurekanotificationservice.model.entity.Notification;
import kata.academy.eurekanotificationservice.repository.NotificationRepository;
import kata.academy.eurekanotificationservice.service.NotificationService;
import kata.academy.eurekanotificationservice.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<Notification> findAllByIsViewedAndRecipientId(Boolean isViewed, Long recipientId, Pageable pageable) {
        if (isViewed != null) {
            return notificationRepository.findAllByIsViewedAndRecipientId(isViewed, recipientId, pageable);
        }
        return notificationRepository.findAllByRecipientId(recipientId, pageable);
    }

    @Override
    public void addNotification(String text, Long recipientId) {
        notificationRepository.save(
                Notification.builder()
                        .recipientId(recipientId)
                        .text(text)
                        .isViewed(false)
                        .time(LocalDateTime.now())
                        .build());
    }

    @Override
    public void viewNotification(Long notificationId, Long recipientId) {
        Optional<Notification> notificationOptional = notificationRepository.findByIdAndRecipientId(notificationId, recipientId);
        ApiValidationUtil.requireTrue(notificationOptional.isPresent(), String.format("Уведомление с notificationId %d и userId %d нет в базе данных", notificationId, recipientId));
        Notification notification = notificationOptional.get();
        notification.setIsViewed(true);
        notificationRepository.save(notification);
    }

    @Override
    public void viewAllNotifications(Long recipientId) {
        notificationRepository.viewAllNotifications(recipientId);
    }

    @Override
    public void deleteByTimeBetween(LocalDateTime from, LocalDateTime now) {
        notificationRepository.deleteByTimeBetween(from, now);
    }
}
