package kata.academy.notification.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationResponseDto {

    private Long notificationId;
    private String text;
    private Long senderProfileId;
    private String senderUsername;
}
