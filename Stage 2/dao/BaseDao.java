package carsharing.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDao {

    protected Connection connection;

    public BaseDao (Connection connection) {
        this.connection = connection;
        createTable();
    }

    private void createTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(getCreateTableSQL());
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract String getCreateTableSQL();
}