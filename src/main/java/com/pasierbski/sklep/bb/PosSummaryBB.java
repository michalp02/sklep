package com.pasierbski.sklep.bb;

import com.pasierbski.sklep.Orders;
import com.pasierbski.sklep.Products;
import com.pasierbski.sklep.Purchases;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.UserTransaction;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named("posSummaryBB")
@RequestScoped
public class PosSummaryBB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CartBB cartBB;

    @Inject
    private UserTransaction userTransaction;

    @PersistenceContext
    private EntityManager entityManager;

    private List<Orders> ordersList; // Lista zamówionych produktów
    private double totalPrice;

    public void generateReceipt() {
        try {
            userTransaction.begin(); // Rozpoczynamy transakcję

            double totalPrice = 0.0;

            // Tworzymy rekord w tabeli Purchases
            Purchases purchase = new Purchases();
            purchase.setUserId(1); // Zakładamy, że użytkownik o ID 1 dokonuje zakupu
            purchase.setCreateTime(new Date()); // Ustawiamy czas stworzenia zakupu
            entityManager.persist(purchase); // Zapisujemy zakup w bazie
            int purchaseId = purchase.getPurchaseId(); // Pobieramy wygenerowane ID zakupu

            // Tworzymy zamówienie na podstawie produktów w koszyku
            for (CartBB.CartItem item : cartBB.getCartItems()) {
                Products product = item.getProduct();
                int quantity = item.getQuantity();
                float unitPrice = product.getPrice();
                int vat = 23; // Zakładamy stały VAT 23%

                // Tworzymy rekord w tabeli Orders dla każdej pozycji
                Orders order = new Orders();
                order.setPurchaseId(purchaseId);
                order.setProductId(product.getProductId());
                order.setQuantity(quantity);
                order.setUnitPrice(unitPrice);
                order.setVat(vat);

                // Zapisujemy zamówienie w bazie danych
                entityManager.persist(order);
                totalPrice += unitPrice * quantity;

                // Zaktualizowanie stanu magazynowego produktu
                updateProductStock(product, quantity); // Zmiana w magazynie
            }

            // Przypisujemy całkowitą cenę
            this.totalPrice = totalPrice;

            // Po zapisaniu zamówienia, czyścimy koszyk
            cartBB.clearCart();

            userTransaction.commit(); // Zatwierdzamy transakcję

            // Przypisujemy zamówienia do listy, by wyświetlić je w tabeli
            ordersList = entityManager.createNamedQuery("Orders.findByPurchaseId", Orders.class)
                                      .setParameter("purchaseId", purchaseId)
                                      .getResultList();

        } catch (Exception e) {
            try {
                userTransaction.rollback(); // W razie błędu, wycofujemy transakcję
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }
    }

    private void updateProductStock(Products product, int quantitySold) {
        // Znajdujemy produkt w bazie danych na podstawie jego productId
        Products productFromDb = entityManager.find(Products.class, product.getProductId());
        if (productFromDb != null) {
            // Zaktualizuj ilość produktu w magazynie
            int updatedStock = productFromDb.getStock() - quantitySold;
            productFromDb.setStock(updatedStock);
            entityManager.merge(productFromDb); // Zapisujemy zmiany w bazie
        }
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Wywołanie tej metody na początku, aby załadować dane zamówienia
    public void loadReceipt() {
        generateReceipt();
    }

    // Powrót do POS
    public String backToPos() {
        return "pos.xhtml?faces-redirect=true";
    }
}
