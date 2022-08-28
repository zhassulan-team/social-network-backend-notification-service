package kata.academy.eurekanotificationservice.repository;

import kata.academy.eurekanotificationservice.model.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findById(Long userId, Pageable pageable);
}
