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

//    @Query("select * from orders o where o.status=0 order by o.call_deadline DESC")
//    public List<Order> findAllOpen();
//
//    @Query("select * from orders o where o.status=3 or o.status=4 order by o.call_deadline DESC")
//    public List<Order> findAllClosedOrCancelled();

    public List<Order> findAllByStatusOrderByCallDeadlineDesc(Status status);
}