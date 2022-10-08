package pl.lets_eat_together.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name= "users")
public class UserModel extends BaseEntity{

    @Column(name = "email",
            nullable = false,
            unique = true)
    private String email;

    @Column(name = "password",
            nullable = false)
    private String password;

    @Column(name = "first_name",
            nullable = false)
    private String firstName;

    @Column(name = "sure_name",
            nullable = true)
    private String sureName;

    @OneToMany(mappedBy = "user",
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user",
               cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();
}
