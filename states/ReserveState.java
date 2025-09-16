package states;

import books.BookCopy;
import members.Member;
import observers.BookReserveObserver;

/** Reserve state:
 *  Here we borrow the product
 *  But returning or reserving a reserved book is not allowed
 */
public class ReserveState implements BookState{
    @Override
    public void borrowProduct(BookCopy bookCopy, final Member member) {
        if(BookReserveObserver.getInstance().isAObserver(bookCopy,member)){
            System.out.println(member.name().concat("who has reserved is now borrowing book: ").concat(bookCopy.getBooks().title()));
            BookReserveObserver.getInstance().removeObserver(bookCopy,member);
            bookCopy.setBookState(new BorrowState());
            member.borrowHistory().setActiveBorrowsBook(bookCopy);
            member.borrowHistory().removePrevBorrowBooks(bookCopy);
        }else{
            System.out.println("The item is reserved by another member");
        }
    }

    @Override
    public void returnProduct(BookCopy bookCopy, final Member member) {
        System.out.println("Item is in reserved state and cannot be returned.Borrowed entity can only be returned");
    }

    @Override
    public void reserveProduct(BookCopy bookCopy, final Member member) {
        System.out.println("Book: ".concat(bookCopy.getBooks().title()).concat("is already reserved."));
    }
}
