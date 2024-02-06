package com.ecommerceproject1.ecommerce;

import com.ecommerceproject1.ecommerce.Entity.Admin.Banner;
import com.ecommerceproject1.ecommerce.Exeption.ResourceNotFound;
import com.ecommerceproject1.ecommerce.Repository.BannerRepository;
import com.ecommerceproject1.ecommerce.Service.Admin.BannerServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BannerServiceImpTest {

    @Mock
    private BannerRepository bannerRepository;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private BannerServiceImp bannerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBanner() throws IOException {
        // Arrange
        MultipartFile[] images = {new MockMultipartFile("image1", "image1.jpg", "image/jpeg", "data".getBytes())};
        String description = "Test Banner Description";

        // Act
        String result = bannerService.addbanner(images, description, redirectAttributes);

        // Assert
        assertEquals("redirect:/admin/banner", result);
        verify(bannerRepository, times(1)).save(any(Banner.class));
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("success"), eq(" banner added successfully"));
    }

    @Test
    void testBannerPage() {
        // Arrange
        List<Banner> banners = new ArrayList<>();
        banners.add(new Banner());
        when(bannerRepository.findAllByOrderByBannerIdAsc()).thenReturn(banners);

        // Act
        String result = bannerService.bannerPage(model);

        // Assert
        assertEquals("admin/banner", result);
        verify(model, times(1)).addAttribute(eq("banners"), eq(banners));
    }

    @Test
    void testActivateBanner() {
        // Arrange
        Banner activeBanner = new Banner();
        activeBanner.setActive(true);
        when(bannerRepository.findFirstByIsActive(true)).thenReturn(activeBanner);

        Banner bannerToActivate = new Banner();
        when(bannerRepository.findById(anyLong())).thenReturn(Optional.of(bannerToActivate));

        // Act
        bannerService.activateBanner(1L);

        // Assert
        verify(bannerRepository, times(1)).save(activeBanner);
        verify(bannerRepository, times(1)).save(bannerToActivate);
    }

    @Test
    void testActivateBannerWithNonExistingBanner() {
        // Arrange
        when(bannerRepository.findFirstByIsActive(true)).thenReturn(new Banner());
        when(bannerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFound.class, () -> bannerService.activateBanner(1L));
    }

    @Test
    void testDeleteBanner() {
        // Arrange
        Banner bannerToDelete = new Banner();
        when(bannerRepository.findById(anyLong())).thenReturn(Optional.of(bannerToDelete));

        // Act
        bannerService.deleteBanner(1L);

        // Assert
        verify(bannerRepository, times(1)).delete(bannerToDelete);
    }

    @Test
    void testDeleteBannerWithNonExistingBanner() {
        // Arrange
        when(bannerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFound.class, () -> bannerService.deleteBanner(1L));
    }
}
