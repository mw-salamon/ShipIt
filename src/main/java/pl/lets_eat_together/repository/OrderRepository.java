package pl.lets_eat_together.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.lets_eat_together.model.Order;
import pl.lets_eat_together.model.Status;

import java.util.List;


@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findAllByOrderByCallDeadlineDesc();

    public List<Order> findAllByStatusOrderByCallDeadlineDesc(Status status);
}