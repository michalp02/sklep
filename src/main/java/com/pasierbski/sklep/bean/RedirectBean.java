package com.pasierbski.sklep.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;
import com.pasierbski.sklep.Users;

@Named
@RequestScoped
public class RedirectBean {
    
    // Metoda przekierowująca na stronę logowania lub panelu w zależności od zalogowania (index.xhtml)
    public void indexRedirect() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);

        try {
            if (session != null && session.getAttribute("user") != null) {
                externalContext.redirect("panel.xhtml"); // Przekierowanie dla zalogowanych
            } else {
                externalContext.redirect("login.xhtml"); // Przekierowanie dla niezalogowanych
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metoda przekierowująca na stronę logowania jeśli użytkownik nie jest zalogowany. W przeciwnym wypadku nie robi nic (panel.xhtml)
    public void panelRedirect() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);

        try {
            if (session == null || session.getAttribute("user") == null) {
                externalContext.redirect("login.xhtml"); // Przekierowanie dla niezalogowanych
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metoda zabezpeczająca strony tylko dla administratora - przekierowuje na stronę panelu jeśli użytkownik jest zalogowany, ale nie jest administratorem
    public void adminRedirect() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);

        try {
            if (session != null && session.getAttribute("user") != null) {
                Users user = (Users) session.getAttribute("user");
                if (user.getRole() != 1) {
                    externalContext.redirect("panel.xhtml"); // Przekierowanie dla zalogowanych, którzy nie są administratorem
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metoda przekierowująca na stronę panelu jeśli użytkownik jest zalogowany. W przeciwnym wypadku nie robi nic (login.xhtml)
    public void loginRedirect() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);

        try {
            if (session != null && session.getAttribute("user") != null) {
                externalContext.redirect("panel.xhtml"); // Przekierowanie dla zalogowanych
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}