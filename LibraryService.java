package services;

import models.Book;
import models.Member;
import models.Loan;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class LibraryService {
    private List<Book> books;
    private List<Member> members;
    private List<Loan> loans;
    private static int bookCounter = 1000;
    private static int memberCounter = 100;
    private static int loanCounter = 1;
    private static final String DATA_DIR = "data";

    public LibraryService() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.loans = new ArrayList<>();
        createDataDirectory();
        loadAllData();
    }

    // ==================== BOOK OPERATIONS ====================
    public boolean addBook(String title, String author, String isbn, int copies, LocalDate pubDate) {
        if (isbn == null || isbn.isEmpty()) {
            System.out.println("❌ ISBN cannot be empty!");
            return false;
        }
        
        // Check if book already exists by ISBN
        if (books.stream().anyMatch(b -> b.getIsbn().equals(isbn))) {
            System.out.println("❌ Book with ISBN " + isbn + " already exists!");
            return false;
        }

        String bookId = "B" + (bookCounter++);
        Book book = new Book(bookId, title, author, isbn, copies, pubDate);
        books.add(book);
        System.out.println("✅ Book added successfully! ID: " + bookId);
        return true;
    }

    public boolean removeBook(String bookId) {
        // Check if book has active loans
        if (loans.stream().anyMatch(l -> l.getBookId().equals(bookId) && !l.isReturned())) {
            System.out.println("❌ Cannot remove book with active loans!");
            return false;
        }

        boolean removed = books.removeIf(b -> b.getBookId().equals(bookId));
        if (removed) {
            System.out.println("✅ Book removed successfully!");
        } else {
            System.out.println("❌ Book not found!");
        }
        return removed;
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerKeyword) ||
                book.getAuthor().toLowerCase().contains(lowerKeyword) ||
                book.getIsbn().toLowerCase().contains(lowerKeyword)) {
                results.add(book);
            }
        }
        return results;
    }

    public Book getBook(String bookId) {
        return books.stream().filter(b -> b.getBookId().equals(bookId)).findFirst().orElse(null);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    // ==================== MEMBER OPERATIONS ====================
    public boolean addMember(String name, String email, String phone) {
        if (name == null || name.isEmpty()) {
            System.out.println("❌ Name cannot be empty!");
            return false;
        }

        String memberId = "M" + (memberCounter++);
        Member member = new Member(memberId, name, email, phone);
        members.add(member);
        System.out.println("✅ Member added successfully! ID: " + memberId);
        return true;
    }

    public boolean removeMember(String memberId) {
        // Check if member has active loans
        if (loans.stream().anyMatch(l -> l.getMemberId().equals(memberId) && !l.isReturned())) {
            System.out.println("❌ Cannot remove member with active loans!");
            return false;
        }

        boolean removed = members.removeIf(m -> m.getMemberId().equals(memberId));
        if (removed) {
            System.out.println("✅ Member removed successfully!");
        } else {
            System.out.println("❌ Member not found!");
        }
        return removed;
    }

    public Member getMember(String memberId) {
        return members.stream().filter(m -> m.getMemberId().equals(memberId)).findFirst().orElse(null);
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members);
    }

    // ==================== LOAN OPERATIONS ====================
    public boolean borrowBook(String bookId, String memberId) {
        Book book = getBook(bookId);
        Member member = getMember(memberId);

        if (book == null) {
            System.out.println("❌ Book not found!");
            return false;
        }

        if (member == null) {
            System.out.println("❌ Member not found!");
            return false;
        }

        if (book.getAvailableCopies() <= 0) {
            System.out.println("❌ Book is not available for borrowing!");
            return false;
        }

        if (member.getActiveLoanCount() >= 5) {
            System.out.println("❌ Member has reached the maximum borrow limit (5 books)!");
            return false;
        }

        String loanId = "L" + (loanCounter++);
        Loan loan = new Loan(loanId, bookId, memberId, LocalDate.now());
        
        book.borrowCopy();
        member.addBorrowedBook(loan);
        loans.add(loan);
        
        System.out.println("✅ Book borrowed successfully!");
        System.out.println("   Loan ID: " + loanId);
        System.out.println("   Due Date: " + loan.getDueDate());
        return true;
    }

    public boolean returnBook(String loanId) {
        Loan loan = loans.stream().filter(l -> l.getLoanId().equals(loanId)).findFirst().orElse(null);

        if (loan == null) {
            System.out.println("❌ Loan not found!");
            return false;
        }

        if (loan.isReturned()) {
            System.out.println("❌ Book already returned!");
            return false;
        }

        Book book = getBook(loan.getBookId());
        Member member = getMember(loan.getMemberId());

        if (book != null) {
            book.returnCopy();
        }

        loan.setReturned(true);
        loan.setReturnDate(LocalDate.now());

        double fine = loan.calculateFine();
        if (fine > 0) {
            member.setFineAmount(member.getFineAmount() + fine);
            System.out.println("⚠️  Book returned with " + Math.round(fine) + " days overdue!");
            System.out.println("   Fine charged: $" + String.format("%.2f", fine));
        } else {
            System.out.println("✅ Book returned successfully!");
        }

        return true;
    }

    public List<Loan> getActiveLoansByMember(String memberId) {
        List<Loan> active = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getMemberId().equals(memberId) && !loan.isReturned()) {
                active.add(loan);
            }
        }
        return active;
    }

    public List<Loan> getOverdueLoans() {
        List<Loan> overdue = new ArrayList<>();
        for (Loan loan : loans) {
            if (!loan.isReturned() && loan.isOverdue()) {
                overdue.add(loan);
            }
        }
        return overdue;
    }

    // ==================== DATA PERSISTENCE ====================
    private void createDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void saveAllData() {
        saveBooks();
        saveMembers();
        saveLoans();
        System.out.println("✅ All data saved successfully!");
    }

    private void saveBooks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_DIR + "/books.dat"))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.out.println("❌ Error saving books: " + e.getMessage());
        }
    }

    private void saveMembers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_DIR + "/members.dat"))) {
            oos.writeObject(members);
        } catch (IOException e) {
            System.out.println("❌ Error saving members: " + e.getMessage());
        }
    }

    private void saveLoans() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_DIR + "/loans.dat"))) {
            oos.writeObject(loans);
        } catch (IOException e) {
            System.out.println("❌ Error saving loans: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadAllData() {
        loadBooks();
        loadMembers();
        loadLoans();
    }

    @SuppressWarnings("unchecked")
    private void loadBooks() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_DIR + "/books.dat"))) {
            books = (List<Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            books = new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadMembers() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_DIR + "/members.dat"))) {
            members = (List<Member>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            members = new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadLoans() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_DIR + "/loans.dat"))) {
            loans = (List<Loan>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            loans = new ArrayList<>();
        }
    }
}
