package carsharing.database;

import carsharing.dao.BaseDao;
import carsharing.dao.CompanyDao;
import carsharing.entity.Company;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

    public class DatabaseImpl extends BaseDao implements CompanyDao {

        private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS COMPANY " +
                "(ID INT AUTO_INCREMENT PRIMARY KEY, " +
                " NAME VARCHAR(255) NOT NULL UNIQUE)";
        private static final String GET_ALL_COMPANIES = "SELECT * FROM COMPANY";
        private static final String CREATE_COMPANY = "INSERT INTO COMPANY (NAME) VALUES(?)";

        public DatabaseImpl(Connection connection) {
            super(connection);
        }

        @Override
        protected String getCreateTableSQL() {
            return CREATE_TABLE_SQL;
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
            try(PreparedStatement stmt = connection.prepareStatement(CREATE_COMPANY)) {
                stmt.setString(1, company);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
