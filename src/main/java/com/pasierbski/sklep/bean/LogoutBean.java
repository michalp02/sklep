package com.pasierbski.sklep.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@Named("logoutBean") // Ważne! JSF musi znaleźć ten bean
@RequestScoped
public class LogoutBean {

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        try {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Wylogowano pomyślnie"));
            externalContext.redirect("login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
