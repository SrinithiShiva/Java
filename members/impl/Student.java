package members.impl;

import members.Member;
import members.MemberType;

public class Student extends Member {

    public Student(String name, String email, String phoneNumber, String address)
    {
        super(name, email, phoneNumber, MemberType.STUDENT,address);
    }
    @Override
    public int getBorrowLimit() {
        return 7;
    }
    @Override
    public double getBorrowFinePerDay() {
        return 1.5;
    }
    @Override
    public int getBorrowExtentionDays() {
        return 3;
    }
    @Override
    public double getMembershipFee() {
        return 200;
    }
}
