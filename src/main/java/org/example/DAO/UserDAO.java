package org.example.DAO;

import org.example.Entity.UserDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UserDAO {

    private EntityManager entityManager;

    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserDB findUserByMembershipCard(String membershipCardNumber) {
        String jpql = "SELECT u FROM UserDB u WHERE u.membershipCardNumber = :membershipCardNumber";
        TypedQuery<UserDB> query = entityManager.createQuery(jpql, UserDB.class);
        query.setParameter("membershipCardNumber", membershipCardNumber);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Utente non trovato per la membership card: " + membershipCardNumber);
            return null;
        }
    }
}
