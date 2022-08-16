package kata.academy.notification.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record NotificationPersistRequestDto(
        @NotNull @Positive Long recipientId,
        @Positive Long senderProfileId,
        @NotBlank String text) {
}
