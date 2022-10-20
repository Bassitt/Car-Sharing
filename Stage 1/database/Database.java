package carsharing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Database db = null;
    static final String JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL = "jdbc:h2:file:../task/src/carsharing/db/";
    private final Connection connection;

    public static Database createDB(String dbName) {
        if (db == null) {
            db = new Database(dbName);
        }
        return db;
    }

    private Database(String dbName) {
        DB_URL = DB_URL + dbName;
        connection = getConnection();
    }

    private Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DB_URL);
            con.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Failed to get connection with db, check the url" + e.getMessage());
        }
        return con;
    }


    public void closeDB() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCOMPANYTable() {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE COMPANY ("
                    + "     ID INTEGER PRIMARY KEY NOT NULL,"
                    + "     NAME VARCHAR(24) NOT NULL "
                    + ");";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

}