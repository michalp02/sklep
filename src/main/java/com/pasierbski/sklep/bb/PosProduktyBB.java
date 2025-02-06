package com.pasierbski.sklep.bb;

import com.pasierbski.sklep.dao.ProductDAO;
import com.pasierbski.sklep.Products;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named("posProduktyBB")
@RequestScoped
public class PosProduktyBB implements Serializable {

    @Inject
    private ProductDAO productDAO;

    private List<Products> produkty;
    private String searchQuery = "";
    private int categoryId = -1; // Domyślnie brak kategorii

    // Przenosimy logikę wczytywania produktów do metody getProdukty
    public List<Products> getProdukty() {
        loadProducts(); // Załaduj produkty za każdym razem, gdy są wyświetlane
        return produkty;
    }

    private void loadProducts() {
        // Pobranie parametrów z żądania
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String cId = params.get("categoryId");
        searchQuery = params.get("search");

        // Przetworzenie parametru categoryId
        if (cId != null && !cId.trim().isEmpty()) {
            try {
                categoryId = Integer.parseInt(cId);
            } catch (NumberFormatException e) {
                categoryId = -1;  // Jeśli nie uda się sparsować categoryId, traktujemy to jako brak kategorii
            }
        }

        // Logika wczytywania produktów w zależności od parametrów
        if (categoryId > 0) {
            // Kategoria wybrana, więc wczytujemy produkty tylko z tej kategorii
            produkty = productDAO.getProduktyByCategoryLazy(categoryId, 0, 50);
        } else if (searchQuery != null && !searchQuery.trim().isEmpty() && searchQuery.equals("null") == false) {
            // Wyszukiwanie produktów na podstawie zapytania
            produkty = productDAO.getProduktyLazy(searchQuery, 0, 50);
        } else {
            // Wczytanie pełnej listy produktów, jeśli brak filtrów
            produkty = productDAO.getFullList();
        }
    }
}
