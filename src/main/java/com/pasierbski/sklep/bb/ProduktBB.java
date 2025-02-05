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
import java.util.Map;

@Named("produktBB")
@RequestScoped
public class ProduktBB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProductDAO productDAO;

    @Inject
    private CategoryDAO categoryDAO;

    private Products product;
    private String categoryName; // Przechowuje nazwę kategorii

    public ProduktBB() {
    }

    public Products getProduct() {
        if (product == null) {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String idParam = params.get("id");

            if (idParam != null) {
                try {
                    int productId = Integer.parseInt(idParam);
                    product = productDAO.getProduktById(productId);

                    // Pobranie nazwy kategorii na podstawie categoryId
                        Categories category = categoryDAO.findCategoryById(product.getCategoryId());
                        categoryName = (category != null) ? category.getCategoryName() : "Brak kategorii";
                } catch (NumberFormatException e) {
                    System.out.println("Nieprawidłowy format ID produktu");
                }
            }
        }
        return product;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
