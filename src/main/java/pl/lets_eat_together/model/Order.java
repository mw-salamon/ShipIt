package pl.lets_eat_together.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name= "orders")
public class Order extends Post{

    @Column(name = "restaurant",
            nullable = false)
    private String restaurant;

    @Column(name = "call_deadline",
            nullable = false)
    private LocalDateTime callDeadline;

    @Enumerated
    @Column(name = "status",
            nullable = false)
    private Status status = Status.COLLECTING_SUBORDERS;

    @Column(name = "pick_up_place",
            nullable = false)
    private String pickUpPlace;

    @Column(name = "max_comments",
            nullable = false)
    private int maxComments = 3;

    @OneToMany(mappedBy = "order",
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id",
                nullable = false)
    private UserModel user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "office_id",
                nullable = false)
    private Office office;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "orders_to_payments",
               joinColumns = @JoinColumn(name = "order_id",
                                         referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "payment_id",
                                                referencedColumnName = "id"))
    private Set<Payment> payments = new HashSet<>();

}
