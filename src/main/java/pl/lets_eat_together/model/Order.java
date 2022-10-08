package pl.lets_eat_together.model;

import lombok.*;
import org.hibernate.annotations.Fetch;

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
               cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id",
                nullable = false)
    private UserModel user;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "office_id",
                nullable = false)
    private Office office;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "orders_to_payments",
               joinColumns = @JoinColumn(name = "order_id",
                                         referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "payment_id",
                                                referencedColumnName = "id"))
    private Set<Payment> payments = new HashSet<>();


    public String getAllPaymentMethods(){
        StringBuilder paymentsMethods = new StringBuilder();
        for (Payment payment: this.getPayments()) {
            paymentsMethods.append(payment.toString())
                           .append(", ");
        }
        return paymentsMethods.toString();
    }

}
