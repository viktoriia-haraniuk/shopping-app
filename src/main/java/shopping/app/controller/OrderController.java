package shopping.app.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.app.dto.response.OrderResponseDto;
import shopping.app.model.Order;
import shopping.app.model.ShoppingCart;
import shopping.app.model.User;
import shopping.app.service.OrderService;
import shopping.app.service.ShoppingCartService;
import shopping.app.service.UserService;
import shopping.app.service.mapper.ResponseDtoMapper;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;
    private final UserService userService;
    private final ResponseDtoMapper<OrderResponseDto, Order> orderResponseDtoMapper;

    @PostMapping("/complete")
    public OrderResponseDto completeOrder(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(
                        "There is no user with this username " + username));
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        Order order = orderService.completeOrder(shoppingCart);
        orderService.deleteUnpaidOrder(order);
        return orderResponseDtoMapper.mapToDto(order);
    }

    @PutMapping("/complete/pay/{id}")
    public OrderResponseDto payOrder(@PathVariable Long id) {
        Order order = orderService.payOrder(id);
        return orderResponseDtoMapper.mapToDto(order);
    }

    @GetMapping
    public List<OrderResponseDto> getOrderHistory(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(
                        "There is no user with this username " + username));
        return orderService.getOrderHistory(user)
                .stream()
                .map(orderResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
