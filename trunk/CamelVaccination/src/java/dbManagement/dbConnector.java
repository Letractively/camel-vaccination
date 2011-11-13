package dbManagement;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author loris.dallago
 * it has package visibility
 */
class dbConnector{
    private final String host = "localhost:3306";
    private final int port = 3306;
    private final String dbName = "medical_db";
    private final String user = "DBuzzer";
    private final String psw = "user";
    private Connection conn = null;

    dbConnector() {
        try {
            System.out.print("jdbc:mysql://" + host + "/" + dbName);
            String dbString = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            dbString = "jdbc:mysql://" + host + "/" + dbName;
            conn = (Connection) DriverManager.getConnection(dbString, user, psw);
        } catch (SQLException ex) {
            Logger.getLogger(dbConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(dbConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(dbConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void closeConnection(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(dbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void executeStatement(String command){
        try {
            Statement statement = (Statement) conn.createStatement();
            statement.execute(command);
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(dbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ResultSet executeQuery(String query){
        ResultSet res = null;
        try {
            Statement statement = (Statement) conn.createStatement();
            res = (ResultSet) statement.executeQuery(query);
            /* we must not close the statement here, otherwise res drops */
        } catch (SQLException ex) {
            Logger.getLogger(dbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    protected void finalize() throws Throwable {
        closeConnection();
    }
}
