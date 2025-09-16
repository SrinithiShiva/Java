package displayPanel;

import java.util.HashMap;
import books.BookCopy;

public class ActiveBorrowsDisplayPanel implements Display<HashMap<String,BookCopy>>{
    @Override
    public void display(HashMap<String,BookCopy> activeBorrows)
    {
        System.out.println("==== Active Borrow of the Member ===");
        if(activeBorrows.size()>0)
        {
            for (BookCopy bookCopy : activeBorrows.values())
            {
                System.out.println("BookCopyID: ".concat(bookCopy.getBookCopyId()).concat(" - ").concat(bookCopy.getBooks().title()));
            }
        }
        else
        {
            System.out.println("----- No Active Borrows ----");
        }
        System.out.println();
    }
}
