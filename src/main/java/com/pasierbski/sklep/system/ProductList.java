/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pasierbski.sklep.system;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import jakarta.ejb.EJB;
import jakarta.faces.context.Flash;

import com.pasierbski.sklep.dao.ProductDAO;
import com.pasierbski.sklep.system.ProductList;
import com.pasierbski.sklep.Products;

@Named
@RequestScoped
/**
 *
 * @author Michał
 */
public class ProductList {
    private static final String PAGE_PRODUKT_DETAILS = "productDetails?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;

    @EJB
    private ProductDAO productDAO;

    @Inject
    private Flash flash;

    public List<Products> getList() {
        return productDAO.getFullList(); // Pobiera pełną listę produktów z bazy danych
    }

   public String viewProductDetails(Products products) {
        // Przekierowanie na stronę szczegółów produktu z przekazaniem ID przez parametr URL
        return PAGE_PRODUKT_DETAILS + products.getProductId();
    }
}
