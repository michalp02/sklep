package com.pasierbski.sklep.dao;

import java.util.List;

import com.pasierbski.sklep.Categories;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class CategoryDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Categories> getAllCategories() {
        return em.createNamedQuery("Categories.findAll", Categories.class).getResultList();
    }

    public Categories findCategoryByName(String categoryName) {
        return em.createNamedQuery("Categories.findByCategoryName", Categories.class).setParameter("categoryName", categoryName).getSingleResult();
    }

    public Categories findCategoryById(int categoryId) {
        return em.find(Categories.class, categoryId);
    }
}
