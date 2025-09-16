package displayPanel;

import java.util.HashMap;
import books.BookCopy;

public class PreviousBorrowsDisplayPanel implements Display<HashMap<String, BookCopy>> {
    @Override
    public void display(final HashMap<String, BookCopy> previousBorrows)
    {
        System.out.println("==== Previous Borrow of the Member ===");
        if(previousBorrows.size()>0)
        {
            for (BookCopy bookCopy : previousBorrows.values())
            {
                System.out.println("BookCopyID: ".concat(bookCopy.getBookCopyId()).concat(" - ").concat(bookCopy.getBooks().title()));
            }
        }
        else
        {
            System.out.println("----- No Previous Borrows ----");
        }
        System.out.println();
    }
}
