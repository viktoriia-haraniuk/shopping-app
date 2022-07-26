package shopping.app.config;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shopping.app.model.Role;
import shopping.app.model.User;
import shopping.app.service.RoleService;
import shopping.app.service.UserService;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleService roleService;
    private final UserService userService;

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setRoleName(Role.RoleName.ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setRoleName(Role.RoleName.USER);
        roleService.add(userRole);
        User user = new User();
        user.setUsername("admin123");
        user.setPassword("admin123");
        user.setRole(adminRole);
        userService.create(user);
    }
}
