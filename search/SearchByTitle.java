package search;

import java.util.List;

import books.Books;

public class SearchByTitle implements SearchCriteria<List<Books>,String>{
    @Override
    public boolean search(List<Books> list,final String value) {
        return list.stream().filter(book->book.title().equals(value)).findFirst().isPresent();
    }
}
