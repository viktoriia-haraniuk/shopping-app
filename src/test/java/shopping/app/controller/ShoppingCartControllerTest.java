package shopping.app.controller;

import java.math.BigDecimal;
import java.util.Optional;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import shopping.app.model.Product;
import shopping.app.model.Role;
import shopping.app.model.User;
import shopping.app.service.ProductService;
import shopping.app.service.ShoppingCartService;
import shopping.app.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ShoppingCartControllerTest {
    @MockBean
    private ShoppingCartService shoppingCartService;
    @MockBean
    private UserService userService;
    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void addToCart_ok() {
        Role userRole = new Role();
        userRole.setId(1L);
        userRole.setRoleName(Role.RoleName.USER);
        User user = new User(1L, "admin", "admin", userRole);
        Product product = new Product(1L, "Iphone10", BigDecimal.valueOf(900), 12L);
        Mockito.when(userService.findByUsername("admin"))
                .thenReturn(Optional.of(user));
        Mockito.when(productService.get(1L))
                .thenReturn(product);
        Mockito.doNothing().when(shoppingCartService).addProductOrder(product, 1L, user);
        RestAssuredMockMvc.given()
                .param("productId", 1L)
                .param("quantity", 1L)
                .when()
                .put("shopping-cart")
                .then()
                .statusCode(200);
    }
}