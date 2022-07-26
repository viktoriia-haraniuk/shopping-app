package shopping.app.service.mapper.response;

import org.springframework.stereotype.Component;
import shopping.app.dto.response.UserResponseDto;
import shopping.app.model.User;
import shopping.app.service.mapper.ResponseDtoMapper;

@Component
public class UserResponseMapper implements ResponseDtoMapper<UserResponseDto, User> {
    @Override
    public UserResponseDto mapToDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setUsername(user.getUsername());
        return responseDto;
    }
}
