import java.util.HashMap;
import java.util.stream.Collectors;

import books.BookCopy;
import books.Books;
import borrowAndLoan.BorrowHistory;
import borrowAndLoan.LoanHistory;
import members.Member;
import search.SearchCriteria;
import states.AvailableState;

public class LibManagementSystem {
    private static LibManagementSystem INSTANCE;
    private static final HashMap<String,Member> memberMap=new HashMap<>();
    private static final HashMap<String,Books> booksMap=new HashMap<>();
    private LibManagementSystem(){
    }
    public static LibManagementSystem getInstance(){
        if(INSTANCE==null){
            INSTANCE=new LibManagementSystem();
        }
        return INSTANCE;
    }
    public void addMember(Member member)
    {
        memberMap.put(member.getMemberID(),member);
    }
    public void addBookWithCopies(Books books){

        booksMap.put(books.ISBN(),books);
        HashMap<String,BookCopy> bookCopy = books.bookCopies();
        for(int id=bookCopy.size();id<books.availableCopies();id++){
            String bookCopyId="b-".concat(String.valueOf(id));
            BookCopy bookCopyObj=new BookCopy(bookCopyId,books,new AvailableState());
            bookCopy.put(bookCopyId,bookCopyObj);
        }
    }
    public void borrowBook(String memberId,String bookISBN,String bookCopyID){
        if(memberMap.containsKey(memberId) && booksMap.containsKey(bookISBN)){
            Member member=memberMap.get(memberId);
            Books books = booksMap.get(bookISBN);
            if(member.limitOfBooksToBeBorrowed()<member.borrowHistory().getActiveBorrows().size()){
                System.out.println("Member borrowProduct limit is done");
            }
            else{
                books.borrow(bookCopyID,member);
            }
        }
    }
    public void returnBook(String memberId,String bookISBN,String bookCopyID){
        if(memberMap.containsKey(memberId) && booksMap.containsKey(bookISBN)){
            Member member=memberMap.get(memberId);
            Books books = booksMap.get(bookISBN);
            if(member.borrowHistory().isBookInActiveBorrow(bookCopyID)){
                books.returnBook(bookCopyID,member);
            }
            else
            {
                System.out.println("Member has not borrowed book to return");
            }
        }
    }
    public void reserveBook(String memberId,String bookISBN,String bookCopyID){
        if(memberMap.containsKey(memberId) && booksMap.containsKey(bookISBN)){
            Member member=memberMap.get(memberId);
            Books books = booksMap.get(bookISBN);
            books.reserveBook(bookCopyID,member);
        }
    }
    public void displayMemberDetailsInSystem(){
        System.out.println("----\tMembers in the System\t----");
        for(String memberId:memberMap.keySet()){
            displayMemberDetails(memberId);
        }
    }
    public void displayBooksInSystem(){
        System.out.println("----\tBooks in the System\t----");
        for(String bookId:booksMap.keySet()){
            displayBookDetails(bookId);
        }
    }
    public void displayMemberDetails(String memberId){
        Member member=memberMap.get(memberId);
        member.memberDetailsDisplayPanel().display(member);
    }
    public void displayBookDetails(String bookId){
        Books books=booksMap.get(bookId);
        books.bookDetailsDisplayPanel().display(books);
    }
    public void displayActiveBorrowHistoryOfMember(String memberId){
        BorrowHistory borrowHistory=memberMap.get(memberId).borrowHistory();
        borrowHistory.getActiveBorrowsDisplayPanel().display(borrowHistory.getActiveBorrows());
    }
    public void displayPreviousBorrowHistoryOfMember(String memberId){
        BorrowHistory borrowHistory=memberMap.get(memberId).borrowHistory();
        borrowHistory.getPreviousBorrowsDisplayPanel().display(borrowHistory.getPreviousBorrows());
    }
    public void displayDueOfEachMember(String memberId){
        Member member=memberMap.get(memberId);
        LoanHistory loanHistory=member.getLoanHistory();
        loanHistory.loanDetailsDisplay().display(loanHistory);
    }
    public void searchISBNValue(String string, SearchCriteria searchCriteria){
        System.out.println(searchCriteria.search(booksMap.values().stream().collect(Collectors.toList()), string));
    }
    public void searchMemberByID(String string, SearchCriteria searchCriteria){
        System.out.println(searchCriteria.search(memberMap.values().stream().collect(Collectors.toList()), string));
    }
}
