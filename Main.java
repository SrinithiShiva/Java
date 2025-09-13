import java.util.Scanner;

import members.MemberType;
import service.BookOperationsMenu;
import service.MemberOperationsMenu;

public class Main
{
    public static void main(String[] args) {
        System.out.println(MemberType.GENERAL_PUBLIC.name());
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Library Management System ===");

        main:
            while(true)
            {
                try {
                    System.out.println("=== Main Menu ===");
                    System.out.println("1. Book Operations");
                    System.out.println("2. Member Operations");
                    System.out.println("3. Borrow Operations");
                    System.out.println("4. Advanced Features");
                    System.out.println("5. Report & Analysis");
                    System.out.println("6. System Operations");
                    System.out.println("7. Exit System");

                    System.out.print("\nEnter a Menu Option: ");
                    int menu = Integer.parseInt(scanner.nextLine());
                    switch (menu) {
                        case 1:
                            BookOperationsMenu.handleBookOperations(scanner);
                            break;
                        case 2:
                            MemberOperationsMenu.handleMemberOperations(scanner);
                            break;
                        case 7:
                            break main;
                        default:
                            break;
                    }
                }
                catch (NumberFormatException exception){
                    System.out.println("Enter a valid Main Menu Option...");
                }
            }
    }
}