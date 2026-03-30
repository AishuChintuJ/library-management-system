package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int LOAN_DAYS = 14;
    private static final double FINE_PER_DAY = 1.0;
    
    private String loanId;
    private String bookId;
    private String memberId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean returned;

    public Loan(String loanId, String bookId, String memberId, LocalDate borrowDate) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusDays(LOAN_DAYS);
        this.returned = false;
    }

    // Getters and Setters
    public String getLoanId() {
        return loanId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public double calculateFine() {
        if (returned && returnDate.isAfter(dueDate)) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate, returnDate);
            return daysOverdue * FINE_PER_DAY;
        } else if (!returned && LocalDate.now().isAfter(dueDate)) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
            return daysOverdue * FINE_PER_DAY;
        }
        return 0.0;
    }

    public boolean isOverdue() {
        return !returned && LocalDate.now().isAfter(dueDate);
    }

    @Override
    public String toString() {
        return String.format("Loan [ID: %s, Book: %s, Member: %s, Borrowed: %s, Due: %s, Returned: %s]",
                loanId, bookId, memberId, borrowDate, dueDate, returned ? returnDate : "Pending");
    }
}
