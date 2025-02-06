package com.pasierbski.sklep.dao;

import com.pasierbski.sklep.Orders;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Orders> findByPurchaseId(int purchaseId) {
        return entityManager.createQuery(
                "SELECT o FROM Orders o WHERE o.purchaseId = :purchaseId", Orders.class)
                .setParameter("purchaseId", purchaseId)
                .getResultList();
    }
}
