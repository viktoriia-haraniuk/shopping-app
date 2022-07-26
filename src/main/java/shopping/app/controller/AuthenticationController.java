package shopping.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shopping.app.dto.request.UserRequestDto;
import shopping.app.dto.response.UserResponseDto;
import shopping.app.model.User;
import shopping.app.service.AuthenticationService;
import shopping.app.service.mapper.ResponseDtoMapper;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final ResponseDtoMapper<UserResponseDto, User> userDtoResponseMapper;

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody UserRequestDto userRequestDto) {
        User user = authService
                .register(userRequestDto.getUsername(),
                        userRequestDto.getPassword());
        return userDtoResponseMapper.mapToDto(user);
    }
}
