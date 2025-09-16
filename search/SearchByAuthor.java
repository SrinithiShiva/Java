package search;

import java.util.List;

import books.Books;

public class SearchByAuthor implements SearchCriteria<List<Books>,String>{
    @Override
    public boolean search(List<Books> list,final String value) {
        return list.stream().filter(book->book.author().equals(value)).findFirst().isPresent();
    }
}
