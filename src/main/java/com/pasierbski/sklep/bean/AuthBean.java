package com.pasierbski.sklep.bean;

import com.pasierbski.sklep.Users;
import com.pasierbski.sklep.dao.UserDAO;

// import jakarta.ejb.EJB;
// import jakarta.faces.bean.ManagedBean;
// import jakarta.faces.bean.SessionScoped;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@Named
@RequestScoped
public class AuthBean {
    
    private String login;
    private String password;
    
    @Inject
    private UserDAO userDAO;
    
    @Inject
    private RemoteClient remoteClient;
    
    @Inject
    private HttpServletRequest request;
    
    public String auth() {
        List<Users> users = userDAO.getList();
        for (Users user : users) {
            if (user.getUsername().equals(login) && user.getPassword().equals(hashPassword(password))) {
                // HttpSession session = request.getSession();
                FacesContext context = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
                session.setAttribute("user", user);

                // var-dump user to console log
                System.out.println(user);

                return "/panel.xhtml?faces-redirect=true";
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Niepoprawne dane logowania"));
        return null;
    }

    
    
    public String logout() {
        HttpSession session = request.getSession();
        session.invalidate();
        return "index";
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isLogged() {
        HttpSession session = request.getSession();
        return session.getAttribute("user") != null;
    }
    
    public Users getUser() {
        // HttpSession session = request.getSession();
        // return (Users) session.getAttribute("user");

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        return (Users) session.getAttribute("user");

    }
    
    public boolean isAdmin() {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        return user != null && user.getRole() == 2;
    }
    
    public boolean isUser() {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        return user != null && user.getRole() == 1;
    }
    
    public boolean isGuest() {
        HttpSession session = request.getSession();
        return session.getAttribute("user") == null;
    }
    
    public String register() {
        Users user = new Users();
        user.setUsername(login);
        user.setPassword(password);
        user.setRole(2);
        userDAO.addUser(user);
        return "index";
    }
    
    public String registerAdmin() {
        Users user = new Users();
        user.setUsername(login);
        user.setPassword(password);
        user.setRole(1);
        userDAO.addUser(user);
        return "index";
    }
    
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }
}
