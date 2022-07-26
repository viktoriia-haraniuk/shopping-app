package shopping.app.service.mapper.request;

import org.springframework.stereotype.Component;
import shopping.app.dto.request.ProductRequestDto;
import shopping.app.model.Product;
import shopping.app.service.mapper.RequestDtoMapper;

@Component
public class ProductRequestMapper implements RequestDtoMapper<ProductRequestDto, Product> {
    @Override
    public Product mapToModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        return product;
    }
}
