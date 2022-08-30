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

    @Override
    @Transactional(readOnly = true)
    public Page<Notification> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Notification> findAll(Long userId, Pageable pageable) {
        return notificationRepository.findAll(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Notification> findAll(Long userId, Boolean isViewed, Pageable pageable) {
        return notificationRepository.findAll(userId, isViewed, pageable);
    }
}
