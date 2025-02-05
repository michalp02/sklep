package com.pasierbski.sklep.bb;

import com.pasierbski.sklep.dao.ProductDAO;
import com.pasierbski.sklep.Products;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("produktListBB")  // Bean dostępny w JSF
@SessionScoped  // Przechowuje stan strony dla użytkownika
public class ProduktListBB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProductDAO productDAO;

    private String searchQuery = ""; // Wyszukiwanie

    private int page = 0; // Numer strony (dla lazy loadingu)
    private static final int PAGE_SIZE = 5; // Ilość produktów na stronę

    public List<Products> getProdukty() {
        return productDAO.getProduktyLazy(searchQuery, page, PAGE_SIZE);
    }

    public void nextPage() {
        page++;
    }

    public void prevPage() {
        if (page > 0) {
            page--;
        }
    }

    public int getMaxPage() {
        return productDAO.getProduktyLazy(searchQuery, 0, Integer.MAX_VALUE).size() / PAGE_SIZE;
    }

    public void search() {
        page = 0; // Resetujemy stronę po nowym wyszukiwaniu
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
