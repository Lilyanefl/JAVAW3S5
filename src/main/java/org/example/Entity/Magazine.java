package org.example.Entity;
import jakarta.persistence.*;

@Entity
public class Magazine extends CatalogItem {


    @Enumerated(EnumType.STRING)
    private Periodicity periodicity;

    public enum Periodicity {
        WEEKLY, MONTHLY, SEMIANNUAL
    }
    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

}