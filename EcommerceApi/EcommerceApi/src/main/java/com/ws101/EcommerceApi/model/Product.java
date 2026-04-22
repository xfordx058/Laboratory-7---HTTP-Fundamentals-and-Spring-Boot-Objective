package com.ws101.EcommerceApi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

/**
 * Entity class representing an e-commerce product.
 * 
 * @author Anquilo
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Product {
    
    /**
     * Unique identifier for the product.
     */
    private Long id;
    
    /**
     * Name of the product.
     */
    private String name;
    
    /**
     * Description of the product.
     */
    private String description;
    
    /**
     * Price of the product. Must be positive.
     */
    private Double price;
    
    /**
     * Category the product belongs to.
     */
    private String category;
    
    /**
     * Available stock quantity. Must be non-negative.
     */
    private Integer stockQuantity;
    
    /**
     * URL to product image (optional).
     */
    private String imageUrl;
}