package com.ecommerceproject1.ecommerce;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import com.ecommerceproject1.ecommerce.Entity.user.Cart;
import com.ecommerceproject1.ecommerce.Entity.user.CartProduct;
import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;
import com.ecommerceproject1.ecommerce.Repository.CartRepository;
import com.ecommerceproject1.ecommerce.Repository.ProductRepository;
import com.ecommerceproject1.ecommerce.Repository.UserInfoRepository;
import com.ecommerceproject1.ecommerce.Service.User.UserService;
import com.ecommerceproject1.ecommerce.Service.User.imp.CartServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartServiceImpTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartServiceImp cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddToCart() {
        // Arrange
        UserInfo user = TestDataUtil.createDummyUser("user@example.com");
        Products product = TestDataUtil.createDummyProduct(1L, "Dummy Product", 10.0);
        Cart cart = TestDataUtil.createDummyCart(user);
        when(userInfoRepository.findByEmail(any())).thenReturn(user);
        when(cartRepository.findByUserUserId(any())).thenReturn(cart);
        when(productRepository.findById(any())).thenReturn(java.util.Optional.of(product));

        // Act
        ResponseEntity<String> responseEntity = cartService.addtocar(1L);

        // Assert
        assertEquals("success", responseEntity.getBody());
        assertEquals(1, cart.getCartProducts().size());
        assertEquals(10.0, cart.getTotalCartAmount());
    }
    // Add more tests for other methods as needed


    @Test
    public void testRemoveFromCart() {
        // Arrange
        UserInfo user = TestDataUtil.createDummyUser("user@example.com");
        Cart cart = TestDataUtil.createDummyCart(user);
        Products product = TestDataUtil.createDummyProduct(1L, "Dummy Product", 10.0);
        CartProduct cartProduct = TestDataUtil.createDummyCartProduct(cart, product, 2);
        cart.getCartProducts().add(cartProduct);

        when(userInfoRepository.findByEmail(any())).thenReturn(user);
        when(cartRepository.findByUserUserId(any())).thenReturn(cart);

        // Act
        ResponseEntity<String> responseEntity = cartService.removeFromCart(1L);

        // Assert
        assertEquals("Product removed from the cart", responseEntity.getBody());
        assertEquals(0, cart.getCartProducts().size());
        assertEquals(0.0, cart.getTotalCartAmount());
    }



    @Test
    public void testDecreaseProductQuantity() {
        // Arrange
        UserInfo user = TestDataUtil.createDummyUser("user@example.com");
        Cart cart = TestDataUtil.createDummyCart(user);
        Products product = TestDataUtil.createDummyProduct(1L, "Dummy Product", 10.0);
        CartProduct cartProduct = TestDataUtil.createDummyCartProduct(cart, product, 3);
        cart.getCartProducts().add(cartProduct);

        when(userInfoRepository.findByEmail(any())).thenReturn(user);
        when(cartRepository.findByUserUserId(any())).thenReturn(cart);

        // Act
        ResponseEntity<String> responseEntity = cartService.decreaseProductQuantity(1L);

        // Assert
        assertEquals("Product quantity decreased in the cart", responseEntity.getBody());
        assertEquals(2, cartProduct.getQuantity());
        assertEquals(20.0, cart.getTotalCartAmount());
    }
}






