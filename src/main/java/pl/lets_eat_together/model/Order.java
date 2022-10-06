package pl.lets_eat_together.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Order extends Post{

    @Setter
    @Column(nullable = false)
    String restaurant;

    @Setter
    @Column(nullable = false)
    LocalDateTime callDeadline;

    @Setter(AccessLevel.NONE)
    @Enumerated
    @Column(name = "status",
            nullable = false)
    Status status;

    @Setter
    @Column(nullable = false)
    String pickUpPlace;

    @Setter
    @Column(nullable = false)
    int maxComments;

}
