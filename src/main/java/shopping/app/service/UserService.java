package shopping.app.service;

import java.util.Optional;
import shopping.app.model.User;

public interface UserService {
    User create(User user);

    Optional<User> get(Long id);

    Optional<User> findByUsername(String username);
}
