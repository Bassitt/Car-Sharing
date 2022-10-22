package carsharing.view;

import carsharing.database.DatabaseImpl;

import java.util.Scanner;


public class Menu {

    public static final Scanner scanner = new Scanner(System.in);

    public static void mainMenu() {
        System.out.println("1. Log in as a manager");
        System.out.println("0. Exit");
    }

    private static void managerMenu() {
        System.out.println();
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
    }

    public static void managementMenu (DatabaseImpl companyDaoH2) {
        Scanner scanner = new Scanner(System.in);
        for (;;) {
            Menu.managerMenu();
            switch (Menu.userChoice()) {
                case 0:
                    return;
                case 1:
                    if(companyDaoH2.getAllCompanies().stream().count() == 0) {
                        System.out.println("The company list is empty!\n");
                    } else {
                        System.out.println("Company list:");
                        companyDaoH2.getAllCompanies()
                                .stream()
                                .forEach(System.out::println);
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.println("Enter the company name:");
                    companyDaoH2.createCompany(scanner.nextLine());
                    System.out.println("The company was created!\n");
                    break;
            }
        }
    }

    public static int userChoice() {
        return scanner.nextInt();
    }
}