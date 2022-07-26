package shopping.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping.app.model.Order;
import shopping.app.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getAllByUser(User user);
}
