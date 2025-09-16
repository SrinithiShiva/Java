package states;

import books.BookCopy;
import members.Member;

public interface BookState {
    void borrowProduct(BookCopy bookCopy, Member member);
    void returnProduct(BookCopy bookCopy, Member member);
    void reserveProduct(BookCopy bookCopy, Member member);
}
