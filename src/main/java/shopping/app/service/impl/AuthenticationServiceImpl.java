package shopping.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping.app.model.Role;
import shopping.app.model.User;
import shopping.app.service.AuthenticationService;
import shopping.app.service.RoleService;
import shopping.app.service.ShoppingCartService;
import shopping.app.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final ShoppingCartService shoppingCartService;

    @Override
    public User register(String username, String password) {
        User user = new User();
        Role role = roleService.getByName(Role.RoleName.USER);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user = userService.create(user);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }
}
