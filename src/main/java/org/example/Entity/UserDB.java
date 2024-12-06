package org.example.Entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class UserDB {
    @Id
    private String membershipCardNumber;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setMembershipCardNumber(String membershipCardNumber) {
        this.membershipCardNumber = membershipCardNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getMembershipCardNumber() {
        return membershipCardNumber;
    }
}