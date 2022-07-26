package shopping.app.controller;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import shopping.app.dto.request.UserRequestDto;
import shopping.app.model.Role;
import shopping.app.model.User;
import shopping.app.service.AuthenticationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    @MockBean
    private AuthenticationService authService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void register_ok() {
        Role userRole = new Role(1L, Role.RoleName.USER);
        Mockito.when(authService.register("user123", "user123"))
                .thenReturn(new User(1L, "user123", "user123", userRole));
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new UserRequestDto("user123", "user123"))
                .when()
                .post("/register")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("username", Matchers.equalTo("user123"));
    }
}