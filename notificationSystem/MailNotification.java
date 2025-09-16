package notificationSystem;

import books.BookCopy;
import members.Member;

public class MailNotification implements NotificationSystem{
    @Override
    public void update(final BookCopy bookCopy, final Member member) {
        System.out.println("Mail Notification:".concat(member.name()).concat(" your reserved book ").concat(bookCopy.getBooks().title()).concat(" is now available and being held on hold for you"));
    }
}
