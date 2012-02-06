package dbManagement;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import logManagement.Log4k;

/**
 *
 * @author loris.dallago
 * it has package visibility
 */
class dbConnector{
    private final String host = "localhost:3306";
    private final String dbName = "mcr_db";
    private final String user = "root";
    private final String psw = "ricordati";
    private Connection conn = null;
    
    /* used to synchronize the code (package visibility) */
    static final Object mutex = new Object();

    dbConnector() {
        try {
            String dbString = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            dbString = "jdbc:mysql://" + host + "/" + dbName;
            conn = (Connection) DriverManager.getConnection(dbString, user, psw);
        } catch (SQLException ex) {
            Log4k.error(dbConnector.class.getName(), ex.getMessage());
        } catch (InstantiationException ex) {
            Log4k.error(dbConnector.class.getName(), ex.getMessage());
        } catch (IllegalAccessException ex) {
            Log4k.error(dbConnector.class.getName(), ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Log4k.error(dbConnector.class.getName(), ex.getMessage());
        }
    }

    void closeConnection(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Log4k.error(dbConnector.class.getName(), ex.getMessage());
        }
    }

    void executeStatement(String command){
        try {
            Statement statement = (Statement) conn.createStatement();
            synchronized(mutex){
                /* Even if execute is a synchronized method we want
                 * this mutex variable to sinchronize execute with other
                 * atomic code blocks (outside dbConnector).
                 * 
                 */
                statement.execute(command);
            }
            statement.close();
        } catch (SQLException ex) {
            Log4k.error(dbConnector.class.getName(),
                    ex.getMessage() + "\n\tcommand was " + command);
        }
    }

    /* This function is dangerous, it works upon the DB without locking
     * the mutex variable.
     * It must be used within a synchronized context, wherein locking a variable
     * would cause a deadlock.
     */
    void executeStatementNoLock(String command){
        try {
            Statement statement = (Statement) conn.createStatement();
            statement.execute(command);
            statement.close();
        } catch (SQLException ex) {
            Log4k.error(dbConnector.class.getName(),
                    ex.getMessage() + "\n\tcommand was " + command);
        }
    }
    
    ResultSet executeQuery(String query){
        ResultSet res = null;
        try {
            Statement statement = (Statement) conn.createStatement();
            res = (ResultSet) statement.executeQuery(query);
            /* we must not close the statement here, otherwise res drops */
        } catch (SQLException ex) {
            Log4k.error(dbConnector.class.getName(),
                    ex.getMessage() + "\n\tquery was " + query);
        }
        return res;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeConnection();
    }
}
