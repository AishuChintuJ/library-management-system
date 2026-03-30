package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private LocalDate membershipDate;
    private List<Loan> borrowedBooks;
    private double fineAmount;

    public Member(String memberId, String name, String email, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipDate = LocalDate.now();
        this.borrowedBooks = new ArrayList<>();
        this.fineAmount = 0.0;
    }

    // Getters and Setters
    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public List<Loan> getBorrowedBooks() {
        return borrowedBooks;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public void addBorrowedBook(Loan loan) {
        borrowedBooks.add(loan);
    }

    public void removeBorrowedBook(Loan loan) {
        borrowedBooks.remove(loan);
    }

    public int getActiveLoanCount() {
        return (int) borrowedBooks.stream().filter(l -> !l.isReturned()).count();
    }

    @Override
    public String toString() {
        return String.format("Member [ID: %s, Name: %s, Email: %s, Phone: %s, Fine: $%.2f]", 
                memberId, name, email, phone, fineAmount);
    }
}
