package books.impl;
import books.Book;
import books.Category;

public class PhysicalBooks extends Book
{
    private int pages;
    private String location;
    public PhysicalBooks(String title, String author, String publisher, int publicationYear, Category category, int pages, int totalCopies, String location)
    {
        super(title, author, publisher, publicationYear,category,totalCopies);
        this.pages = pages;
        this.location = location;
    }

}