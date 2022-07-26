package shopping.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping.app.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
