package shopping.app.controller;

import java.math.BigDecimal;
import java.util.List;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import shopping.app.dto.request.ProductRequestDto;
import shopping.app.model.Product;
import shopping.app.service.ProductService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
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
    void getAll_ok() {
        List<Product> mockProducts = List.of(
                new Product(1L, "Iphone10", BigDecimal.valueOf(800), 10L),
                new Product(2L, "Iphone12", BigDecimal.valueOf(1000), 15L)
        );
        Mockito.when(productService.getAll(PageRequest.of(0, 20))).thenReturn(mockProducts);
        RestAssuredMockMvc.when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].title", Matchers.equalTo("Iphone10"))
                .body("[0].price", Matchers.equalTo(800))
                .body("[0].quantity", Matchers.equalTo(10))
                .body("[1].id", Matchers.equalTo(2))
                .body("[1].title", Matchers.equalTo("Iphone12"))
                .body("[1].price", Matchers.equalTo(1000))
                .body("[1].quantity", Matchers.equalTo(15));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void create_ok() {
        Product product = new Product("Iphone10", BigDecimal.valueOf(800), 10L);
        Mockito.when(productService.create(product))
                .thenReturn(new Product(1L, "Iphone10", BigDecimal.valueOf(800), 10L));
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(product.getTitle(), product.getPrice(), product.getQuantity()))
                .when()
                .post("/products")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("title", Matchers.equalTo("Iphone10"))
                .body("price", Matchers.equalTo(800))
                .body("quantity", Matchers.equalTo(10));
    }
}