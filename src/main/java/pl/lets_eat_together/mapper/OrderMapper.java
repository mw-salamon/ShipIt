//package pl.lets_eat_together.mapper;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import pl.lets_eat_together.dto.OrderDTO;
//import pl.lets_eat_together.model.Order;
//
//import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
//
//@Mapper(componentModel = SPRING)
//public interface OrderMapper {
//
//    OrderDTO orderToOrderDTO(Order order);
//
//    @Mapping(target = "id",
//             ignore = true)
//    Order orderDTOToOrder(OrderDTO orderDTO);
//
//}