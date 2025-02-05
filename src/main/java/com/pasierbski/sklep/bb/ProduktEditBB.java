package com.pasierbski.sklep.bb;

import com.pasierbski.sklep.Products;
import com.pasierbski.sklep.Categories;
import com.pasierbski.sklep.dao.ProductDAO;
import com.pasierbski.sklep.dao.CategoryDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named("produktEditBB")
@RequestScoped
public class ProduktEditBB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProductDAO productDAO;

    @Inject
    private CategoryDAO categoryDAO;

    private Products product;
    
    private String productName;
    private float productPrice;
    private String productDescription;
    private int productCategory;
    private String productImage;
    private int productStock;

    private List<Categories> categories; // Lista kategorii
    private String categoryName; // Nazwa kategorii

    public ProduktEditBB() {
    }

    public Products getProduct() {
        if (product == null) {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String idParam = params.get("id");

            if (idParam != null) {
                try {
                    int productId = Integer.parseInt(idParam);
                    product = productDAO.getProduktById(productId);

                    if (product != null) {
                        // Ustawienie pól
                        this.productName = product.getProductName();
                        this.productPrice = product.getPrice();
                        this.productDescription = product.getDescription();
                        this.productImage = product.getImage();
                        this.productStock = product.getStock();
                        this.productCategory = product.getCategoryId();

                        // Pobranie nazwy kategorii
                        Categories category = categoryDAO.findCategoryById(product.getCategoryId());
                        categoryName = (category != null) ? category.getCategoryName() : "Brak kategorii";
                    } else {
                        System.out.println("Produkt o podanym ID nie istnieje.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Nieprawidłowy format ID produktu");
                }
            }
        }
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public List<Categories> getCategories() {
        if (categories == null) { // Pobierz listę tylko raz
            categories = categoryDAO.getAllCategories();
        }
        return categories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    // Metoda zapisująca zmiany w produkcie
    public String saveProdukt() {
        if (product != null) {
            product.setProductName(productName);
            product.setPrice(productPrice);
            product.setDescription(productDescription);
            product.setImage(productImage);
            product.setStock(productStock);
            product.setCategoryId(productCategory);

            productDAO.updateProdukt(product);
        }
        return "produkty.xhtml?faces-redirect=true";
    }

    // Gettery i settery
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public int getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(int productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
