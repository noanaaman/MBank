package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import bank_exceptions.MBankException;
import actions_classes.AdminAction;
import actions_classes.ClientAction;

public class InitiateDB {
    public static void main(String[] args) throws MBankException {

        String db_address = "jdbc:derby://localhost:1527/MBank;create=true";

        try {
            Connection connection = DriverManager.getConnection(db_address);
            try {
                System.out.println("Connection established successfully");

                Statement statement = connection.createStatement();
                statement.executeUpdate("create table Clients(client_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1), client_name VARCHAR(80), password VARCHAR(20), type VARCHAR(9), address VARCHAR(80), email VARCHAR(50), phone VARCHAR(15), comment VARCHAR(250), PRIMARY KEY (client_id))");
                System.out.println("Clients was created");

                statement.executeUpdate("create table Accounts(account_id  BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1), client_id  BIGINT NOT NULL, balance DOUBLE, credit_limit VARCHAR(50), comment VARCHAR(50), PRIMARY KEY (account_id))");
                System.out.println("Accounts was created");

                statement.executeUpdate("create table Deposits(deposit_id  BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1), client_id  BIGINT NOT NULL, balance DOUBLE, type VARCHAR(6), estimated_balance DOUBLE, opening_date DATE, closing_date DATE, PRIMARY KEY (deposit_id))");
                System.out.println("Deposits was created");

                statement.executeUpdate("create table Activity(id  BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1), client_id  BIGINT NOT NULL, amount DOUBLE, activity_date DATE, commision DOUBLE, description VARCHAR(50), PRIMARY KEY (id))");
                System.out.println("Activities was created");

                statement.executeUpdate("create table Properties(prop_key VARCHAR(50), prop_value VARCHAR(20), description VARCHAR(255), PRIMARY KEY (prop_key))");
                System.out.println("Properties was created");
                
                //populate system properties table
                statement.executeUpdate("insert into Properties values ('regular_deposit_rate','10000','New regular client deposit rate ($).')");
                statement.executeUpdate("insert into Properties values ('gold_deposit_rate','100000', 'New gold client deposit rate (?).' )");
                statement.executeUpdate("insert into Properties values ('platinum_deposit_rate','1000000','New platinum client deposit rate (?).')");
                statement.executeUpdate("insert into Properties values ('regular_deposit_commission','1.5','Regular client commission rate for deposit opening (%).')");
                statement.executeUpdate("insert into Properties values ('gold_deposit_commission','1.0','Gold client commission rate for deposit opening (%).')");
                statement.executeUpdate("insert into Properties values ('platinum_deposit_commission','0.5','Platinum client commission rate for deposit opening (%).')");
                statement.executeUpdate("insert into Properties values ('regular_credit_limit','100000','Regular account overdraft limit ($).')");
                statement.executeUpdate("insert into Properties values ('gold_credit_limit','1000000','Gold account overdraft limit ($).')");
                statement.executeUpdate("insert into Properties values ('platinum_credit_limit','unlimited','Platinum account overdraft limit ($).')");
                statement.executeUpdate("insert into Properties values ('commission_rate','0.5','Commission rate for all withdraws & deposits ($).')");
                statement.executeUpdate("insert into Properties values ('regular_daily_interest','0.014','Regular daily precentage added to deposit balance (%).')");
                statement.executeUpdate("insert into Properties values ('gold_daily_interest','0.019','Gold daily precentage added to deposit balance (%).')");
                statement.executeUpdate("insert into Properties values ('platinum_daily_interest','0.021','Platinum daily precentage added to deposit balance (%).')");
                statement.executeUpdate("insert into Properties values ('pre_open_fee','1','Commission rate for pre-opening a deposit (%).')");
                statement.executeUpdate("insert into Properties values ('number_of_connections','20','Number of allowed connections to the DB.')");
                statement.executeUpdate("insert into Properties values ('admin_username','system','Username for all system administrators.')");
                statement.executeUpdate("insert into Properties values ('admin_password','admin','Password for all system administrators.')");
                
                System.out.println("properties table has been populated");
                
                AdminAction adminAcion = new AdminAction(connection, 0);
                adminAcion.addNewClient("Liz Lemon", "password", "30 Rock, New York", "liz@tgs.com", "555-1111", "", "", 12500);
                adminAcion.addNewClient("Jenna Maroney", "password", "30 Rock, New York", "jenna@tgs.com", "555-1112", "", "", 140000);
                adminAcion.addNewClient("Tracy Jordan", "password", "30 Rock, New York", "tracy@tgs.com", "555-1113", "", "", 132000);
                adminAcion.addNewClient("Leslie Knope", "password", "Pawnee, Indiana", "leslie@Pawnee.com", "555-1114", "", "", 1008000);
                ClientAction lizAction = new ClientAction(connection, 1000);
                ClientAction leslieAction = new ClientAction(connection, 1003);
                lizAction.createNewLongDeposit(2500);
                lizAction.createNewShortDeposit(348);
                leslieAction.createNewLongDeposit(1240);
                leslieAction.createNewShortDeposit(5600);
                lizAction.depositToAccount(123);
                leslieAction.depositToAccount(1200);
                lizAction.withdrawFromAccount(560);
                leslieAction.withdrawFromAccount(900);
                

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