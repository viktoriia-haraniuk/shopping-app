package shopping.app.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
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
import shopping.app.model.Order;
import shopping.app.model.Product;
import shopping.app.model.ProductOrder;
import shopping.app.model.Role;
import shopping.app.model.ShoppingCart;
import shopping.app.model.User;
import shopping.app.service.OrderService;
import shopping.app.service.ShoppingCartService;
import shopping.app.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @MockBean
    private ShoppingCartService shoppingCartService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    private static User user;
    private static ShoppingCart shoppingCart;
    private static Order order;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @BeforeAll
    static void beforeAll() {
        Role userRole = new Role();
        userRole.setId(1L);
        userRole.setRoleName(Role.RoleName.USER);
        user = new User(1L, "admin", "admin", userRole);
        Product product = new Product(1L, "Iphone10", BigDecimal.valueOf(800), 10L);
        ProductOrder productOrder = new ProductOrder(1L, product, 10L);
        shoppingCart = new ShoppingCart(1L, List.of(productOrder), user);
        order = new Order(1L, List.of(productOrder),
                LocalDateTime.of(2022, 7, 13, 12, 12),
                false, user);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void completeOrder_ok() {
        Mockito.when(userService.findByUsername("admin"))
                .thenReturn(Optional.of(user));
        Mockito.when(shoppingCartService.getByUser(user))
                .thenReturn(shoppingCart);
        Mockito.when(orderService.completeOrder(shoppingCart))
                .thenReturn(order);
        Mockito.doNothing().when(orderService).deleteUnpaidOrder(order);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .when()
                .post("orders/complete")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("userId", Matchers.equalTo(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void payOrder_ok() {
        order.setPaid(true);
        Mockito.when(orderService.payOrder(1L))
                .thenReturn(order);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .when()
                .put("/orders/complete/pay/1")
                .then()
                .statusCode(200)
                .body("paid", Matchers.equalTo(true));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getOrderHistory_ok() {
        Mockito.when(userService.findByUsername("admin"))
                .thenReturn(Optional.of(user));
        Mockito.when(orderService.getOrderHistory(user))
                .thenReturn(List.of(order));
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .get("/orders")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].userId", Matchers.equalTo(1))
                .body("[0].paid", Matchers.equalTo(false));
    }
}