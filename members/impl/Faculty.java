package members.impl;

import members.Member;

public class Faculty extends Member
{
    public Faculty(String name, String email, String phoneNumber, String address)
    {
        super(name, email, phoneNumber, members.MemberType.FACULTY,address);
    }
    @Override
    public int getBorrowLimit()
    {
        return 5;
    }
    @Override
    public double getBorrowFinePerDay()
    {
        return 1;
    }
    @Override
    public int getBorrowExtentionDays()
    {
        return 1;
    }
    @Override
    public double getMembershipFee()
    {
        return 300;
    }
}
