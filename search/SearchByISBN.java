package search;

import java.util.List;

import books.Books;

public class SearchByISBN implements SearchCriteria<List<Books>,String>{
    @Override
    public boolean search(List<Books> list,final String value) {
        return list.stream().filter(book->book.ISBN().equals(value)).findFirst().isPresent();
    }
}
