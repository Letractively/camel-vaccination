package dbManagement;

import com.mysql.jdbc.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import logManagement.Log4k;

/**
 *
 * @author loris.dallago
 */
public class dbManager{
    private dbConnector dbConn;

    public dbManager() {
        dbConn = new dbConnector();
    }

    public void releaseConnection(){
        dbConn.closeConnection();
    }

    public ResultSet getPatientVaccinations(int patientID){
        ResultSet res = null;
        try {
            String command;
            command = "SELECT doctor_id, vaccination_date FROM vaccinations WHERE patient_id = " + patientID;
            ResultSet set1 = dbConn.executeQuery(command);
            command = "SELECT name, surname FROM doctors WHERE doctor_id = " + set1.getString("doctor_id");
            ResultSet set2 = dbConn.executeQuery(command);
            command = "SELECT name, surname, vaccination_date FROM " + set1.getCursorName() + " JOIN " + set2.getCursorName();
            res = dbConn.executeQuery(command);
        } catch (SQLException ex) {
            Log4k.error(dbManager.class.getName(), ex.getMessage());
        }
        return res;
    }

    public ResultSet getDoctorVaccinations(int doctorID) {
        ResultSet res = null;
        try {
            String command;
            command = "SELECT patient_id, vaccination_date FROM vaccinations WHERE doctor_id = " + doctorID;
            ResultSet set1 = dbConn.executeQuery(command);
            command = "SELECT name, surname FROM patients WHERE patient_id = " + set1.getString("patient_id");
            ResultSet set2 = dbConn.executeQuery(command);
            command = "SELECT name, surname, vaccination_date FROM " + set1.getCursorName() + " JOIN " + set2.getCursorName();
            res = dbConn.executeQuery(command);
            return res;
        } catch (SQLException ex) {
            Log4k.error(dbManager.class.getName(), ex.getMessage());
        }
        return res;
    }

    private String getDBtime(){
        String res = null;
        try {
            String command = "SELECT NOW()";
            ResultSet set = dbConn.executeQuery(command);
            res = set.getString(1);
        } catch (SQLException ex) {
            Log4k.error(dbManager.class.getName(), ex.getMessage());
        }
        return res;
    }

    private String getDBdiffTime(String sec) {
        String res = null;
        try {
            String command = "SELECT NOW() - INTERVAL " + sec + " SECOND";
            ResultSet set = dbConn.executeQuery(command);
            res = set.getString(0);
        } catch (SQLException ex) {
            Log4k.error(dbManager.class.getName(), ex.getMessage());
        }
        return res;
    }

    public ResultSet getPreviousVaccinationsPatients(int doctorID, String sec){
        ResultSet res = null;
        try {
            String command;
            command = "SELECT patient_id FROM vaccinations WHERE doctor_id = " + doctorID +
                    " AND vaccination_date <= " + getDBdiffTime(sec);
            ResultSet set1 = dbConn.executeQuery(command);
            command = "SELECT * FROM patients WHERE patient_id = " + set1.getString("patient_id");
            res = dbConn.executeQuery(command);
        } catch (SQLException ex) {
            Log4k.error(dbManager.class.getName(), ex.getMessage());
        }
        return res;
    }

    public ResultSet userMatches(String userName, String pwd, boolean isDoctor){
        ResultSet res;
        String userKind = ((isDoctor) ? "doctor" : "patient");
        String command = "SELECT * FROM " + userKind + "s WHERE "
                + "username = '" + userName + "' AND "
                + "password = '" + pwd + "' AND "
                + ((isDoctor) ? " TRUE" : "registered");
        res = dbConn.executeQuery(command);        
        return res;
    }
}
