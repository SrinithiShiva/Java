package books.impl;

import books.Book;
import books.Category;

public class Ebooks extends Book
{
    private Double duration;
    private String link;
    private String fileFormat;
    private String fileSize;
    public Ebooks(String title, String author, String publisher, int publicationYear, Category category, int totalCopies, Double duration, String link, String fileFormat, String fileSize){
        super( title, author, publisher, publicationYear,category,totalCopies);
        this.duration = duration;
        this.link = link;
        this.fileFormat = fileFormat;
        this.fileSize = fileSize;   
    }
}