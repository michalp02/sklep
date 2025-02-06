package com.pasierbski.sklep.bb;

import com.pasierbski.sklep.dao.PurchaseDAO;
import com.pasierbski.sklep.dao.OrderDAO;
import com.pasierbski.sklep.dao.ProductDAO;
import com.pasierbski.sklep.Products;
import com.pasierbski.sklep.Purchases;
import com.pasierbski.sklep.Orders;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import jakarta.faces.context.FacesContext;

@Named("purchaseBB") 
@RequestScoped
public class PurchaseBB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PurchaseDAO purchaseDAO;
    
    @Inject
    private OrderDAO orderDAO;

    @Inject
    private ProductDAO productDAO;
    
    private Purchases purchase;
    private List<Orders> orders;

    public PurchaseBB() {}

    public void loadPurchase() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String idParam = params.get("id");

        if (idParam != null) {
            try {
                int purchaseId = Integer.parseInt(idParam);
                purchase = purchaseDAO.findById(purchaseId);
                orders = orderDAO.findByPurchaseId(purchaseId);
            } catch (NumberFormatException e) {
                purchase = null;
                orders = null;
            }
        }
    }

    public Purchases getPurchase() {
        if (purchase == null) {
            loadPurchase();
        }
        return purchase;
    }

    public List<Orders> getOrders() {
        if (orders == null) {
            loadPurchase();
        }
        return orders;
    }

    public String getTotalPrice() {
        double sum = orders.stream().mapToDouble(order -> order.getQuantity() * order.getUnitPrice()).sum();
        return String.format("%.2f", sum);
    }

    public String getPaymentMethodName() {
        if (purchase == null || purchase.getPaymentMethod() == null) {
            return "Nieznana";
        }
        switch (purchase.getPaymentMethod()) {
            case 1: return "Gotówka";
            case 2: return "Karta kredytowa";
            case 3: return "BLIK";
            default: return "Inna";
        }
    }

    public String getProductName(int productId) {
        Products p = productDAO.getProduktById(productId);
        return p != null ? p.getProductName() : "Produkt #" + productId;
    }
}
