package pl.lets_eat_together.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name= "payments")
public class Payment extends BaseEntity{

    @Column(name = "name",
            nullable = false)
    private String name;

    @ManyToMany(mappedBy = "payments")
    @ToString.Exclude
    private Set<Order> orders = new HashSet<>();

    @Override
    public String toString() {
        return name;
    }
}
