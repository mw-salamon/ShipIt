package pl.lets_eat_together.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;
import pl.lets_eat_together.model.Status;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;

    @NotBlank(message = "Please provide restaurant name")
    private String restaurant;

    @NotNull(message = "Please provide your order deadline")
    private LocalDateTime callDeadline;

    private Status status = Status.COLLECTING_SUBORDERS;

    @NotBlank(message = "Please provide pick up place")
    private String pickUpPlace;

    private int maxComments = 3;
}
