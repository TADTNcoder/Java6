package com.lab7.controller;

import com.lab7.dto.ProductRequest;
import com.lab7.dto.ProductResponse;
import com.lab7.entity.Category;
import com.lab7.entity.Product;
import com.lab7.repository.CategoryRepository;
import com.lab7.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return toResponse(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody ProductRequest request) {
        validate(request.id(), request.name(), request.categoryId(), request.price(), request.date());
        if (productRepository.existsById(request.id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product id already exists");
        }
        Category category = findCategory(request.categoryId());

        Product product = new Product();
        product.setId(request.id().trim());
        product.setName(request.name().trim());
        product.setPrice(request.price());
        product.setDate(request.date());
        product.setCategory(category);
        return toResponse(productRepository.save(product));
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable String id, @RequestBody ProductRequest request) {
        validate(id, request.name(), request.categoryId(), request.price(), request.date());
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        Category category = findCategory(request.categoryId());

        product.setName(request.name().trim());
        product.setPrice(request.price());
        product.setDate(request.date());
        product.setCategory(category);
        return toResponse(productRepository.save(product));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        productRepository.delete(product);
    }

    private Category findCategory(String categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category does not exist"));
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDate(),
                product.getCategory().getId()
        );
    }

    private void validate(String id, String name, String categoryId, Object price, Object date) {
        if (id == null || id.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id is required");
        }
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name is required");
        }
        if (categoryId == null || categoryId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category id is required");
        }
        if (price == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price is required");
        }
        if (date == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is required");
        }
    }
}
