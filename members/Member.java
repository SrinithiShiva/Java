package members;

import java.util.Date;

public abstract class Member
{
    private String memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private MemberType memberType;
    private Date membershipDate;

    public Member(String name, String email, String phoneNumber,MemberType memberType, String address)
    {
        this.memberId = generateMemberId(memberType);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.membershipDate = new Date();
    }
    private static String generateMemberId(MemberType memberType){
        int memberId=(int) Math.random()*100000;
        return memberType.name().substring(2).concat(String.valueOf(memberId));
    }
    // Getters and Setters
    public String getMemberId()
    {
        return memberId;
    }
    public String getName()
    {
        return name;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getEmail(){
        return email;
    }
    public String getAddress(){
        return address;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public abstract int getBorrowLimit();
    public abstract double getBorrowFinePerDay();
    public abstract int getBorrowExtentionDays();
    public abstract double getMembershipFee();
}
