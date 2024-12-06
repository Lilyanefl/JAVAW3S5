package org.example.DAO;
import org.example.Entity.Loan;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class LoanDAO {
    private EntityManager entityManager;

    public LoanDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void addLoan(Loan loan) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(loan);
        transaction.commit();
    }

    public List<Loan> findLoansByMembershipCardNumber(String membershipCardNumber) {
        return entityManager.createQuery("SELECT l FROM Loan l WHERE l.user.membershipCardNumber = :cardNumber AND l.actualReturnDate IS NULL", Loan.class)
                .setParameter("cardNumber", membershipCardNumber)
                .getResultList();
    }

    public List<Loan> findOverdueLoans() {
        return entityManager.createQuery("SELECT l FROM Loan l WHERE l.expectedReturnDate < :today ", Loan.class)
                .setParameter("today", LocalDate.now())
                .getResultList();
    }
    public void updateActualReturnDate(LocalDate actualReturnDate, LocalDate loanStartDate, String isbn, String membershipCardNumber) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        String jpql = "UPDATE Loan l SET l.actualReturnDate = :actualReturnDate " +
                "WHERE l.loanStartDate = :loanStartDate AND l.catalogItem.isbn = :isbn AND l.user.membershipCardNumber = :membershipCardNumber";

        int updatedCount = entityManager.createQuery(jpql)
                .setParameter("actualReturnDate", actualReturnDate)
                .setParameter("loanStartDate", loanStartDate)
                .setParameter("isbn", isbn)
                .setParameter("membershipCardNumber", membershipCardNumber)
                .executeUpdate();

        transaction.commit();
    }
}