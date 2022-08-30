package kata.academy.eurekanotificationservice.service.impl;

import kata.academy.eurekanotificationservice.model.entity.Notification;
import kata.academy.eurekanotificationservice.repository.NotificationRepository;
import kata.academy.eurekanotificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<Notification> findAllByRecipientId(Long recipientId, Pageable pageable) {
        return notificationRepository.findAllByRecipientId(recipientId, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Notification> findAllByIsViewedAndRecipientId(Boolean isViewed, Long recipientId, Pageable pageable) {
        return notificationRepository.findAllByIsViewedAndRecipientId(isViewed, recipientId, pageable);
    }
}
