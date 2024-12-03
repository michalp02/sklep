package com.pasierbski.sklep.dao;

import com.pasierbski.sklep.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class AuthBB {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Sprawdza poprawność loginu i hasła użytkownika.
     *
     * @param username Nazwa użytkownika
     * @param password Hasło użytkownika (powinno być zahashowane przed porównaniem)
     * @return Obiekt User, jeśli dane są poprawne; null w przeciwnym razie
     */
    public User authenticate(String username, String password) {
        try {
            return entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Rejestruje nowego użytkownika w systemie.
     *
     * @param user Obiekt użytkownika do zapisania
     * @return True, jeśli rejestracja się powiodła; False w przeciwnym razie
     */
    @Transactional
    public boolean registerUser(User user) {
        try {
            entityManager.persist(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Znajduje użytkownika po jego nazwie użytkownika.
     *
     * @param username Nazwa użytkownika
     * @return Obiekt User, jeśli istnieje; null w przeciwnym razie
     */
    public User findByUsername(String username) {
        try {
            return entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
