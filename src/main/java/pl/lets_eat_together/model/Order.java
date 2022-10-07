package pl.lets_eat_together.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name= "orders")
public class Order extends Post{

    @Column(nullable = false)
    String restaurant;

    @Column(nullable = false)
    LocalDateTime callDeadline;

    @Enumerated
    @Column(name = "status",
            nullable = false)
    Status status = Status.COLLECTING_SUBORDERS;

    @Column(nullable = false)
    String pickUpPlace;

    @Column(nullable = false)
    int maxComments = 3;

}
