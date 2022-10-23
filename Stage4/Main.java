package carsharing;


import carsharing.database.CarDaoDatabaseImpl;
import carsharing.database.CompanyDaoDatabaseImpl;
import carsharing.database.CustomerDaoDatabaseImpl;
import carsharing.database.Database;
import carsharing.view.Menu;
import carsharing.view.PrintMenu;

public class Main {
    
    public static void main(String[] args) {

        Database database = new Database(getDbName(args));
        CompanyDaoDatabaseImpl companyDaoH2 = new CompanyDaoDatabaseImpl(database.getConnection());
        CarDaoDatabaseImpl carDaoH2 = new CarDaoDatabaseImpl(database.getConnection());
        CustomerDaoDatabaseImpl customerDaoH2 = new CustomerDaoDatabaseImpl(database.getConnection());

        for(;;) {
            PrintMenu.mainMenu();
            switch (Menu.userChoice()) {
                case 1 -> Menu.managementCompany(companyDaoH2, carDaoH2);
                case 2 -> Menu.managementCustomerMenu(customerDaoH2, companyDaoH2, carDaoH2);
                case 3 -> Menu.managementCreateCustomer(customerDaoH2);
                case 0 -> {
                    database.closeConnection();
                    return;
                }
            }
        }
    }


    private static String getDbName(String[] args) {
        if (args.length >= 2 && args[0].equals("-databaseFileName")) {
            return args[1];
        } else {
            return "carsharing";
        }
    }
}