package builders;

import members.Member;
import members.MemberType;
import notificationSystem.NotificationSystem;

public abstract class MemberBuilder {
    private String name;
    private String email;
    private String phoneNumber;
    private String residentAddress;
    private Integer limitOfBooksToBeBorrowed;
    private Double loanFeesPerDay;
    private MemberType memberType;
    private NotificationSystem notificationSystem;
    MemberBuilder(Integer limitOfBooksToBeBorrowed,Double loanFeesPerDay,MemberType memberType){
        this.limitOfBooksToBeBorrowed=limitOfBooksToBeBorrowed;
        this.loanFeesPerDay=loanFeesPerDay;
        this.memberType=memberType;
    }
    public MemberBuilder setName(String name){
        this.name=name;
        return this;
    }

    public MemberBuilder setEmail(final String email) {
        this.email = email;
        return this;
    }

    public MemberBuilder setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public MemberBuilder setResidentAddress(final String residentAddress) {
        this.residentAddress = residentAddress;
        return this;
    }
    public MemberBuilder setNotificationSystem(final NotificationSystem notificationSystem) {
        this.notificationSystem = notificationSystem;
        return this;
    }
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public String getResidentAddress(){
        return this.residentAddress;
    }
    public Integer getLimitOfBooksToBeBorrowed(){
        return this.limitOfBooksToBeBorrowed;
    }
    public Double getLoanFeesPerDay(){
        return this.loanFeesPerDay;
    }
    public MemberType getMemberType(){
        return this.memberType;
    }
    public NotificationSystem getNotificationSystem() {
        return this.notificationSystem;
    }
    public Member build(){
        return Member.createInstance(this);
    }

}
