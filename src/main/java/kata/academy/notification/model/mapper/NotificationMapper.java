package kata.academy.notification.model.mapper;

import kata.academy.notification.model.dto.NotificationPersistRequestDto;
import kata.academy.notification.model.entity.Notification;

public final class NotificationMapper {

    private NotificationMapper() {
    }

    public static Notification toEntity(NotificationPersistRequestDto dto) {
        return Notification
                .builder()
                .recipientId(dto.recipientId())
                .senderProfileId(dto.senderProfileId())
                .text(dto.text())
                .build();
    }
}
