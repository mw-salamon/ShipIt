package pl.lets_eat_together.model;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Column(updatable = false,
            nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY,
                    generator = "native")
    @GenericGenerator(name = "native",
                      strategy = "native")
    private Long id;
}