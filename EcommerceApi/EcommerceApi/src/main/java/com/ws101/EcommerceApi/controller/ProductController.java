package com.ws101.EcommerceApi.controller;


import com.ws101.EcommerceApi.model.Product;
import com.ws101.EcommerceApi.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Product resources.
 * Exposes API endpoints for CRUD operations on products.
 * 
 * Base path: /api/v1/products
 * 
 * @author Anquilo
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    
    private final ProductService productService;
    
    /**
     * Constructor-based dependency injection of ProductService.
     * 
     * @param productService the service handling product business logic
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    /**
     * Retrieves all products.
     * 
     * GET /api/v1/products
     * 
     * @return ResponseEntity with list of products and HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    
    /**
     * Retrieves a single product by ID.
     * 
     * GET /api/v1/products/{id}
     * 
     * @param id the product ID extracted from URL path
     * @return ResponseEntity with product (200 OK) or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
    
    /**
     * Filters products by criteria.
     * 
     * GET /api/v1/products/filter?filterType=<type>&filterValue=<value>
     * 
     * @param filterType the criteria to filter by (category, price, name)
     * @param filterValue the value to filter by
     * @return ResponseEntity with filtered list and HTTP 200 OK
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam String filterType,
            @RequestParam String filterValue) {
        
        List<Product> filtered;
        
        switch (filterType.toLowerCase()) {
            case "category":
                filtered = productService.filterByCategory(filterValue);
                break;
            case "name":
                filtered = productService.filterByName(filterValue);
                break;
            default:
                // For price, expect format: min-max (e.g., "10-50")
                if (filterType.equalsIgnoreCase("price")) {
                    String[] range = filterValue.split("-");
                    double min = Double.parseDouble(range[0]);
                    double max = Double.parseDouble(range[1]);
                    filtered = productService.filterByPrice(min, max);
                } else {
                    filtered = productService.getAllProducts();
                }
        }
        
        return ResponseEntity.ok(filtered);
    }
    
    /**
     * Creates a new product.
     * 
     * POST /api/v1/products
     * 
     * @param product the product data from request body (JSON)
     * @return ResponseEntity with created product and HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product created = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    /**
     * Replaces an existing product entirely.
     * 
     * PUT /api/v1/products/{id}
     * 
     * @param id the product ID to update
     * @param product the new complete product data
     * @return ResponseEntity with updated product (200 OK) or 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {
        Product updated = productService.updateProduct(id, product);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }
    
    /**
     * Partially updates an existing product.
     * 
     * PATCH /api/v1/products/{id}
     * 
     * @param id the product ID to patch
     * @param product partial product data
     * @return ResponseEntity with patched product (200 OK) or 404 Not Found
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(
            @PathVariable Long id,
            @RequestBody Product product) {
        Product patched = productService.patchProduct(id, product);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patched);
    }
    
    /**
     * Deletes a product by ID.
     * 
     * DELETE /api/v1/products/{id}
     * 
     * @param id the product ID to delete
     * @return ResponseEntity with HTTP 204 No Content (success) or 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
