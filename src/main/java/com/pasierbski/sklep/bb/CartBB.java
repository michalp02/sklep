package com.pasierbski.sklep.bb;

import com.pasierbski.sklep.Products;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("cartBB")
@SessionScoped
public class CartBB implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<CartItem> cartItems = new ArrayList<>();

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addProduct(Products product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItem(product, quantity));
    }

    public void removeProduct(int productId) {
        cartItems.removeIf(item -> item.getProduct().getProductId() == productId);
    }

    public void clearCart() {
        cartItems.clear();
    }

    public String getTotalPrice() {
        Double sum = cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
        return String.format("%.2f", sum);
    }

    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }

    public static class CartItem {
        private Products product;
        private int quantity;

        public CartItem(Products product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Products getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

}
