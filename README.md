# 📚 Library Management System

A comprehensive Java-based Library Management System with persistent data storage and an intuitive CLI interface.

## Features

✅ **Book Management**
- Add, remove, and search books
- Track available copies
- ISBN-based duplicate prevention

✅ **Member Management**
- Register and manage library members
- Track member information
- Monitor active loans per member

✅ **Loan Management**
- Borrow and return books
- Automatic due date assignment (14 days)
- Overdue tracking and fine calculation ($1/day)
- Maximum 5 concurrent loans per member

✅ **Reports & Analytics**
- Library statistics
- Overdue books list
- Member borrowing history

✅ **Data Persistence**
- Automatic save/load using Java serialization
- Data stored in `data/` directory

## Project Structure

```
library-management-system/
├── src/
│   ├── models/
│   │   ├── Book.java          # Book model class
│   │   ├── Member.java        # Member model class
│   │   └── Loan.java          # Loan model class
│   ├── services/
│   │   └── LibraryService.java # Business logic & persistence
│   ├── ui/
│   │   └── LibraryApp.java    # CLI interface & main method
│   └── Main.java              # Entry point (optional)
├── data/                       # Data storage directory
├── README.md
└── .gitignore
```

## Installation & Setup

### Prerequisites
- Java 8 or higher
- Git

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/AishuChintuJ/library-management-system.git
   cd library-management-system
   ```

2. **Compile the project**
   ```bash
   javac -d bin src/models/*.java src/services/*.java src/ui/*.java
   ```

3. **Run the application**
   ```bash
   java -cp bin ui.LibraryApp
   ```

## Usage

### Main Menu Options

```
1. 📕 Book Management
   - Add new books
   - Search books by title/author/ISBN
   - View all books
   - Remove books

2. 👥 Member Management
   - Register new members
   - View all members
   - View member details & borrowing history
   - Remove members

3. 📤 Loan Management
   - Borrow books (max 5 per member)
   - Return books with automatic fine calculation
   - View active loans

4. 📊 Reports
   - Library statistics
   - Overdue books report

5. 🚪 Exit
   - Save all data and exit
```

## Example Workflow

```
1. Add a member: John Doe (M101)
2. Add a book: "Clean Code" by Robert Martin (ISBN: 978-0132350884)
3. Borrow: Member M101 borrows Book B1000
4. Return: Member returns Book B1000 (with fine if overdue)
5. View overdue books and generate reports
```

## Data Structure

### Book
- **ID**: B1000, B1001, ...
- **Title, Author, ISBN**
- **Total Copies & Available Copies**
- **Publication Date**

### Member
- **ID**: M100, M101, ...
- **Name, Email, Phone**
- **Membership Date**
- **Fine Amount**

### Loan
- **ID**: L1, L2, ...
- **Book ID & Member ID**
- **Borrow & Due Date**
- **Return Status & Fine Calculation**

## Technical Details

- **Language**: Java 8+
- **Storage**: Serialized objects in binary format
- **Architecture**: Service-based with separation of concerns
- **Error Handling**: Input validation and exception handling
- **Persistence**: Automatic save on exit

## Future Enhancements

- [ ] Graphical User Interface (Swing/JavaFX)
- [ ] Database integration (MySQL/PostgreSQL)
- [ ] Email notifications for due dates
- [ ] Reservation system
- [ ] Advanced search and filtering
- [ ] User authentication & role-based access
- [ ] REST API

## Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -m 'Add YourFeature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Author

**AishuChintuJ**

## Support

For issues, questions, or suggestions, please open an issue on GitHub.

---

Made with ❤️ for library management
