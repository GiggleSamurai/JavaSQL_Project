/**
 * @class DBConnection.java
 * @author Louis Wong
 */

package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String databaseName="client_schedule";
    private static final String DB_URL="jdbc:mysql://localhost:3306/"+databaseName;
    private static final String username="sqlUser";
    private static final String password="Passw0rd!";
    static Connection conn;

    /**
     * establish mySQL connection with username & password
     */
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception{
        conn=(Connection) DriverManager.getConnection(DB_URL,username,password);
    }

    /**
     * end mySQL connection
     */
    public static void closeConnection() throws ClassNotFoundException,SQLException, Exception{
        conn.close();
    }

}

