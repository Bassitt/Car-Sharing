package carsharing;


import carsharing.database.CarDaoDatabaseImpl;
import carsharing.database.CompanyDaoDatabaseImpl;
import carsharing.database.Database;
import carsharing.view.Menu;

public class Main {

    public static void main(String[] args) {

        Database database = new Database(getDbName(args));
        CompanyDaoDatabaseImpl companyDaoH2 = new CompanyDaoDatabaseImpl(database.getConnection());
        CarDaoDatabaseImpl carDaoH2 = new CarDaoDatabaseImpl(database.getConnection());


        for(;;) {
            Menu.mainMenu();
            switch (Menu.userChoice()) {
                case 1 -> Menu.managementCompany(companyDaoH2, carDaoH2);
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