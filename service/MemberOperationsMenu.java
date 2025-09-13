package service;

import java.util.Scanner;
import members.Member;
import members.MemberService;
import members.MemberType;
import utils.ManagementUtil;

public class MemberOperationsMenu {
    public static void handleMemberOperations(Scanner scanner) {
        member:
            while(true) {
                try {
                    System.out.println("=== Member Operations ===");
                    System.out.println("1. Register Member");
                    System.out.println("2. Update Member");
                    System.out.println("3. Search Member");
                    System.out.println("4. Delete Member");
                    System.out.println("5. View Member Fine Details");
                    System.out.println("6. Exit Member System");
                    System.out.println("\nEnter a Member Operation Menu: ");
                    int memberMenu = Integer.parseInt(scanner.nextLine());

                    switch (memberMenu)
                    {
                        case 1:
                            addMember(scanner);
                            break;
                        case 2:
                            updateMember(scanner);
                            break;
                        case 3:
                            searchMember(scanner);
                            break;
                        case 4:
                            // deleteMember(scanner);
                            break;
                        case 5:
                            // viewMemberFineDetails(scanner);
                            break;
                        case 6:
                            break member;
                        default:
                            System.out.println("Invalid Member Option...");
                            break;
                    }
                }
                catch (NumberFormatException exception)
                {
                    System.out.println("Enter a valid Member Option...");
                }
            }
    }

    private static void addMember(Scanner scanner)
    {
        System.out.println("Enter Member Name: ");
        String name=scanner.nextLine();
        System.out.println("Enter Member Email: ");
        String email=scanner.nextLine();
        System.out.println("Enter Member Phone Number: ");
        String phoneNumber=scanner.nextLine();
        System.out.println("Enter Member Address: ");
        String address=scanner.nextLine();
        System.out.println("Select Member Type: ");
        for(MemberType memberType:MemberType.values())
        {
            System.out.println(memberType.ordinal()+". "+memberType.getName());
        }
        int memberTypeOption=Integer.parseInt(scanner.nextLine());

        if(!ManagementUtil.isNullOrEmpty.test(name)){
            System.out.println("Invalid or Empty Member Name...");
            return;
        }
        if (!ManagementUtil.isMailChecked.test(email)) {
            System.out.println("Invalid Member Email...");
            return;
        }
        if (!ManagementUtil.isValidPhoneNumber.test(phoneNumber)) {
            System.out.println("Invalid Member Phone Number...");
            return;
        }
        if(!ManagementUtil.isNullOrEmpty.test(address)){
            System.out.println("Invalid or Empty Member Address...");
            return;
        }
        if(memberTypeOption<0 || memberTypeOption>=MemberType.values().length) {
            System.out.println("Invalid Member Type Option...");
            return;
        }
        MemberType memberType = MemberType.values()[memberTypeOption];
        MemberService.addMember(name,email,phoneNumber,memberType,address);
    }
    private static void updateMember(Scanner scanner)
    {
        System.out.println("Enter Member ID to Update: ");
        String memberId=scanner.nextLine();
        if(ManagementUtil.isNullOrEmpty.test(memberId) && !MemberService.isValidMemberId(memberId)){
            System.out.println("Invalid or Empty Member ID...");
            return;
        }
        else
        {
            System.out.println("Choose field to update: ");
            System.out.println("1. Name");
            System.out.println("2. Email");
            System.out.println("3. Phone Number");
            System.out.println("4. Address");
            int updateOption=Integer.parseInt(scanner.nextLine());
            if(updateOption<1 || updateOption>4) {
                System.out.println("Invalid Update Option...");
                return;
            }
            switch (updateOption){
                case 1:
                    System.out.println("Enter new Name: ");
                    String name=scanner.nextLine();
                    if(ManagementUtil.isNullOrEmpty.test(name)){
                        System.out.println("Invalid or Empty Member Name...");
                        return;
                    }
                    MemberService.updateMemberName(memberId,name);
                    break;
                case 2:
                    System.out.println("Enter new Email: ");
                    String email=scanner.nextLine();
                    if (!ManagementUtil.isMailChecked.test(email)) {
                        System.out.println("Invalid Member Email...");
                        return;
                    }
                    MemberService.updateMemberEmail(memberId,email);
                    break;
                case 3:
                    System.out.println("Enter new Phone Number: ");
                    String phoneNumber=scanner.nextLine();
                    if (!ManagementUtil.isValidPhoneNumber.test(phoneNumber)) {
                        System.out.println("Invalid Member Phone Number...");
                        return;
                    }
                    MemberService.updateMemberPhoneNumber(memberId,phoneNumber);
                    break;
                case 4:
                    System.out.println("Enter new Address: ");
                    String address=scanner.nextLine();
                    if(ManagementUtil.isNullOrEmpty.test(address)){
                        System.out.println("Invalid or Empty Member Address...");
                        return;
                    }
                    MemberService.updateMemberAddress(memberId,address);
                    break;
            }
        }
    }
    private static void searchMember(Scanner scanner)
    {
        System.out.println("Enter Member ID to Search: ");
        String memberId=scanner.nextLine();
        if(ManagementUtil.isNullOrEmpty.test(memberId) && !MemberService.isValidMemberId(memberId)){
            System.out.println("Invalid or Empty Member ID...");
            return;
        }
        Member member = MemberService.getMemberObject(memberId);
        System.out.println("=============== Member Details ===============");
        System.out.println("Member ID: "+member.getMemberId());
        System.out.println("Name: "+member.getName());
        System.out.println("Email: "+member.getEmail());
        System.out.println("Phone Number: "+member.getPhoneNumber());
        System.out.println("Address: "+member.getAddress());
        System.out.println("=============================================");
    }

}
