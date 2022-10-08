package pl.lets_eat_together.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name= "orders")
public class Notification extends BaseEntity{

    @Column(name = "name",
            nullable = false,
            unique = true)
    private String name;

    @Column(name = "content",
            nullable = false,
            columnDefinition = "TEXT")
    private String content;
}
