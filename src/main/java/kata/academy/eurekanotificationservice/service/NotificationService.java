package kata.academy.eurekanotificationservice.service;

import kata.academy.eurekanotificationservice.model.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    Page<Notification> findAll(Pageable pageable);

    Page<Notification> findAll(Long userId, Pageable pageable);

    Page<Notification> findAll(Long userId, Boolean isViewed, Pageable pageable);
}
