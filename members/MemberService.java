package members;

import java.util.HashMap;
import members.impl.Faculty;
import members.impl.GeneralPublic;
import members.impl.Student;

public class MemberService
{
    private static final HashMap<String,Member> memberMap=new HashMap<>();
    public static void addMember(String name,String email,String phoneNumber,MemberType memberType,String address){
        Member member=null;
        String message="";
        switch (memberType){
            case STUDENT:
                member=new Student(name,email,phoneNumber,address);
                message="Student added with member ID: ";
                break;
            case FACULTY:
                member=new Faculty(name,email,phoneNumber,address);
                message= "Faculty added with member ID: ";
                break;
            case GENERAL_PUBLIC:
                member=new GeneralPublic(name,email,phoneNumber,address);
                message = "General Public added with member ID: ";
                break;
        }
        if(member!=null){
            memberMap.put(member.getMemberId(),member);
            System.out.println(message.concat(member.getMemberId()));
        }
    }
    public static void updateMemberName(String memberId,String name){
        Member member=memberMap.get(memberId);
        if(member!=null){
            member.setName(name);
            System.out.println("Member name updated successfully.");
        }else{
            System.out.println("Member not found with ID: "+memberId);
        }
    }
    public static void updateMemberEmail(String memberId,String email){
        Member member=memberMap.get(memberId);
        if(member!=null){
            member.setEmail(email);
            System.out.println("Member email updated successfully.");
        }else{
            System.out.println("Member not found with ID: "+memberId);
        }
    }
    public static void updateMemberPhoneNumber(String memberId,String phoneNumber) {
        Member member = memberMap.get(memberId);
        if (member != null) {
            member.setPhoneNumber(phoneNumber);
            System.out.println("Member phone number updated successfully.");
        } else {
            System.out.println("Member not found with ID: " + memberId);
        }
    }
    public static void updateMemberAddress(String memberId,String address){
        Member member=memberMap.get(memberId);
        if(member!=null){
            member.setAddress(address);
            System.out.println("Member address updated successfully.");
        }else{
            System.out.println("Member not found with ID: "+memberId);
        }
    }
    public static boolean isValidMemberId(String memberId){
        return memberMap.containsKey(memberId);
    }
    public static Member getMemberObject(String memberId){
        return memberMap.get(memberId);
    }
}
