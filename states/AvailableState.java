package states;

import books.BookCopy;
import members.Member;

/** Status Available:
 *  -> Setting status as borrowed
 *  -> adding the book to active borrows of member
 */
public class AvailableState implements BookState{

    @Override
    public void borrowProduct(BookCopy bookCopy, Member member) {
        System.out.println(member.name().concat(" has borrowed the book: ").concat(bookCopy.getBooks().title()));
        bookCopy.setBookState(new BorrowState());
        member.borrowHistory().setActiveBorrowsBook(bookCopy);
        member.borrowHistory().removePrevBorrowBooks(bookCopy); //if the member is borrowing again then it is an active borrow and removing from prev
    }

    @Override
    public void returnProduct(BookCopy bookCopy, Member member) {
        System.out.println("Book is not borrowed.Kindly borrowProduct the book to return");
    }

    @Override
    public void reserveProduct(BookCopy bookCopy, Member member) {
        System.out.println("Book is already Available.Hence cannot be reserved");
    }
}
