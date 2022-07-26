package shopping.app.service;

import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import shopping.app.model.Product;

public interface ProductService {
    List<Product> getAll(PageRequest pageRequest);

    Product create(Product product);

    Product get(Long id);
}
