package observers;

import java.util.HashMap;
import books.BookCopy;
import members.Member;

public class BookReserveObserver{
    private static HashMap<String,HashMap<String,Member>> observerMap=new HashMap<>();
    private static BookReserveObserver INSTANCE;
    private BookReserveObserver(){
    }
    public static BookReserveObserver getInstance(){
        if(INSTANCE==null){
            INSTANCE=new BookReserveObserver();
        }
        return INSTANCE;
    }
    public void addObserver(BookCopy bookCopy, Member member)
    {
        if(!observerMap.containsKey(bookCopy.getBookCopyId())){
            observerMap.put(bookCopy.getBookCopyId(), new HashMap<>());
        }
        if(!observerMap.get(bookCopy.getBookCopyId()).containsKey(member.getMemberID())){
            observerMap.get(bookCopy.getBookCopyId()).put(member.getMemberID(),member);
        }
    }
    public void removeObserver(BookCopy bookCopy, Member member)
    {
        if(!observerMap.containsKey(bookCopy.getBookCopyId())){
            return;
        }
        if(!observerMap.get(bookCopy.getBookCopyId()).containsKey(member.getMemberID())){
            observerMap.get(bookCopy.getBookCopyId()).remove(member.getMemberID());
        }
    }
    public boolean hasObserver(BookCopy bookCopy) {
        return observerMap.containsKey(bookCopy.getBookCopyId());
    }
    public boolean isAObserver(BookCopy bookCopy,Member member) {
        return hasObserver(bookCopy) && observerMap.get(bookCopy.getBookCopyId()).containsKey(member.getMemberID());
    }
    public void notifyObserver(BookCopy bookCopy) {
        for(Member member:observerMap.get(bookCopy.getBookCopyId()).values()){
            member.notificationSystem().update(bookCopy,member);
        }
    }
}
