package pl.lets_eat_together.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lets_eat_together.model.Order;
import pl.lets_eat_together.model.Payment;


@Repository("paymentRepository")
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}