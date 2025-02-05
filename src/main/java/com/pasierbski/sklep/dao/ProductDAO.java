/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pasierbski.sklep.dao;

import com.pasierbski.sklep.Categories;
import com.pasierbski.sklep.Products;
import com.pasierbski.sklep.Users;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Michał
 */
@Stateless
public class ProductDAO {
    
        @PersistenceContext
        private EntityManager em;
     
     
        public List<Products> getFullList() {
        return em.createNamedQuery("Products.findAll", Products.class).getResultList();
        }
        
        public List<Products> getList(Map<String, Object> searchParams) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Product p WHERE 1=1");
        var query = em.createQuery(jpql.toString(), Products.class);
        return query.getResultList();
        }

        public List<Products> getProduktyLazy(String searchQuery, int page, int pageSize) {
                String jpql = "SELECT p FROM Products p WHERE p.productName LIKE :searchQuery ORDER BY p.productName";
                return em.createQuery(jpql, Products.class)
                         .setParameter("searchQuery", "%" + searchQuery + "%")
                         .setFirstResult(page * pageSize) // Offset
                         .setMaxResults(pageSize) // Limit
                         .getResultList();
        }

        public Products getProduktById(int id) {
            return em.createNamedQuery("Products.findByProductId", Products.class).setParameter("productId", id).getSingleResult();
        }

        public void addProdukt(Products produkt) {
            em.persist(produkt);
        }

        public void updateProdukt(Products produkt) {
            em.merge(produkt);
        }

        public List<Categories> getCategories() {
                return em.createNamedQuery("Categories.findAll", Categories.class).getResultList();
        }
}
