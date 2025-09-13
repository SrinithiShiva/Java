package service;

import java.util.Scanner;

public class BookOperationsMenu
{
    public static void handleBookOperations(Scanner scanner)
    {
        book:
            while(true) {
                try {
                    System.out.println("=== Book Operations ===");
                    System.out.println("1. Add New Book");
                    System.out.println("5. Exit Book System");
                    System.out.print("\n Enter a Book Operation Menu: ");
                    int bookMenu = Integer.parseInt(scanner.nextLine());
                    switch (bookMenu) {
                        case 1:
                            addBooks(scanner);
                            break;

                    }
                }
                catch (NumberFormatException exception)
                {
                    System.out.println("Enter a valid Book Option...");
                }
            }
    }
    private static void addBooks(Scanner scanner){

    }
}
