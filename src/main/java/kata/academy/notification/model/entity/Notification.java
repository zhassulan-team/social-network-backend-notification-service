package kata.academy.notification.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Long recipientId; //Profile::accountId

    @Positive
    private Long senderProfileId; //Profile::id

    @NotBlank
    @Column(nullable = false)
    private String text;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification notification = (Notification) o;
        return Objects.equals(id, notification.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
