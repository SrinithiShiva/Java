package states;

import books.BookCopy;
import members.Member;
import observers.BookReserveObserver;

/** Borrow state: when return we calculate the fees
 * Calculate the rate is per book for each member type
 * then we set the state to available if case no one is reserved else on hold
 */
public class BorrowState implements BookState{
    @Override
    public void borrowProduct(BookCopy bookCopy, final Member member) {
        System.out.println(bookCopy.getBookCopyId() + " is already Borrowed");
    }
    @Override
    public void returnProduct(BookCopy bookCopy, final Member member) {
        System.out.println(member.name().concat(" has returned the book: ").concat(bookCopy.getBooks().title()));
        member.getLoanHistory().calculate(member);
        member.getLoanHistory().loanDetailsDisplay().display(member.getLoanHistory());
        member.borrowHistory().removeActiveBorrowsBook(bookCopy);
        member.borrowHistory().setPrevBorrowsBook(bookCopy);
        if(BookReserveObserver.getInstance().hasObserver(bookCopy)){
            bookCopy.setBookState(new ReserveState());
            BookReserveObserver.getInstance().notifyObserver(bookCopy);
        }else {
            bookCopy.setBookState(new AvailableState());
        }
    }

    @Override
    public void reserveProduct(BookCopy bookCopy, final Member member) {
        BookReserveObserver.getInstance().addObserver(bookCopy,member);
        System.out.println("Book: ".concat(bookCopy.getBooks().title()).concat(" is reserved for the member: ").concat(member.name()));
    }
}
