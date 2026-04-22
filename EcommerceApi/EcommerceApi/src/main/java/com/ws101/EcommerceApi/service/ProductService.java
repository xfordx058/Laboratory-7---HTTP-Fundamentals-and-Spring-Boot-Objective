package com.ws101.ecommerceapi.service;

import com.ws101.ecommerceapi.exception.ProductNotFoundException;
import com.ws101.ecommerceapi.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for product-related operations.
 * Manages product data using in-memory storage.
 * 
 * Provides business logic for filtering, searching, and managing products.
 * This class acts as an intermediary between the API controller and the
 * data access layer.
 * 
 * @author Anquilo
 * @see Product
 */
@Service
public class ProductService {
    
    /**
     * In-memory storage for products using ArrayList.
     * Data is lost when application restarts.
     */
    private final List<Product> productList = new ArrayList<>();
    
    /**
     * Simple counter for generating unique IDs.
     * Increments atomically for each new product.
     */
    private long idCounter = 1;
    
    /**
     * Initializes the service with sample product data.
     * At least 10 products are pre-loaded for testing purposes.
     */
    public ProductService() {
        initializeSampleData();
    }
    
    /**
     * Populates the in-memory storage with sample products.
     * Creates diverse products across multiple categories.
     */
    private void initializeSampleData() {
        productList.add(new Product(idCounter++, "Wireless Mouse", "Ergonomic wireless mouse with USB receiver", 29.99, "Electronics", 150, "https://example.com/mouse.jpg"));
        productList.add(new Product(idCounter++, "Mechanical Keyboard", "RGB backlit mechanical keyboard", 89.99, "Electronics", 75, "https://example.com/keyboard.jpg"));
        productList.add(new Product(idCounter++, "USB-C Cable", "Fast charging USB-C to USB-C cable", 12.99, "Electronics", 200, null));
        productList.add(new Product(idCounter++, "Notebook", "A5 lined notebook, 200 pages", 8.50, "Stationery", 300, "https://example.com/notebook.jpg"));
        productList.add(new Product(idCounter++, "Coffee Mug", "Ceramic coffee mug, 350ml", 15.00, "Home & Kitchen", 120, "https://example.com/mug.jpg"));
        productList.add(new Product(idCounter++, "Running Shoes", "Lightweight running shoes size 10", 120.00, "Sports", 45, "https://example.com/shoes.jpg"));
        productList.add(new Product(idCounter++, "Yoga Mat", "Non-slip exercise yoga mat", 35.00, "Sports", 80, null));
        productList.add(new Product(idCounter++, "Bluetooth Speaker", "Portable waterproof speaker", 55.00, "Electronics", 60, "https://example.com/speaker.jpg"));
        productList.add(new Product(idCounter++, "Desk Lamp", "LED desk lamp with adjustable brightness", 40.00, "Home & Kitchen", 90, "https://example.com/lamp.jpg"));
        productList.add(new Product(idCounter++, "Backpack", "Water-resistant laptop backpack", 65.00, "Accessories", 55, "https://example.com/backpack.jpg"));
    }
    
    /**
     * Retrieves all products from in-memory storage.
     * 
     * @return a {@code List<Product>} containing all products.
     *         Returns an empty list if no products exist.
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(productList);
    }
    
    /**
     * Finds a product by its unique ID.
     * 
     * @param id the unique identifier of the product to retrieve
     * @return the {@code Product} if found
     * @throws ProductNotFoundException if no product with the given ID exists
     */
    public Product getProductById(Long id) {
        return productList.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }
    
    /**
     * Creates a new product and assigns a unique ID.
     * 
     * @param product the product to create (ID will be auto-generated, any provided ID is ignored)
     * @return the created product with assigned ID
     */
    public Product createProduct(Product product) {
        product.setId(idCounter++);
        productList.add(product);
        return product;
    }
    
    /**
     * Replaces an existing product entirely with new data.
     * 
     * @param id the ID of the product to update
     * @param updatedProduct the new product data (ID will be set to match path variable)
     * @return the updated product
     * @throws ProductNotFoundException if no product with the given ID exists
     */
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        updatedProduct.setId(id);
        int index = productList.indexOf(existing);
        productList.set(index, updatedProduct);
        return updatedProduct;
    }
    
    /**
     * Partially updates an existing product.
     * Only non-null fields from the input are applied.
     * 
     * @param id the ID of the product to patch
     * @param partialProduct product containing only fields to update
     * @return the updated product
     * @throws ProductNotFoundException if no product with the given ID exists
     */
    public Product patchProduct(Long id, Product partialProduct) {
        Product existing = getProductById(id);
        
        if (partialProduct.getName() != null) {
            existing.setName(partialProduct.getName());
        }
        if (partialProduct.getDescription() != null) {
            existing.setDescription(partialProduct.getDescription());
        }
        if (partialProduct.getPrice() != null) {
            existing.setPrice(partialProduct.getPrice());
        }
        if (partialProduct.getCategory() != null) {
            existing.setCategory(partialProduct.getCategory());
        }
        if (partialProduct.getStockQuantity() != null) {
            existing.setStockQuantity(partialProduct.getStockQuantity());
        }
        if (partialProduct.getImageUrl() != null) {
            existing.setImageUrl(partialProduct.getImageUrl());
        }
        
        return existing;
    }
    
    /**
     * Deletes a product by ID.
     * 
     * @param id the ID of the product to delete
     * @throws ProductNotFoundException if no product with the given ID exists
     */
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productList.remove(product);
    }
    
    /**
     * Filters products by category (case-insensitive).
     * 
     * @param category the category to filter by
     * @return a {@code List<Product>} matching the category; empty list if no matches
     */
    public List<Product> filterByCategory(String category) {
        return productList.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    /**
     * Filters products by price range (inclusive).
     * 
     * @param minPrice the minimum price threshold (inclusive)
     * @param maxPrice the maximum price threshold (inclusive)
     * @return a {@code List<Product>} within the price range; empty list if no matches
     * @throws IllegalArgumentException if minPrice is negative, maxPrice is negative, or minPrice > maxPrice
     */
    public List<Product> filterByPrice(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Prices must be non-negative");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("minPrice must be less than or equal to maxPrice");
        }
        return productList.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
    
    /**
     * Filters products by name containing the search term (case-insensitive).
     * 
     * @param name the search term for product names
     * @return a {@code List<Product>} matching the name search; empty list if no matches
     */
    public List<Product> filterByName(String name) {
        return productList.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}