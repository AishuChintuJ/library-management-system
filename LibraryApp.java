package ui;

import services.LibraryService;
import models.Book;
import models.Member;
import models.Loan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {
    private LibraryService libraryService;
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;

    public LibraryApp() {
        this.libraryService = new LibraryService();
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public void run() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   📚 LIBRARY MANAGEMENT SYSTEM 📚              ║");
        System.out.println("╚════════════════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            displayMainMenu();
            System.out.print("\n➤ Select an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    bookManagementMenu();
                    break;
                case "2":
                    memberManagementMenu();
                    break;
                case "3":
                    loanManagementMenu();
                    break;
                case "4":
                    displayReports();
                    break;
                case "5":
                    libraryService.saveAllData();
                    System.out.println("\n👋 Thank you for using Library Management System!");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n╭─── MAIN MENU ───╮");
        System.out.println("│ 1. 📕 Book Management");
        System.out.println("│ 2. 👥 Member Management");
        System.out.println("│ 3. 📤 Loan Management");
        System.out.println("│ 4. 📊 Reports");
        System.out.println("│ 5. 🚪 Exit");
        System.out.println("╰──────────────────╯");
    }

    // ==================== BOOK MANAGEMENT ====================
    private void bookManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n┌─── BOOK MANAGEMENT ───┐");
            System.out.println("│ 1. ➕ Add Book");
            System.out.println("│ 2. 🔍 Search Book");
            System.out.println("│ 3. 📋 View All Books");
            System.out.println("│ 4. ❌ Remove Book");
            System.out.println("│ 5. ⬅️  Back to Main Menu");
            System.out.println("└───────────────────────┘");

            System.out.print("➤ Select an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addBook();
                    break;
                case "2":
                    searchBook();
                    break;
                case "3":
                    viewAllBooks();
                    break;
                case "4":
                    removeBook();
                    break;
                case "5":
                    inMenu = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    private void addBook() {
        System.out.println("\n➕ ADD NEW BOOK");
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author name: ");
        String author = scanner.nextLine();

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter number of copies: ");
        int copies = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter publication date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        LocalDate pubDate = LocalDate.parse(dateStr, dateFormatter);

        libraryService.addBook(title, author, isbn, copies, pubDate);
    }

    private void searchBook() {
        System.out.print("\n🔍 Enter search keyword (title/author/ISBN): ");
        String keyword = scanner.nextLine();

        List<Book> results = libraryService.searchBooks(keyword);
        if (results.isEmpty()) {
            System.out.println("❌ No books found!");
        } else {
            System.out.println("\n📚 Search Results:");
            for (Book book : results) {
                System.out.println("   " + book);
            }
        }
    }

    private void viewAllBooks() {
        List<Book> books = libraryService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("❌ Library has no books!");
        } else {
            System.out.println("\n📚 ALL BOOKS IN LIBRARY:");
            for (Book book : books) {
                System.out.println("   " + book);
            }
        }
    }

    private void removeBook() {
        System.out.print("\n❌ Enter book ID to remove: ");
        String bookId = scanner.nextLine();
        libraryService.removeBook(bookId);
    }

    // ==================== MEMBER MANAGEMENT ====================
    private void memberManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n┌─── MEMBER MANAGEMENT ───┐");
            System.out.println("│ 1. ➕ Add Member");
            System.out.println("│ 2. 📋 View All Members");
            System.out.println("│ 3. 👤 View Member Details");
            System.out.println("│ 4. ❌ Remove Member");
            System.out.println("│ 5. ⬅️  Back to Main Menu");
            System.out.println("└──────────────────────────┘");

            System.out.print("➤ Select an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addMember();
                    break;
                case "2":
                    viewAllMembers();
                    break;
                case "3":
                    viewMemberDetails();
                    break;
                case "4":
                    removeMember();
                    break;
                case "5":
                    inMenu = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    private void addMember() {
        System.out.println("\n➕ ADD NEW MEMBER");
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        libraryService.addMember(name, email, phone);
    }

    private void viewAllMembers() {
        List<Member> members = libraryService.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("\n❌ No members registered!");
        } else {
            System.out.println("\n👥 ALL MEMBERS:");
            for (Member member : members) {
                System.out.println("   " + member);
            }
        }
    }

    private void viewMemberDetails() {
        System.out.print("\n👤 Enter member ID: ");
        String memberId = scanner.nextLine();

        Member member = libraryService.getMember(memberId);
        if (member == null) {
            System.out.println("❌ Member not found!");
        } else {
            System.out.println("\n👤 MEMBER DETAILS:");
            System.out.println("   " + member);
            System.out.println("   Active Loans: " + member.getActiveLoanCount());

            List<Loan> activeLoans = libraryService.getActiveLoansByMember(memberId);
            if (!activeLoans.isEmpty()) {
                System.out.println("\n   📕 Borrowed Books:");
                for (Loan loan : activeLoans) {
                    Book book = libraryService.getBook(loan.getBookId());
                    System.out.println("      - " + book.getTitle() + " (Due: " + loan.getDueDate() + ")");
                }
            }
        }
    }

    private void removeMember() {
        System.out.print("\n❌ Enter member ID to remove: ");
        String memberId = scanner.nextLine();
        libraryService.removeMember(memberId);
    }

    // ==================== LOAN MANAGEMENT ====================
    private void loanManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n┌─── LOAN MANAGEMENT ───┐");
            System.out.println("│ 1. 📤 Borrow Book");
            System.out.println("│ 2. 📥 Return Book");
            System.out.println("│ 3. 📋 View Active Loans");
            System.out.println("│ 4. ⬅️  Back to Main Menu");
            System.out.println("└───────────────────────┘");

            System.out.print("➤ Select an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    borrowBook();
                    break;
                case "2":
                    returnBook();
                    break;
                case "3":
                    viewActiveLoans();
                    break;
                case "4":
                    inMenu = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    private void borrowBook() {
        System.out.println("\n📤 BORROW BOOK");
        System.out.print("Enter book ID: ");
        String bookId = scanner.nextLine();

        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine();

        libraryService.borrowBook(bookId, memberId);
    }

    private void returnBook() {
        System.out.println("\n📥 RETURN BOOK");
        System.out.print("Enter loan ID: ");
        String loanId = scanner.nextLine();

        libraryService.returnBook(loanId);
    }

    private void viewActiveLoans() {
        System.out.print("\n📋 View loans for member ID (or press Enter to skip): ");
        String memberId = scanner.nextLine().trim();

        if (!memberId.isEmpty()) {
            List<Loan> loans = libraryService.getActiveLoansByMember(memberId);
            if (loans.isEmpty()) {
                System.out.println("❌ No active loans for this member!");
            } else {
                System.out.println("\n📤 ACTIVE LOANS FOR MEMBER " + memberId + ":");
                for (Loan loan : loans) {
                    System.out.println("   " + loan);
                }
            }
        }
    }

    // ==================== REPORTS ====================
    private void displayReports() {
        System.out.println("\n┌─── REPORTS ───┐");
        System.out.println("│ 1. 📊 Library Statistics");
        System.out.println("│ 2. ⏰ Overdue Books");
        System.out.println("└────────────────┘");

        System.out.print("➤ Select an option: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                displayStatistics();
                break;
            case "2":
                displayOverdueBooks();
                break;
            default:
                System.out.println("❌ Invalid choice!");
        }
    }

    private void displayStatistics() {
        System.out.println("\n📊 LIBRARY STATISTICS:");
        System.out.println("   Total Books: " + libraryService.getAllBooks().size());
        System.out.println("   Total Members: " + libraryService.getAllMembers().size());
        System.out.println("   Overdue Books: " + libraryService.getOverdueLoans().size());
    }

    private void displayOverdueBooks() {
        List<Loan> overdueLoans = libraryService.getOverdueLoans();
        if (overdueLoans.isEmpty()) {
            System.out.println("\n✅ No overdue books!");
        } else {
            System.out.println("\n⏰ OVERDUE BOOKS:");
            for (Loan loan : overdueLoans) {
                Member member = libraryService.getMember(loan.getMemberId());
                Book book = libraryService.getBook(loan.getBookId());
                double fine = loan.calculateFine();
                System.out.println("   📕 " + book.getTitle() + " by " + member.getName() +
                        " (Fine: $" + String.format("%.2f", fine) + ")");
            }
        }
    }

    public static void main(String[] args) {
        LibraryApp app = new LibraryApp();
        app.run();
    }
}
