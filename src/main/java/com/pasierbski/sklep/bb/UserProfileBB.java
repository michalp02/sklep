package com.pasierbski.sklep.bb;

import com.pasierbski.sklep.Users;
import com.pasierbski.sklep.dao.UserDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;

@Named("userProfileBB")
@RequestScoped
public class UserProfileBB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UserDAO userDAO;

    private Users user;
    
    public Users getUser() {
        if (user == null) {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String idParam = params.get("id");

            if(idParam == null) {
                // Set idParam to current user id
                Users loggeduser = (Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
                idParam = String.valueOf(loggeduser.getUserId());
            }
            
            if (idParam != null) {
                try {
                    int userId = Integer.parseInt(idParam);
                    user = userDAO.getUserById(userId);
                } catch (NumberFormatException e) {
                    System.out.println("Nieprawidłowy format ID użytkownika");
                }
            }
        }
        return user;
    }
    
    public void setUser(Users user) {
        this.user = user;
    }
    
    public String getUserRole() {
        return (user != null) ? userDAO.getRole(user) : "Brak roli";
    }
}
