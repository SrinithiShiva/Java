package displayPanel;

import members.Member;

public class MemberDetailsDisplayPanel implements Display<Member> {
    @Override
    public void display(Member memberDetails)
    {
        System.out.println("====== Member Details of ".concat(memberDetails.name()).concat(" ======"));
        System.out.println("MemberId:: ".concat(memberDetails.getMemberID()));
        System.out.println("Name:: ".concat(memberDetails.name()));
        System.out.println("PhoneNumber:: ".concat(memberDetails.phoneNumber()));
        System.out.println("Email:: ".concat(memberDetails.email()));
        System.out.println("Resident Address:: ".concat(memberDetails.residentAddress()));
        System.out.println("Category:: ".concat(memberDetails.getMemberType().name()));
        System.out.println();
    }
}
