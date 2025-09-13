package books.impl;
import books.Book;
import books.Category;

public class AudioBooks extends Book
{
    private double duration;
    private String audioFormat;
    public AudioBooks(String title, String author, String publisher, int publicationYear, Category category, int totalCopies,double duration,String audioFormat)
    {
        super(title,author,publisher,publicationYear,category,totalCopies);
        this.duration=duration;
        this.audioFormat=audioFormat;
    }

}