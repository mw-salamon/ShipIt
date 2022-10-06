package pl.lets_eat_together.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Post extends BaseEntity{

    @Setter(AccessLevel.NONE)
    String meal;

    @Column(name = "note",
            length = 65535,
            columnDefinition = "BLOB")
    String note;

}
