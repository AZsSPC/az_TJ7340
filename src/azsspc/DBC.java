package azsspc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBC {
    //  Database credentials
    static final String DB_URL = "jdbc:postgresql://localhost:5432/lobby";
    static final String USER = "az";
    static final String PASS = "pass";

    public Statement connect() {
        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return null;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }

        if (connection != null) System.out.println("You successfully connected to database now");
        else System.out.println("Failed to make connection to database");
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
