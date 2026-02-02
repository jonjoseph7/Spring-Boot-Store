package com.jonathanjoseph.store.products;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping("/products")
@AllArgsConstructor
@RestController
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping("")
    public Iterable<ProductDto> getAllProducts(
            @RequestParam(required = false, name = "category_id") Byte categoryId
    ) {
        List<Product> prods;
        if (categoryId == null) {
            prods = productRepository.findAllWithCategory();
        }
        else {
            prods = productRepository.findByCategoryId(categoryId);
        }

        return prods.stream().map(productMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        var prod = productRepository.findById(id).orElse(null);
        if (prod == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDto(prod));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder
    ) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);

        if (category == null) {
            return ResponseEntity.badRequest().build();
        }

        var product = productMapper.toEntity(productDto);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());

        var uri = uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto
    ) {
        var prod = productRepository.findById(id).orElse(null);
        if (prod == null) {
            return ResponseEntity.notFound().build();
        }

        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        productMapper.update(productDto, prod);
        prod.setCategory(category);

        productDto.setId(id);

        productRepository.save(prod);

        return ResponseEntity.ok(productDto);
    }

}

