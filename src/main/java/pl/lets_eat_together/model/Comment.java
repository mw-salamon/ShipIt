package pl.lets_eat_together.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name= "comments")
public class Comment extends Post{

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "order_id",
                nullable = false)
    private Order order;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id",
                nullable = false)
    private UserModel user;
}
