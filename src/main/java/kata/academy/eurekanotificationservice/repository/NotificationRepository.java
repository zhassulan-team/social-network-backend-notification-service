package kata.academy.eurekanotificationservice.repository;

import kata.academy.eurekanotificationservice.model.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import java.time.LocalDateTime;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findAllByRecipientId(Long recipientId, Pageable pageable);

    Page<Notification> findAllByIsViewedAndRecipientId(Boolean isViewed, Long recipientId, Pageable pageable);

    void deleteAllByTimeIsBefore(LocalDateTime time);

    Optional<Notification> findByIdAndRecipientId(Long notificationId, Long recipientId);

    @Modifying
    @Query("""
            UPDATE Notification n
            SET n.isViewed = true
            WHERE n.recipientId = :recipientId
                                """)
    void viewAllNotifications(Long recipientId);
}
