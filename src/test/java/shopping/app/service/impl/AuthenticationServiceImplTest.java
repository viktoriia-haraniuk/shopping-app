package shopping.app.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.app.model.Role;
import shopping.app.model.User;
import shopping.app.service.RoleService;
import shopping.app.service.ShoppingCartService;
import shopping.app.service.UserService;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private ShoppingCartService shoppingCartService;

    private Role userRole;
    private User user;
    private User userToSave;
    @BeforeEach
    void setUp() {
        userRole = new Role();
        userRole.setId(1L);
        userRole.setRoleName(Role.RoleName.USER);
        user = new User(1L, "user123", "user123", userRole);
        userToSave = new User();
        userToSave.setUsername(user.getUsername());
        userToSave.setPassword(user.getPassword());
        userToSave.setRole(user.getRole());
    }

    @Test
    void register_ok() {
        Mockito.when(roleService.getByName(Role.RoleName.USER))
                .thenReturn(userRole);
        Mockito.when(userService.create(userToSave))
                .thenReturn(user);
        User actual = authenticationService.register(userToSave.getUsername(), userToSave.getPassword());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(user.getId(), actual.getId());
        Assertions.assertEquals(user.getUsername(), actual.getUsername());
        Assertions.assertEquals(user.getPassword(), actual.getPassword());
        Assertions.assertEquals(user.getRole(), actual.getRole());
    }
}