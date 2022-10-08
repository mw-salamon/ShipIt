package pl.lets_eat_together.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name= "offices")
public class Office extends BaseEntity{

    @Column(name = "city",
            nullable = false)
    private String city;

    @Column(name = "street",
            nullable = false)
    private String street;

    @Column(name = "number",
            nullable = false)
    private String number;

    @Column(name = "name",
            nullable = false)
    private String name;

    @OneToMany(mappedBy = "office",
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Order> orders = new ArrayList<>();
}
