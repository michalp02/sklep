package com.pasierbski.sklep.bb;

import com.pasierbski.sklep.Users;
import com.pasierbski.sklep.dao.UserDAO;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;

@Named("userEditBB")
@RequestScoped
public class UserEditBB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UserDAO userDAO;

    private Users user;

    @PostConstruct
    public void init() {
        if (user == null) {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String idParam = params.get("id");

            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int userId = Integer.parseInt(idParam);
                    user = userDAO.getUserById(userId);
                } catch (NumberFormatException e) {
                    System.out.println("Nieprawidłowy format ID użytkownika");
                }
            }
        }
    }

    public Users getUser() {
        if (user == null) {
            init(); // Zapewniamy, że użytkownik jest załadowany
        }
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String saveUser() {
        if (user != null) {
            userDAO.updateUser(user);
            return "pracownik.xhtml?id=" + user.getUserId() + "&faces-redirect=true";
        }
        return null;
    }
}
