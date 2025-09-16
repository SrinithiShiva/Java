package members;

import borrowAndLoan.LoanHistory;
import builders.MemberBuilder;
import borrowAndLoan.BorrowHistory;
import displayPanel.MemberDetailsDisplayPanel;
import notificationSystem.NotificationSystem;

public class Member {
    private String memberID;
    private String name;
    private String email;
    private String phoneNumber;
    private String residentAddress;
    private MemberType memberType;
    private Integer limitOfBooksToBeBorrowed;
    private Double loanFees;
    private BorrowHistory borrowHistory;
    private LoanHistory loanHistory;
    private NotificationSystem notificationSystem;
    private MemberDetailsDisplayPanel memberDetailsDisplayPanel;

    private Member(MemberBuilder memberBuilder){
        this.memberID=memberBuilder.getName().substring(3).concat("_").concat(memberBuilder.getEmail().substring(3));
        this.name=memberBuilder.getName();
        this.email=memberBuilder.getEmail();
        this.phoneNumber=memberBuilder.getPhoneNumber();
        this.residentAddress=memberBuilder.getResidentAddress();
        this.memberType=memberBuilder.getMemberType();
        this.limitOfBooksToBeBorrowed=memberBuilder.getLimitOfBooksToBeBorrowed();
        this.loanFees=memberBuilder.getLoanFeesPerDay();
        this.borrowHistory=new BorrowHistory();
        this.loanHistory=new LoanHistory();
        this.notificationSystem=memberBuilder.getNotificationSystem();
        this.memberDetailsDisplayPanel=new MemberDetailsDisplayPanel();
    }
    public String getMemberID(){
        return this.memberID;
    }
    public String name() {
        return this.name;
    }

    public String email() {
        return this.email;
    }

    public String phoneNumber() {
        return this.phoneNumber;
    }

    public String residentAddress() {
        return this.residentAddress;
    }

    public Integer limitOfBooksToBeBorrowed() {
        return this.limitOfBooksToBeBorrowed;
    }

    public Double loanFeesPerDay() {
        return this.loanFees;
    }

    public MemberType getMemberType(){ return this.memberType; }

    public BorrowHistory borrowHistory() {
        return this.borrowHistory;
    }
    public LoanHistory getLoanHistory(){
        return this.loanHistory;
    }
    public NotificationSystem notificationSystem() {
        return this.notificationSystem;
    }
    public MemberDetailsDisplayPanel memberDetailsDisplayPanel(){
        return this.memberDetailsDisplayPanel;
    }
    public static Member createInstance(MemberBuilder memberBuilder){
        return new Member(memberBuilder);
    }
}
