package com.pasierbski.sklep.bb;

import com.pasierbski.sklep.Products;
import com.pasierbski.sklep.dao.ProductDAO;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.enterprise.context.RequestScoped;

@Named("posActionBB")
@RequestScoped
public class PosActionBB {

    @Inject
    private CartBB cartBB;  // Wstrzykujemy CartBB

    @Inject
    private ProductDAO productDAO;

    // Metoda do dodania produktu do koszyka
    public void addToCart(ComponentSystemEvent event) {
        String productIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productId");

        if (productIdParam != null) {
            try {
                int productId = Integer.parseInt(productIdParam);
                Products product = productDAO.getProduktById(productId);  // Pobieramy produkt z bazy

                if (product != null) {
                    cartBB.addProduct(product, 1);  // Dodajemy produkt do koszyka (zakładając, że ilość to 1)
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();  // Obsługa błędów
            }
        }

        // Po dodaniu produktu przekierowujemy użytkownika na stronę "pos.xhtml"
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "pos.xhtml?faces-redirect=true");
    }

    // Metoda do usunięcia produktu z koszyka
    public void removeFromCart(ComponentSystemEvent event) {
        String productIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productId");

        if (productIdParam != null) {
            try {
                int productId = Integer.parseInt(productIdParam);
                cartBB.removeProduct(productId);  // Usuwamy produkt z koszyka

            } catch (NumberFormatException e) {
                e.printStackTrace();  // Obsługa błędów
            }
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "pos.xhtml?faces-redirect=true");
    }

    // Metoda do wyczyszczenia koszyka
    public void clearCart(ComponentSystemEvent event) {
        cartBB.clearCart();  // Czyścimy koszyk

        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "pos.xhtml?faces-redirect=true");
    }
}
