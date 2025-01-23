//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private int id;
    private String title;
    private String author;
    private boolean available;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", available=" + available +
                '}';
    }
}

class Member {
    private int id;
    private String name;
    private String email;

    public Member(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

class LibraryService {
    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private int transactionId = 1;

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book);
    }

    public void addMember(Member member) {
        members.add(member);
        System.out.println("Member added: " + member);
    }

    public void issueBook(int bookId, int memberId) {
        Book book = books.stream().filter(b -> b.getId() == bookId && b.isAvailable()).findFirst().orElse(null);
        Member member = members.stream().filter(m -> m.getId() == memberId).findFirst().orElse(null);

        if (book == null) {
            System.out.println("Book not available or does not exist.");
            return;
        }
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        book.setAvailable(false);
        System.out.println("Book issued: " + book.getTitle() + " to Member: " + member.getName());
    }

    public void returnBook(int bookId) {
        Book book = books.stream().filter(b -> b.getId() == bookId && !b.isAvailable()).findFirst().orElse(null);

        if (book == null) {
            System.out.println("Invalid book ID or book is not issued.");
            return;
        }

        book.setAvailable(true);
        System.out.println("Book returned: " + book.getTitle());
    }

    public void listBooks() {
        System.out.println("\nBooks in Library:");
        books.forEach(System.out::println);
    }

    public void listMembers() {
        System.out.println("\nLibrary Members:");
        members.forEach(System.out::println);
    }
}

public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. List Books");
            System.out.println("6. List Members");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 : {
                    System.out.print("Enter Book ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Book Author: ");
                    String author = scanner.nextLine();
                    libraryService.addBook(new Book(id, title, author));
                }
                case 2 : {
                    System.out.print("Enter Member ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Enter Member Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Member Email: ");
                    String email = scanner.nextLine();
                    libraryService.addMember(new Member(id, name, email));
                }
                case 3 : {
                    System.out.print("Enter Book ID to Issue: ");
                    int bookId = scanner.nextInt();
                    System.out.print("Enter Member ID: ");
                    int memberId = scanner.nextInt();
                    libraryService.issueBook(bookId, memberId);
                }
                case 4 : {
                    System.out.print("Enter Book ID to Return: ");
                    int bookId = scanner.nextInt();
                    libraryService.returnBook(bookId);
                }
                case 5 : libraryService.listBooks();
                case 6 : libraryService.listMembers();
                case 7 : {
                    System.out.println("Exiting...");
                    return;
                }
                default : System.out.println("Invalid choice! Try again.");
            }
        }
    }
}