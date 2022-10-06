//package pl.lets_eat_together.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import pl.lets_eat_together.dto.OrderDTO;
//import pl.lets_eat_together.mapper.OrderMapper;
//import pl.lets_eat_together.model.Order;
//import pl.lets_eat_together.service.OrderService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//public class OrderController {
//
//    private final OrderService orderService;
//    private final OrderMapper orderMapper;
//
//    @Autowired
//    public OrderController(@Qualifier("orderService") OrderService orderService,
//                   @Qualifier("orderMapper") OrderMapper orderMapper) {
//        this.orderService = orderService;
//        this.orderMapper = orderMapper;
//    }
//
//    @GetMapping(value = "/orders")
//    public List<OrderDTO> getAllOrders() {
//        List<Order> allOrders = orderService.getAllOrders();
//
//        return allOrders.stream()
//                             .map(orderMapper::orderToOrderDTO)
//                             .collect(Collectors.toList());
//    }
//}
