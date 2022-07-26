package shopping.app.service;

import shopping.app.model.User;

public interface AuthenticationService {
    User register(String username, String password);
}