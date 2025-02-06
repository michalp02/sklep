package com.pasierbski.sklep.dao;

import com.pasierbski.sklep.Products;
import com.pasierbski.sklep.Purchases;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Stateless
public class PurchaseDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<Purchases> getPurchasesLazy(String searchQuery, int page, int pageSize) {
        String query = "SELECT p FROM Purchases p WHERE p.purchaseId = :searchQuery";
        
        // Sprawdzamy, czy searchQuery nie jest puste i próba konwersji na Integer
        Integer searchId = null;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            try {
                searchId = Integer.parseInt(searchQuery);
            } catch (NumberFormatException e) {
                // Jeśli nie uda się przekonwertować, możemy po prostu ignorować błąd
                // lub obsługiwać go zgodnie z logiką aplikacji.
                searchId = null;
            }
        }
        
        return em.createQuery(query, Purchases.class)
                            .setParameter("searchQuery", searchId)
                            .setFirstResult(page * pageSize)
                            .setMaxResults(pageSize)
                            .getResultList();
    }

     public List<Purchases> getFullList() {
        return em.createNamedQuery("Purchases.findAll", Purchases.class).getResultList();
        }

        public List<Purchases> getFullListReverse() {
            return em.createNamedQuery("Purchases.findAllReverse", Purchases.class).getResultList();
        }
        
        public List<Purchases> getList(Map<String, Object> searchParams) {
            StringBuilder jpql = new StringBuilder("SELECT p FROM Purchases p WHERE 1=1");
            var query = em.createQuery(jpql.toString(), Purchases.class);
            return query.getResultList();
        }

        public Purchases findById(int id) {
            return em.find(Purchases.class, id);
        }

}