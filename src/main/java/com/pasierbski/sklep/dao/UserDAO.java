package com.pasierbski.sklep.dao;

import com.pasierbski.sklep.Users;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;


public class UserDAO {
        
            @PersistenceContext
            private EntityManager em;
        
        
            public List<Users> getList() {
            return em.createNamedQuery("Users.findAll", Users.class).getResultList();
            }
            
            public List<Users> getList(Map<String, Object> searchParams) {
            StringBuilder jpql = new StringBuilder("SELECT u FROM Users u WHERE 1=1");
            var query = em.createQuery(jpql.toString(), Users.class);
            return query.getResultList();
            }

            public Users getUserById(int id) {
                return em.createNamedQuery("Users.findByUserId", Users.class).setParameter("userId", id).getSingleResult();
            }

            public Users getUserByUsername(String username) {
                return em.createNamedQuery("Users.findByUsername", Users.class).setParameter("username", username).getSingleResult();
            }

            public Users getUserByUsernameAndPassword(String username, String password) {
                return em.createNamedQuery("Users.findByUsernameAndPassword", Users.class).setParameter("username", username).setParameter("password", password).getSingleResult();
            }

            public void addUser(Users user) {
                em.persist(user);
            }

            public void updateUser(Users user) {
                em.merge(user);
            }

            public void deleteUser(Users user) {
                em.remove(em.merge(user));
            }

            // Getter i setter roli. 0 - brak, 1 - admin, 2 - user
            public String getRole(Users user) {
                int role = user.getRole();
                if (role == 1) {
                    return "admin";
                } else if (role == 2) {
                    return "user";
                } else {
                    return "";
                }
            }

            public void setRole(Users user, String roletext) {
                int role = 0;
                if (roletext.equals("admin")) {
                    role = 1;
                } else if (roletext.equals("user")) {
                    role = 2;
                }
                user.setRole(role);
            }


    
}
