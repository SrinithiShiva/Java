package books;

import java.time.Year;
import java.util.HashMap;
import displayPanel.BookDetailsDisplayPanel;
import members.Member;

/** This class handles all the books in the system
 * Each book has a list of copies and has a display panel to display the details
 */
public class Books
{
    private String ISBN;
    private String title;
    private String author;
    private Year publicationYear;
    private BookCategory bookCategory;
    private Integer availableCopies;
    private BookDetailsDisplayPanel bookDetailsDisplayPanel;
    private HashMap<String,BookCopy> bookCopies;

    public Books(String ISBN,String title, String author, String publicationYear, BookCategory bookCategory, Integer availableCopies){
        this.ISBN=ISBN;
        this.title=title;
        this.author=author;
        this.publicationYear=Year.parse(publicationYear);
        this.bookCategory=bookCategory;
        this.availableCopies=availableCopies;
        this.bookCopies=new HashMap<>();
        this.bookDetailsDisplayPanel=new BookDetailsDisplayPanel();
    }
    public String ISBN() {
        return this.ISBN;
    }
    public String title() {
        return this.title;
    }
    public String author() {
        return this.author;
    }
    public Year publicationYear() {
        return this.publicationYear;
    }
    public BookCategory bookCategory() {
        return this.bookCategory;
    }
    public HashMap<String, BookCopy> bookCopies() {
        return this.bookCopies;
    }
    public BookDetailsDisplayPanel bookDetailsDisplayPanel(){
        return this.bookDetailsDisplayPanel;
    }
    public Integer availableCopies() {
        return this.availableCopies;
    }
    public void borrow(String bookCopyId,Member member){
        this.bookCopies.get(bookCopyId).borrow(member);
    }
    public void returnBook(String bookCopyId,Member member){
        this.bookCopies.get(bookCopyId).returnBook(member);
    }
    public void reserveBook(String bookCopyId,Member member){
        this.bookCopies.get(bookCopyId).reserveBook(member);
    }
}
