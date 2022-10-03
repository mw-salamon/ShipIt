package pl.lets_eat_together.registration;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private String firstName;
    private String sureName;
    private String email;
    private String password;
}
