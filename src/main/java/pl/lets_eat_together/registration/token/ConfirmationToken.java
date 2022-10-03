package pl.lets_eat_together.registration.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lets_eat_together.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdTime;

    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedTime;
    @ManyToOne
    @JoinColumn(nullable = false,
            name = "user_id")
    private User user;

    public ConfirmationToken(String token, LocalDateTime createdTime, LocalDateTime expiresAt, User user) {
        this.token = token;
        this.createdTime = createdTime;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
