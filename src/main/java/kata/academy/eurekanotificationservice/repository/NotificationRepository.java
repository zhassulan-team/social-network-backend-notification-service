package kata.academy.eurekanotificationservice.repository;

import kata.academy.eurekanotificationservice.model.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findAllByRecipientId(Long recipientId, Pageable pageable);

    Page<Notification> findAllByIsViewedAndRecipientId(Boolean isViewed, Long recipientId, Pageable pageable);
}
