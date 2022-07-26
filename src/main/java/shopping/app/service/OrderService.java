package shopping.app.service;

import java.util.List;
import shopping.app.model.Order;
import shopping.app.model.ShoppingCart;
import shopping.app.model.User;

public interface OrderService {
    Order completeOrder(ShoppingCart shoppingCart);

    Order payOrder(Long id);

    void deleteUnpaidOrder(Order order);

    List<Order> getOrderHistory(User user);
}
