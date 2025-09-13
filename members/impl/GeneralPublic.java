package members.impl;

import members.Member;
import members.MemberType;

public class GeneralPublic extends Member {
    public GeneralPublic(String name, String email, String phoneNumber, String address) {
        super(name, email, phoneNumber, MemberType.GENERAL_PUBLIC, address);
    }
    @Override
    public int getBorrowLimit() {
        return 3;
    }
    @Override
    public double getBorrowFinePerDay() {
        return 2.0;
    }
    @Override
    public int getBorrowExtentionDays() {
        return 1;
    }
    public double getMembershipFee() {
        return 500;
    }
}
