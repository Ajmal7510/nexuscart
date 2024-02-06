package com.ecommerceproject1.ecommerce;

import com.ecommerceproject1.ecommerce.Entity.user.*;
import com.ecommerceproject1.ecommerce.Repository.*;
import com.ecommerceproject1.ecommerce.Service.Admin.CouponService;
import com.ecommerceproject1.ecommerce.Service.Prodect.ProductService;
import com.ecommerceproject1.ecommerce.Service.User.CartService;
import com.ecommerceproject1.ecommerce.Service.User.OrderProductService;
import com.ecommerceproject1.ecommerce.Service.User.UserService;
import com.ecommerceproject1.ecommerce.Service.User.imp.OrderServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceImpTest {

    @Mock
    private CouponService couponService;

    @Mock
    private UserService userService;

    @Mock
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderProductService orderProductService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private OrderServiceImp orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckOut() {
        // Arrange
        String coupon = "SAMPLE_COUPON";
        Model model = mock(Model.class);
        String email = "sample@example.com";
        UserInfo user = new UserInfo();
        user.setEmail(email);
        Cart cart = new Cart();
        cart.setTotalCartAmount(100.0);
        when(userService.currentUserName()).thenReturn(email);
        when(userService.userInfofindByEmail(email)).thenReturn(user);
        when(cartRepository.findByUserUserId(user.getUserId())).thenReturn(cart);
        when(couponService.validateCoupon(coupon)).thenReturn(null);

        // Act
        String result = orderService.checkOut(coupon, model);

        // Assert
        assertEquals("user/checkout-page", result);
        verify(model).addAttribute(eq("showinput"), any());
        verify(model).addAttribute(eq("discountAmount"), anyDouble());
        verify(model).addAttribute(eq("totalAmount"), anyDouble());
    }

    @Test
    void testCheckOutValidation() {
        // Arrange
        Model model = mock(Model.class);
        when(userService.currentUserName()).thenReturn("sample@example.com");
        Cart cart = new Cart();
        when(cartRepository.findByUserEmail("sample@example.com")).thenReturn(cart);

        // Act
        boolean result = orderService.checkOutValidation(model).getBody();

        // Assert
        assertEquals(false, result);
    }

}

