package shopping.app.service;

import shopping.app.model.Product;
import shopping.app.model.ShoppingCart;
import shopping.app.model.User;

public interface ShoppingCartService {
    void addProductOrder(Product product, Long quantity, User user);

    ShoppingCart getByUser(User user);

    void registerNewShoppingCart(User user);

    void clear(ShoppingCart shoppingCart);
}
