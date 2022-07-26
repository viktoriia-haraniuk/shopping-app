package shopping.app.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import shopping.app.model.Order;
import shopping.app.model.ProductOrder;
import shopping.app.model.ShoppingCart;
import shopping.app.model.User;
import shopping.app.repository.OrderRepository;
import shopping.app.service.OrderService;
import shopping.app.service.ShoppingCartService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private static final long DELAY = 600000L;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setCreationTime(LocalDateTime.now());
        order.setProductOrders(shoppingCart.getProductOrders());
        Hibernate.initialize(shoppingCart.getProductOrders());
        order.setUser(shoppingCart.getUser());
        order.setPaid(false);
        shoppingCartService.clear(shoppingCart);
        return orderRepository.save(order);
    }

    @Override
    public Order payOrder(Long id) {
        Order order = orderRepository
                .findById(id).orElseThrow(() -> new RuntimeException("Can't get order with id " + id));
        order.setPaid(true);
        return orderRepository.save(order);
    }

    @Override
    public void deleteUnpaidOrder(Order order) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!order.isPaid()) {
                    orderRepository.delete(order);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, DELAY);
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        return orderRepository.getAllByUser(user);
    }
}
