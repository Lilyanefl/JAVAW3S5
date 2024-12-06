package org.example.Entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserDB user;

    @ManyToOne
    private CatalogItem catalogItem;

    private LocalDate loanStartDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;

    @PrePersist
    public void calculateExpectedReturnDate() {
        this.expectedReturnDate = this.loanStartDate.plusDays(30);
    }

    public void setUser(UserDB user) {
        this.user = user;
    }

    public void setCatalogItem(CatalogItem catalogItem) {
        this.catalogItem = catalogItem;
    }

    public void setLoanStartDate(LocalDate loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public void setExpectedReturnDate(LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public UserDB getUser() {
        return user;
    }

    public CatalogItem getCatalogItem() {
        return catalogItem;
    }
}