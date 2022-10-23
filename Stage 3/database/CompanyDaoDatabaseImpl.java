package carsharing.database;

import carsharing.dao.BaseDao;
import carsharing.dao.CompanyDao;
import carsharing.entity.Company;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CompanyDaoDatabaseImpl extends BaseDao implements CompanyDao {
    private static final String DROP_TABLE_SQL = "ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS COMPANY " +
            "(ID INT AUTO_INCREMENT PRIMARY KEY, " +
            " NAME VARCHAR(255) NOT NULL UNIQUE)";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM COMPANY";
    private static final String CREATE_COMPANY = "INSERT INTO COMPANY (NAME) VALUES(?)";
    private static final String GET_COMPANY_NAME = "SELECT NAME FROM COMPANY WHERE ID = ?";

    public CompanyDaoDatabaseImpl(Connection connection) {
        super(connection);
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    @Override
    protected String dropTableSQL() {
        return DROP_TABLE_SQL;
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new LinkedList<>();
        try(PreparedStatement stmt = connection.prepareStatement(GET_ALL_COMPANIES)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                companies.add(new Company(resultSet.getInt("ID"), resultSet.getString("NAME")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public void createCompany(String company) {

        if (getAllCompanies().size() == 0) {
            try (PreparedStatement statement = connection.prepareStatement("ALTER TABLE COMPANY ALTER " +
                    "COLUMN ID RESTART WITH ?")) {
                statement.setString(1, "1");
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try(PreparedStatement stmt = connection.prepareStatement(CREATE_COMPANY)) {
            stmt.setString(1, company);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



}