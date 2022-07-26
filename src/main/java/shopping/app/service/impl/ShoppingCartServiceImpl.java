package shopping.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping.app.model.Product;
import shopping.app.model.ProductOrder;
import shopping.app.model.ShoppingCart;
import shopping.app.model.User;
import shopping.app.repository.ProductOrderRepository;
import shopping.app.repository.ShoppingCartRepository;
import shopping.app.service.ShoppingCartService;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductOrderRepository productOrderRepository;

    @Override
    public void addProductOrder(Product product, Long quantity, User user) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setProduct(product);
        productOrder.setQuantity(quantity);
        ShoppingCart shoppingCart = shoppingCartRepository.getShoppingCartByUser(user);
        shoppingCart.getProductOrders().add(productOrderRepository.save(productOrder));
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartRepository.getShoppingCartByUser(user);
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.setProductOrders(null);
        shoppingCartRepository.save(shoppingCart);
    }
}
