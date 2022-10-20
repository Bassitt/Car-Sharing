package carsharing;

import carsharing.database.Database;

public class Main {

    public static void main(String[] args) {
        String dbName = "default";

        for (int i = 0; i < args.length; i++) {
            if ("-databaseFileName".equals(args[i])) {
                dbName = args[i + 1];
                break;
            }
        }

        Database database = Database.createDB(dbName);
        database.createCOMPANYTable();
        database.closeDB();
    }
}