package shopping.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shopping.app.model.Product;
import shopping.app.model.User;
import shopping.app.service.ProductService;
import shopping.app.service.ShoppingCartService;
import shopping.app.service.UserService;

@RestController
@RequestMapping("/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final ProductService productService;

    @PutMapping
    public void addToCart(Authentication authentication,
                          @RequestParam Long productId,
                          @RequestParam Long quantity) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        String username = details.getUsername();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(
                        "There is no user with this username " + username));
        Product product = productService.get(productId);
        shoppingCartService.addProductOrder(product, quantity, user);
    }
}
