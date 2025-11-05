/*
    Author: Jye Curtis-Smith
    Version: 1.0
    Date: 3/11/2025
    DB class, handles the database connection, query executions, and result sets.
 */

import java.sql.*;

public class DB
{
    private final String url = "jdbc:mysql://localhost:3306/pcpartsales";
    private final String user = "root";
    private final String password = "";

    Connection conn;

    DB() throws SQLException
    {
        conn = DriverManager.getConnection(url, user, password);
    }

    public ResultSet executeQuery(String query) throws SQLException
    {
        Statement stmt = createNewStatement();
        return stmt.executeQuery(query);
    }

    public PreparedStatement newPreparedStatement(String query) throws SQLException
    {
        return conn.prepareStatement(query);
    }

    public void executeQuery(PreparedStatement stmt) throws SQLException
    {
        stmt.executeUpdate();
    }

    public void CloseConnection() throws SQLException
    {
        conn.close();
    }

    private Statement createNewStatement() throws SQLException
    {
        return conn.createStatement();
    }
}
