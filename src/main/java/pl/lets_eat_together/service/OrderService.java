package pl.lets_eat_together.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.lets_eat_together.exception.PastDateException;
import pl.lets_eat_together.model.Order;
import pl.lets_eat_together.repository.OrderRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(@Qualifier("orderRepository") OrderRepository orderRepository) {
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

    public Order addNewOrder(Order newOrder) throws PastDateException {
        if(newOrder.getCallDeadline().isAfter(LocalDateTime.now())){
            return orderRepository.saveAndFlush(newOrder);
        }else{
            throw new PastDateException();
        }
    }

    @Transactional
    public Order updateOrderStatus(Order orderToUpdate) {
        Optional<Order> found = orderRepository.findById(orderToUpdate.getId());
        Order oldOrder = found.orElseThrow();
        oldOrder.setMeal(orderToUpdate.getMeal());
        oldOrder.setNote(orderToUpdate.getNote());
        oldOrder.setRestaurant(orderToUpdate.getRestaurant());
        oldOrder.setCallDeadline(orderToUpdate.getCallDeadline());
        oldOrder.setPickUpPlace(orderToUpdate.getPickUpPlace());
        oldOrder.setOffice(orderToUpdate.getOffice());
        oldOrder.setPayments(orderToUpdate.getPayments());
        oldOrder.setStatus(orderToUpdate.getStatus());
        oldOrder.setMaxComments(orderToUpdate.getMaxComments());
        return orderRepository.save(oldOrder);
    }

    public String deleteOrder(Long id){
         Order order = getOrderById(id);
         orderRepository.delete(order);
         return "Order with id=" + id + " deleted";
    }

}
