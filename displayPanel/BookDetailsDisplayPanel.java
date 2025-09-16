package displayPanel;

import books.BookCopy;
import books.Books;
import states.AvailableState;
import states.BorrowState;

public class BookDetailsDisplayPanel implements Display<Books>{
    @Override
    public void display(Books books) {
        System.out.println("====== Book Details of ".concat(books.title()).concat(" ======"));
        System.out.println("ISBN:: ".concat(books.ISBN()));
        System.out.println("Title:: ".concat(books.title()));
        System.out.println("Author:: ".concat(books.author()));
        System.out.println("Category:: ".concat(books.bookCategory().name()));
        System.out.println("Publication Year:: ".concat(books.publicationYear().toString()));
        System.out.println("Total Copies:: ".concat(books.availableCopies().toString()));
        System.out.println(" Details of Copies:: ");
        for(BookCopy bookCopy:books.bookCopies().values()){
            System.out.println("BookCopyID: ".concat(bookCopy.getBookCopyId()).concat(" - ").concat(bookCopy.getBookState() instanceof AvailableState?"available😁":bookCopy.getBookState() instanceof BorrowState ?"borrowed😢":"reserved🫡"));
        }
        System.out.println();
    }
}
