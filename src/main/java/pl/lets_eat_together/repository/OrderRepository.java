package pl.lets_eat_together.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lets_eat_together.model.Order;


@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Long> {

}