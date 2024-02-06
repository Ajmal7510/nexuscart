package com.ecommerceproject1.ecommerce;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import com.ecommerceproject1.ecommerce.Exeption.ResourceNotFound;
import com.ecommerceproject1.ecommerce.Repository.ProductRepository;
import com.ecommerceproject1.ecommerce.Service.Prodect.CategoryService;
import com.ecommerceproject1.ecommerce.Service.Prodect.impl.ProductServiceimp;
import com.ecommerceproject1.ecommerce.model.product.Product_DTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceimpTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductServiceimp productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllProducts() {
        // Arrange
        List<Products> dummyProducts = Collections.singletonList(new Products());
        when(productRepository.findAll()).thenReturn(dummyProducts);

        // Act
        List<Products> result = productService.findallProduct();

        // Assert
        assertEquals(dummyProducts, result);
    }

    @Test
    void testFindProductById() {
        // Arrange
        Long productId = 1L;
        Products dummyProduct = new Products();
        when(productRepository.findById(productId)).thenReturn(Optional.of(dummyProduct));

        // Act
        Products result = productService.findProductById(productId);

        // Assert
        assertEquals(dummyProduct, result);
    }

    @Test
    void testDeleteProductById() {
        // Arrange
        Long productId = 1L;
        Products dummyProduct = new Products();
        when(productRepository.findById(productId)).thenReturn(Optional.of(dummyProduct));

        // Act
        assertDoesNotThrow(() -> productService.deteleProductbyid(productId));

        // Assert
        assertTrue(dummyProduct.isDelete());
        assertFalse(dummyProduct.isActive());
        verify(productRepository, times(1)).save(dummyProduct);
    }

    // Add more test cases for other methods as needed
}
