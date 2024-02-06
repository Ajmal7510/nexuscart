package com.ecommerceproject1.ecommerce;

import com.ecommerceproject1.ecommerce.Entity.Prodect.Products;
import com.ecommerceproject1.ecommerce.Entity.user.Cart;
import com.ecommerceproject1.ecommerce.Entity.user.CartProduct;
import com.ecommerceproject1.ecommerce.Entity.user.UserInfo;

import java.util.ArrayList;

public class TestDataUtil {

    public static Products createDummyProduct(Long productId, String productName, double price) {
        Products product = new Products();
        product.setProductID(productId);
        product.setProductName(productName);
        product.setPrice((float) price);
        // Set other properties as needed
        return product;
    }

    public static Cart createDummyCart(UserInfo user) {
        Cart cart = new Cart();
        cart.setCartProducts(new ArrayList<>());
        cart.setUser(user);
        cart.setTotalCartAmount(0.0);
        // Set other properties as needed
        return cart;
    }

    public static CartProduct createDummyCartProduct(Cart cart, Products product, int quantity) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);
        // Set other properties as needed
        return cartProduct;
    }

    public static UserInfo createDummyUser(String email) {
        UserInfo user = new UserInfo();
        user.setEmail(email);
        // Set other properties as needed
        return user;
    }

    // Add more methods for other entities as needed
}
