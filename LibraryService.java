Member.java 
   package assignment; 
   import java.util.*; 
   abstract class Member { 
    protected int memberId; 
    protected String name; 
    protected String email; 
    protected String phone; 
    protected List<Book> borrowedBooks = new ArrayList<>(); 
    public Member(int memberId, String name, String email, String phone) { 
        this.memberId = memberId; 
        this.name = name; 
        this.email = email; 
        this.phone = phone; 
    } 
    public abstract boolean borrow(Book book); 
    public abstract double calcFine(int lateDays); 
    public void returnBook(Book book) { 
        if (borrowedBooks.contains(book)) { 
            borrowedBooks.remove(book); 
            book.setAvailable(true); 
            System.out.println(name + " returned book: " + book.getTitle()); 
        } else { 
            System.out.println("Book not found in borrowed list!"); 
        } 
    } 
    public List<Book> getBorrowedBooks() { 
        return borrowedBooks; 
    } 
    public String getName() { 
        return name; 
    } 
} 
 
 
StudentMember.java: 
   package assignment; 
class StudentMember extends Member { 
    private int semester; 
    private String dept; 
    private int borrowLimit; 
 
    public StudentMember(int memberId, String name, String email, String phone, int 
semester, String dept) { 
        super(memberId, name, email, phone); 
        this.semester = semester;  
        this.dept = dept; 
        this.borrowLimit = 3;  
    } 
    @Override 
    public boolean borrow(Book book) { 
        if (borrowedBooks.size() < borrowLimit && book.isAvailable()) { 
            borrowedBooks.add(book); 
            book.setAvailable(false); 
            System.out.println(name + " borrowed book: " + book.getTitle()); 
            return true; 
        } else { 
            System.out.println("Borrowing limit reached or book not available!"); 
            return false; 
        } 
    } 
    @Override 
    public double calcFine(int lateDays) { 
        return lateDays * 2;  
    } 
} 
 
FacultyMember.java 
 
package assignment; 
class FacultyMember extends Member { 
    private String designation; 
    private int borrowLimit; 
    private int priority; 
    public FacultyMember(int memberId, String name, String email, String phone, String designation) { 
        super(memberId, name, email, phone); 
        this.designation = designation; 
        this.borrowLimit = 5;  
        this.priority = 1; 
    } 
    @Override 
    public boolean borrow(Book book) { 
        if (borrowedBooks.size() < borrowLimit && book.isAvailable()) { 
            borrowedBooks.add(book); 
            book.setAvailable(false); 
            System.out.println("Faculty " + name + " borrowed book: " + book.getTitle()); 
            return true; 
        } else { 
            System.out.println("Borrowing limit reached or book not available!"); 
            return false; 
        } 
    } 
    @Override 
    public double calcFine(int lateDays) { 
        return lateDays * 1;  
    } 
}   
Book.java: 
package assignment; 
class Book { 
    private int bookId; 
    private String title; 
    private String author; 
    private String category; 
    private boolean available; 
    public Book(int bookId, String title, String author, String category) { 
        this.bookId = bookId; 
        this.title = title; 
        this.author = author; 
        this.category = category; 
        this.available = true; 
    } 
    public int getBookId() { return bookId; } 
    public String getTitle() { return title; } 
    public String getAuthor() { return author; } 
    public String getCategory() { return category; } 
    public boolean isAvailable() { return available; } 
    public void setAvailable(boolean available) { this.available = available; } 
} 
 
LibraryService.java: 
 
package assignment; 
import java.util.List; 
import java.util.ArrayList; 
class LibraryService { 
    private List<Book> books = new ArrayList<>(); 
    private List<Member> members = new ArrayList<>(); 
    public void addBook(Book book) { 
        books.add(book); 
    } 
    public void addMember(Member member) { 
        members.add(member); 
    } 
    public Book search(String title) { 
        for (Book b : books) { 
            if (b.getTitle().equalsIgnoreCase(title)) return b; 
        } 
        return null; 
    } 
    public Book searchByAuthor(String author) { 
        for (Book b : books) { 
            if (b.getAuthor().equalsIgnoreCase(author)) return b; 
        } 
 
        return null; 
    } 
    public Book search(int bookId) { 
        for (Book b : books) { 
            if (b.getBookId() == bookId) return b; 
        } 
        return null; 
    } 
    public void dailyReport() { 
        System.out.println("\n Daily Issue/Return Report "); 
        for (Member m : members) { 
            System.out.println("Member: " + m.getName()); 
            for (Book b : m.getBorrowedBooks()) { 
                System.out.println("  Borrowed: " + b.getTitle()); 
            } 
        } 
    } 
} 
LibraryAppMain 
 
package assignment; 
public class LibraryAppMain { 
    public static void main(String[] args) { 
        LibraryService service = new LibraryService(); 
        Book b1 = new Book(101, "Java Programming", "James Gosling", "Programming"); 
        Book b2 = new Book(102, "Database Systems", "Navathe", "Database"); 
        Book b3 = new Book(103, "Web Technology", "Ramesh", "Web"); 
        service.addBook(b1); 
        service.addBook(b2); 
        service.addBook(b3); 
        StudentMember s1 = new StudentMember(1, "Arun", "arun@gmail.com", 
"9876543210", 3, "IT"); 
        FacultyMember f1 = new FacultyMember(2, "Dr. Kumar", "kumar@college.edu", 
"9876501234", "Professor"); 
        service.addMember(s1); 
        service.addMember(f1); 
        s1.borrow(b1); 
        f1.borrow(b2); 
        s1.returnBook(b1); 
        System.out.println("Fine for Arun (5 late days): Rs." + s1.calcFine(5)); 
        System.out.println("Fine for Dr. Kumar (5 late days): Rs." + f1.calcFine(5)); 
        Book searchResult = service.search("Web Technology"); 
        if (searchResult != null) { 
            System.out.println("Book found: " + searchResult.getTitle() + " by " + 
searchResult.getAuthor()); 
        } 
        service.dailyReport(); 
    }  
} 
