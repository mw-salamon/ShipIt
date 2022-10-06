package pl.lets_eat_together.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class Post extends BaseEntity{

    @Setter
    String meal;

    @Setter
    @Column(name = "note",
            length = 65535)
    String note;

}
