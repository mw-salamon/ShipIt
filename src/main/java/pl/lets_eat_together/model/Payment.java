package pl.lets_eat_together.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name= "payments")
public class Payment extends BaseEntity{

    @Column(name = "name",
            nullable = false)
    private String name;

    @ManyToMany(mappedBy = "payments")
    private Set<Order> orders = new HashSet<>();
}
