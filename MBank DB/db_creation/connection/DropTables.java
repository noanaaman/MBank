package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DropTables {
    public static void main(String[] args) {

        String db_address = "jdbc:derby://localhost:1527/MBank;";

        try {
            Connection connection = DriverManager.getConnection(db_address);
            try {


                System.out.println("Connection established successfully");

                Statement statement = connection.createStatement();
                statement.executeUpdate("DROP TABLE Clients");
                System.out.println("Clients was dropped");

                statement.executeUpdate("DROP TABLE Accounts");
                System.out.println("Accounts was dropped");

                statement.executeUpdate("DROP TABLE Deposits");
                System.out.println("Accounts was dropped");

                statement.executeUpdate("DROP TABLE Activity");
                System.out.println("Activities was dropped");

                statement.executeUpdate("DROP TABLE Properties");
                System.out.println("Properties was dropped");

                connection.close();
            } catch (SQLException e) {
                connection.close();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}