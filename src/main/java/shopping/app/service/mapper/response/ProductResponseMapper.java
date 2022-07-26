package shopping.app.service.mapper.response;

import org.springframework.stereotype.Component;
import shopping.app.dto.response.ProductResponseDto;
import shopping.app.model.Product;
import shopping.app.service.mapper.ResponseDtoMapper;

@Component
public class ProductResponseMapper implements ResponseDtoMapper<ProductResponseDto, Product> {
    @Override
    public ProductResponseDto mapToDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setQuantity(product.getQuantity());
        return productResponseDto;
    }
}
