package com.ecommerceproject1.ecommerce;

import com.ecommerceproject1.ecommerce.Entity.user.Orders;
import com.ecommerceproject1.ecommerce.Repository.OrderRepository;
import com.ecommerceproject1.ecommerce.Service.Admin.AdminOrderSeviceimp;
import com.ecommerceproject1.ecommerce.Service.User.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminOrderSeviceimpTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminOrderSeviceimp adminOrderService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOrders() {
        // Arrange
        List<Orders> dummyOrders = Collections.singletonList(new Orders()); // Add more dummy data as needed
        when(orderRepository.findByOrderByOrderDateDesc()).thenReturn(dummyOrders);

        // Act
        String result = adminOrderService.Orders(model);

        // Assert
        assertEquals("admin/orderlist", result);
        verify(model).addAttribute("orders", dummyOrders);
    }

    // Add more test cases for other methods as needed
}

