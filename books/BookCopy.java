package books;

import members.Member;
import states.BookState;

public class BookCopy {
    private String bookCopyId;
    private Books books;
    private BookState bookState;

    public BookCopy(String bookCopyId,Books books, BookState state){
        this.bookCopyId=bookCopyId;
        this.books=books;
        this.bookState=state;
    }
    public void setBookState(BookState bookState){
        this.bookState=bookState;
    }
    public BookState getBookState(){
        return bookState;
    }
    public String getBookCopyId() {
        return this.bookCopyId;
    }
    public Books getBooks() {
        return this.books;
    }
    public void borrow(Member member){
        this.getBookState().borrowProduct(this,member);
    }
    public void returnBook(Member member){
        this.getBookState().returnProduct(this,member);
    }
    public void reserveBook(Member member){
        this.getBookState().reserveProduct(this,member);
    }
}
