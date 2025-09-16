package borrowAndLoan;

import java.util.HashMap;
import books.BookCopy;
import displayPanel.ActiveBorrowsDisplayPanel;
import displayPanel.PreviousBorrowsDisplayPanel;

/** This class contains data related to the borrow history
 *  Each member has a borrow history
 *  Borrow history has an active borrow and previous borrows display panel
 */
public class BorrowHistory {
    private HashMap<String,BookCopy> activeBorrows=new HashMap<>();
    private HashMap<String,BookCopy> prevBorrows=new HashMap<>();
    ActiveBorrowsDisplayPanel activeBorrowsDisplayPanel =new ActiveBorrowsDisplayPanel();
    PreviousBorrowsDisplayPanel previousBorrowsDisplayPanel=new PreviousBorrowsDisplayPanel();

    public void setActiveBorrowsBook(BookCopy bookCopy){
        this.activeBorrows.put(bookCopy.getBookCopyId(),bookCopy);
    }
    public void removeActiveBorrowsBook(BookCopy bookCopy){
        this.activeBorrows.remove(bookCopy.getBookCopyId());
    }
    public void setPrevBorrowsBook(BookCopy bookCopy){
        this.prevBorrows.put(bookCopy.getBookCopyId(),bookCopy);
    }
    public void removePrevBorrowBooks(BookCopy bookCopy){
        this.prevBorrows.remove(bookCopy.getBookCopyId());
    }
    public HashMap<String,BookCopy> getActiveBorrows(){
        return this.activeBorrows;
    }
    public HashMap<String,BookCopy> getPreviousBorrows(){
        return this.prevBorrows;
    }
    public boolean isBookInActiveBorrow(String bookCopyId){
        return this.activeBorrows.containsKey(bookCopyId);
    }
    public ActiveBorrowsDisplayPanel getActiveBorrowsDisplayPanel(){
        return this.activeBorrowsDisplayPanel;
    }
    public PreviousBorrowsDisplayPanel getPreviousBorrowsDisplayPanel(){
        return this.previousBorrowsDisplayPanel;
    }
}
