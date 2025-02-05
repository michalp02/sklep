package com.pasierbski.sklep.bb;

import com.pasierbski.sklep.dao.ProductDAO;
import com.pasierbski.sklep.Products;
import com.pasierbski.sklep.Categories;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Named("produktNewBB")
@RequestScoped
public class ProduktNewBB implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productName;
    private float productPrice;
    private String productDescription;
    private int productCategory;
    private String productImage;
    private int productStock;

    @Inject
    private ProductDAO productDAO;

    public String addProdukt() {
        Products produkt = new Products();
        produkt.setProductName(productName);
        produkt.setPrice(productPrice);
        produkt.setDescription(productDescription);
        produkt.setCategoryId(productCategory);
        produkt.setImage(productImage);
        produkt.setStock(productStock);

        productDAO.addProdukt(produkt);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produkt dodany!"));

        return "produkty.xhtml?faces-redirect=true"; // Przekierowanie na listę produktów
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

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    private List<Categories> categories; // Buforowana lista kategorii

    public List<Categories> getCategories() {
        if (categories == null) { // Pobierz listę tylko raz
            categories = productDAO.getCategories();
        }
        return categories;
    }
}
