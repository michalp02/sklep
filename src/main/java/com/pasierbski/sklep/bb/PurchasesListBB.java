package com.pasierbski.sklep.bb;

import com.pasierbski.sklep.dao.PurchaseDAO;
import com.pasierbski.sklep.Purchases;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("purchasesListBB")  // Bean dostępny w JSF
@RequestScoped  // Przechowuje stan strony dla użytkownika
public class PurchasesListBB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PurchaseDAO purchaseDAO;

    private String searchQuery = ""; // Wyszukiwanie

    private int page = 0; // Numer strony (dla lazy loadingu)
    private static final int PAGE_SIZE = 5; // Ilość paragonów na stronę

    public List<Purchases> getPurchases() {
        return purchaseDAO.getFullList();
    }
    public List<Purchases> getPurchasesReverse() {
        return purchaseDAO.getFullListReverse();
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
        return purchaseDAO.getPurchasesLazy(searchQuery, 0, Integer.MAX_VALUE).size() / PAGE_SIZE;
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
