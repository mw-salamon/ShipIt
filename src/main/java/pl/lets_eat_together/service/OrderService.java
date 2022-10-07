package pl.lets_eat_together.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.lets_eat_together.model.Order;
import pl.lets_eat_together.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(@Qualifier("orderRepository") OrderRepository orderRepository
                        ) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        Optional<Order> found = orderRepository.findById(id);
        return found.orElseThrow();
    }

    //TODO proper Exceptions classes

    public Order addNewOrder(Order newOrder){
        return orderRepository.saveAndFlush(newOrder);
    }

    public String deleteOrder(Long id){
         Order order = getOrderById(id);
         orderRepository.delete(order);
         return "Order with id=" + id + " deleted";
    }

}
