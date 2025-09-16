package notificationSystem;

import books.BookCopy;
import members.Member;

public interface NotificationSystem {
    public void update(BookCopy copy,Member member);
}
