/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pasierbski.sklep.dao;

import com.pasierbski.sklep.Categories;
import com.pasierbski.sklep.Products;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

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

        public List<Products> getProduktyByCategoryLazy(int categoryId, int start, int limit) {
            String jpql = "SELECT p FROM Products p WHERE p.categoryId = :categoryId ORDER BY p.productName";
            TypedQuery<Products> query = em.createQuery(jpql, Products.class);
            query.setParameter("categoryId", categoryId);
            query.setFirstResult(start);
            query.setMaxResults(limit);
            return query.getResultList();
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

        public void deleteProdukt(Products produkt) {
            em.remove(em.merge(produkt));
        }

        public List<Products> getProduktyByCategory(int categoryId) {
            return em.createQuery("SELECT p FROM Products p WHERE p.category.categoryId = :categoryId", Products.class)
                     .setParameter("categoryId", categoryId)
                     .getResultList();
        }
    
        public List<Products> searchProdukty(String searchQuery) {
            return em.createQuery("SELECT p FROM Products p WHERE LOWER(p.productName) LIKE LOWER(:searchQuery)", Products.class)
                     .setParameter("searchQuery", "%" + searchQuery + "%")
                     .getResultList();
        }

        public List<Categories> getCategories() {
                return em.createNamedQuery("Categories.findAll", Categories.class).getResultList();
        }

        public List<Products> getProduktyByCategoryAndSearch(int categoryId, String searchQuery) {
            String jpql = "SELECT p FROM Products p WHERE p.category.categoryId = :categoryId";
            
            // Dodaj warunek wyszukiwania tylko jeśli searchQuery nie jest puste
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                jpql += " AND p.productName LIKE :searchQuery";
            }
        
            var query = em.createQuery(jpql, Products.class)
                          .setParameter("categoryId", categoryId);
        
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                query.setParameter("searchQuery", "%" + searchQuery + "%");
            }
        
            return query.getResultList();
        }
        
}
