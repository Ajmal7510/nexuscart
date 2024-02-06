package com.ecommerceproject1.ecommerce;

import com.ecommerceproject1.ecommerce.Entity.Admin.Coupon;
import com.ecommerceproject1.ecommerce.Repository.CouponRepository;
import com.ecommerceproject1.ecommerce.Service.Admin.CouponServiceImp;
import com.ecommerceproject1.ecommerce.model.Admin.CouponDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CouponServiceImpTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private CouponServiceImp couponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCoupon() {
        // Arrange
        List<Coupon> coupons = new ArrayList<>();
        when(couponRepository.findAll()).thenReturn(coupons);

        // Act
        String result = couponService.getCoupen(model);

        // Assert
        assertEquals("admin/coupon", result);
        verify(model, times(1)).addAttribute(eq("coupons"), eq(coupons));
    }

    @Test
    void testAddCoupon() {
        // Arrange
        Coupon couponDetails = new Coupon();

        // Act
        String result = couponService.addcoupon(couponDetails, redirectAttributes);

        // Assert
        assertEquals("redirect:/admin/coupon", result);
        verify(couponRepository, times(1)).save(any(Coupon.class));
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("success"), eq("coupon added success"));
    }

    @Test
    void testUpdateCoupon() {
        // Arrange
        CouponDto couponDto = new CouponDto();
        couponDto.setCouponId(1L);

        Coupon existingCoupon = new Coupon();
        existingCoupon.setCouponId(1L);
        when(couponRepository.findById(anyLong())).thenReturn(Optional.of(existingCoupon));

        // Act
        String result = couponService.updateCoupon(couponDto, redirectAttributes);

        // Assert
        assertEquals("redirect:/admin/coupon", result);
        verify(couponRepository, times(1)).save(any(Coupon.class));
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("updateSuccess"), eq("update success "));
    }

    @Test
    void testDeleteCoupon() {
        // Arrange
        Long couponId = 1L;
        Coupon existingCoupon = new Coupon();
        when(couponRepository.findById(couponId)).thenReturn(Optional.of(existingCoupon));

        // Act
        String result = couponService.deleteCoupon(couponId, redirectAttributes);

        // Assert
        assertEquals("redirect:/admin/coupon", result);
        verify(couponRepository, times(1)).delete(any(Coupon.class));
    }

    @Test
    void testValidateCoupon() {
        // Arrange
        String couponCode = "TESTCODE";
        Coupon coupon = new Coupon();
        coupon.setCouponCode(couponCode);
        when(couponRepository.findByCouponCode(couponCode)).thenReturn(coupon);

        // Act
        Coupon result = couponService.validateCoupon(couponCode);

        // Assert
        assertEquals(coupon, result);
    }

    @Test
    void testFindCoupon() {
        // Arrange
        String couponCode = "TESTCODE";
        Coupon coupon = new Coupon();
        when(couponRepository.findByCouponCode(couponCode)).thenReturn(coupon);

        // Act
        boolean result = couponService.findcoupon(couponCode);

        // Assert
        assertEquals(true, result);
    }
}
