package kata.academy.eurekanotificationservice.model.entity;

import lombok.AllArgsConstructor;
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
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long recipientId;

    @NotBlank
    @Column(nullable = false)
    private String text;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime time;

    @NotNull
    @Column(nullable = false)
    private Boolean isViewed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id) && Objects.equals(recipientId, that.recipientId) && Objects.equals(text, that.text) && Objects.equals(time, that.time) && Objects.equals(isViewed, that.isViewed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipientId, text, time, isViewed);
    }
}
