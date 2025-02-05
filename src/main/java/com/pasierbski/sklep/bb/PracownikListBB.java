package com.pasierbski.sklep.bb;

import com.pasierbski.sklep.Users;
import com.pasierbski.sklep.dao.UserDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named("pracownikListBB") // Bean dostępny w JSF
@RequestScoped
public class PracownikListBB {

    @Inject
    private UserDAO userDAO;

    public List<Users> getPracownicy() {
        return userDAO.getList(); // Pobiera listę użytkowników z bazy danych
    }
}
