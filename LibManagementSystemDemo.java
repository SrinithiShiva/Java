import books.BookCategory;
import books.Books;
import builders.GeneralPublicBuilder;
import builders.StaffBuilder;
import builders.StudentBuilder;
import notificationSystem.MailNotification;
import notificationSystem.SMSNotification;
import notificationSystem.WhatsappNotification;
import search.SearchByISBN;
import search.SearchMemberByMemberId;

public class LibManagementSystemDemo {
    public static void main(String[] args) {
        LibManagementSystem libManagementSystem=LibManagementSystem.getInstance();
        System.out.println("====\tLibrary Management System\t===");

        //Adding members to the system
        libManagementSystem.addMember(new StudentBuilder()
                .setName("naveen")
                .setEmail("naveen@gmail.com")
                .setPhoneNumber("+91 8779907654")
                .setResidentAddress("New 10,Raj Enclave,Delhi-900004")
                .setNotificationSystem(new SMSNotification())
                .build());
        libManagementSystem.addMember(new StaffBuilder()
                .setName("Rajesh")
                .setEmail("Rajesh@gmail.com")
                .setPhoneNumber("+91 8759907654")
                .setResidentAddress("New 10,Reem Enclave,Delhi-900004")
                .setNotificationSystem(new MailNotification())
                .build());
        libManagementSystem.addMember(new GeneralPublicBuilder()
                .setName("Priya")
                .setEmail("priya@gmail.com")
                .setPhoneNumber("+91 9882209876")
                .setResidentAddress("New 10,Ram Enclave,Delhi-900004")
                .setNotificationSystem(new WhatsappNotification())
                .build());
        libManagementSystem.addMember(new StudentBuilder()
                .setName("Mohan")
                .setEmail("mohan@gmail.com")
                .setPhoneNumber("+91 7662298734")
                .setResidentAddress("New 10,Raj Enclave,Delhi-900004")
                .setNotificationSystem(new SMSNotification())
                .build());

        libManagementSystem.displayMemberDetailsInSystem();

        //Adding Books to System
        libManagementSystem.addBookWithCopies(new Books("978-0-13-468599-1","Effective Java","Joshua Bloch","2001", BookCategory.TECH,3));

        libManagementSystem.addBookWithCopies(new Books("978-0-12-468599-9","The Second World War","Winston S. Churchill","1953", BookCategory.HISTORY,2));

        libManagementSystem.addBookWithCopies(new Books("958-0-13-468699-1","The Little Dictionary of Fashion","Christian Dior","1954", BookCategory.FASHION,1));

        libManagementSystem.addBookWithCopies(new Books("978-2-33-468599-1","The War of the Worlds","H. G. Wells","1898", BookCategory.SCI_FIC,2));
        libManagementSystem.displayBooksInSystem();

        //Scenario 1:Borrow a book which is borrowProduct(member:"ya_ya@gmail.com")
        libManagementSystem.borrowBook("ya_ya@gmail.com","958-0-13-468699-1","b-0");

        //Scenario 2:Display current borrows of member(member:"ya_ya@gmail.com")
        libManagementSystem.displayActiveBorrowHistoryOfMember("ya_ya@gmail.com");

        //Scenario 3:Reserving the same book("ISBN:"958-0-13-468699-1") borrowed by member(member:"ya_ya@gmail.com")
        libManagementSystem.reserveBook("esh_esh@gmail.com","958-0-13-468699-1","b-0");

        //Scenario 4:Return a borrowed book(member:"ya_ya@gmail.com") and notification to reserved member will be sent
        libManagementSystem.returnBook("ya_ya@gmail.com","958-0-13-468699-1","b-0");

        //Scenario 5:Displaying the previous borrows of member(member:"ya_ya@gmail.com")
        libManagementSystem.displayPreviousBorrowHistoryOfMember("ya_ya@gmail.com");

        //Scenario 6:Searching book by ISBN
        libManagementSystem.searchISBNValue("958-0-13-468699-1",new SearchByISBN());

        //Scenario 7:Searching member by memberID
        libManagementSystem.searchMemberByID("ya_ya@gmail.com",new SearchMemberByMemberId());
    }
}
