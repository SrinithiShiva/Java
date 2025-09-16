package search;

public interface SearchCriteria<T,V>{
    boolean search(T set,V value);
}
