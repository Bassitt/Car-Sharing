package carsharing;


import carsharing.view.Menu;
import carsharing.database.DatabaseImpl;
import carsharing.database.Database;

public class Main {

    public static void main(String[] args) {

        Database database = new Database(getDbName(args));
        DatabaseImpl companyDaoH2 = new DatabaseImpl(database.getConnection());

        for(;;) {
            Menu.mainMenu();
            switch (Menu.userChoice()) {
                case 1:
                    Menu.managementMenu(companyDaoH2);
                    break;
                case 0:
                    return;
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