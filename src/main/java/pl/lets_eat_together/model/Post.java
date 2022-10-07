package pl.lets_eat_together.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class Post extends BaseEntity{

    @Column(name = "meal",
            nullable = false)
    String meal;

    @Column(name = "note",
            length = 65535,
            columnDefinition = "TEXT")
    String note;

}
