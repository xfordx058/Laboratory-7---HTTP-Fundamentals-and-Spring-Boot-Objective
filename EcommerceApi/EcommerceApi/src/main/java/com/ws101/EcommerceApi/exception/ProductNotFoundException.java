package com.ws101.EcommerceApi.exception;

/**
 * Custom exception thrown when a requested product cannot be found
 * in the in-memory data store.
 * 
 * @author Magallanes
 */
public class ProductNotFoundException extends RuntimeException {
    
    /**
     * Constructs a new ProductNotFoundException with the given message.
     * 
     * @param message descriptive error message explaining which product was not found
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}