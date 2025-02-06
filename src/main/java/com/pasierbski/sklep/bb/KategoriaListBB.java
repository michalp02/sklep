package com.pasierbski.sklep.bb;

import com.pasierbski.sklep.dao.CategoryDAO;
import com.pasierbski.sklep.Categories;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("kategoriaListBB")  // Bean dostępny w JSF
@SessionScoped  // Przechowuje stan strony dla użytkownika
public class KategoriaListBB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CategoryDAO categoryDAO;

    public List<Categories> getKategorie() {
        return categoryDAO.getAllCategories();
    }

    
}
