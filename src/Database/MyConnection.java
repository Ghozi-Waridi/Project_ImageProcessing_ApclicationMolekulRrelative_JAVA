package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyConnection {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/senyawa";

    public static Connection connnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn =  DriverManager.getConnection(URL, USERNAME, PASSWORD);
        conn.setAutoCommit(false);
        return conn;
    }
}
