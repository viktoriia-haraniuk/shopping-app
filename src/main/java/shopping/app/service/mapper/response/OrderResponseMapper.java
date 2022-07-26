package shopping.app.service.mapper.response;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import shopping.app.dto.response.OrderResponseDto;
import shopping.app.model.Order;
import shopping.app.model.ProductOrder;
import shopping.app.service.mapper.ResponseDtoMapper;

@Component
public class OrderResponseMapper implements ResponseDtoMapper<OrderResponseDto, Order> {

    @Override
    public OrderResponseDto mapToDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setProductOrdersIds(
                order.getProductOrders().stream()
                        .map(ProductOrder::getId)
                        .collect(Collectors.toList()));
        orderResponseDto.setCreationTime(order.getCreationTime());
        orderResponseDto.setUserId(order.getUser().getId());
        orderResponseDto.setPaid(order.isPaid());
        return orderResponseDto;
    }
}
