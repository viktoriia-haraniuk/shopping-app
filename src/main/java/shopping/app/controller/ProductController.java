package shopping.app.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shopping.app.dto.request.ProductRequestDto;
import shopping.app.dto.response.ProductResponseDto;
import shopping.app.model.Product;
import shopping.app.service.ProductService;
import shopping.app.service.mapper.RequestDtoMapper;
import shopping.app.service.mapper.ResponseDtoMapper;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final RequestDtoMapper<ProductRequestDto, Product> productRequestDtoMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> productResponseDtoMapper;

    @GetMapping
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "20")
                                    Integer count,
                                @RequestParam (defaultValue = "0")
                                    Integer page) {
        PageRequest pageRequest = PageRequest.of(page, count);
        return productService.getAll(pageRequest).stream()
                .map(productResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService
                .create(productRequestDtoMapper
                        .mapToModel(productRequestDto));
        return productResponseDtoMapper.mapToDto(product);
    }
}
