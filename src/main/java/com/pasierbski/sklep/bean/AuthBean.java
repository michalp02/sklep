package com.pasierbski.sklep.bean;

import com.pasierbski.sklep.dao.AuthBB;
import com.pasierbski.sklep.model.User;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class AuthBean implements Serializable {

    private String username;
    private String password;

    private User loggedInUser;

    @Inject
    private AuthBB authBB;

    public String login() {
        User user = authBB.authenticate(username, password);
        if (user != null) {
            loggedInUser = user;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sukces", "Zalogowano pomyślnie!"));
            return "home.xhtml?faces-redirect=true"; // Przekierowanie do strony głównej
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd", "Niepoprawna nazwa użytkownika lub hasło."));
            return null; // Pozostanie na stronie logowania
        }
    }

    public void logout() {
        loggedInUser = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    // Gettery i settery
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
