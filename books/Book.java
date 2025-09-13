package books;

public abstract class Book
{
    private String ISBN;
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private Category category;
    private int totalCopies;
    private int availableCopies;

    public Book(String title, String author, String publisher, int publicationYear,Category category,int totalCopies)
    {
        this.ISBN = generateISBN(category);
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.category=category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies; // Initially, all copies are available
    }
    private String generateISBN(Category category)
    {
        int randomId=(int) Math.random()*1000000;
        return category.name().substring(2).concat(String.valueOf(randomId));
    }
    public String getISBN()
    {
        return ISBN;
    }

    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public Category getCategory(){
        return category;
    }
    public int getPublicationYear()
    {
        return publicationYear;
    }

    public int getTotalCopies()
    {
        return totalCopies;
    }
    
    public int getAvailableCopies()
    {
        return availableCopies;
    }

}
