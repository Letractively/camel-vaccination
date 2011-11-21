package dbManagement;

import com.mysql.jdbc.ResultSet;
import java.sql.SQLException;
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
    
    public ResultSet getPatient(int patientID){
        ResultSet res = null;
        try {
            String command;
            command = "SELECT * FROM patients WHERE id = "+ patientID;
            res = dbConn.executeQuery(command);
        } catch (Exception ex) {
            Log4k.error(dbManager.class.getName(), ex.getMessage());
        }
        return res;
    }

    public ResultSet getDoctor(int doctorID){
        ResultSet res = null;
        try {
            String command;
            command = "SELECT * FROM doctors WHERE id = "+ doctorID;
            res = dbConn.executeQuery(command);
        } catch (Exception ex) {
            Log4k.error(dbManager.class.getName(), ex.getMessage());
        }
        return res;
    }

    
    public ResultSet getPatientVaccinations(int patientID){
        ResultSet res = null;
        try {
            String command;
            command = "SELECT doctor_id, vaccination_date FROM vaccinations WHERE patient_id = " + patientID;
            res = dbConn.executeQuery(command);
        } catch (Exception ex) {
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

    public String getDBtime(){
        String res = null;
        try {
            String command = "SELECT NOW() AS N";
            ResultSet set = dbConn.executeQuery(command);
            set.next();
            res = set.getString("N");
        } catch (SQLException ex) {
            Log4k.error(dbManager.class.getName(), ex.getMessage());
        }
        return res;
    }

    private String getDBdiffTime(int sec) {
        String res = null;
        try {
            String command = "SELECT NOW() - INTERVAL " + sec + " SECOND AS C";            
            ResultSet set = dbConn.executeQuery(command);
            set.next();
            res = set.getString("C");            
        } catch (SQLException ex) {
            Log4k.error(dbManager.class.getName(), ex.getMessage());
        }
        return res;
    }

    public ResultSet getPreviousVaccinationsPatients(int sec){
        ResultSet res = null;
        try {
            String command;
            /* Select the last vaccination of each vaccinated patient before sec seconds ago */
            String lastVaccinations = "SELECT DISTINCT MAX(vaccination_date) AS 'max', patient_id AS 'pid' FROM vaccinations GROUP BY patient_id";
            String JT1 = "SELECT * " +
                    "FROM patients LEFT JOIN (" + lastVaccinations + ") AS last_vaccinations " +
                    "ON patients.id = last_vaccinations.pid";
            String JT2 = "SELECT JT1.*, V.doctor_id " +
                    "FROM (" + JT1 + ") AS JT1 LEFT JOIN vaccinations AS V " +
                    "ON JT1.id = V.patient_id AND JT1.max = V.vaccination_date";
            command = "SELECT * " +
                    "FROM (" + JT2 + ") AS JT2 " +
                    "WHERE (max <= '" + getDBdiffTime(sec) + "' OR max IS NULL)";            
            System.out.println(command);
            res = dbConn.executeQuery(command);
        } catch (Exception ex) {
            Log4k.error(dbManager.class.getName(), ex.getMessage());
        }
        return res;
    }
    
        public ResultSet getFollowingVaccinationsPatients(int sec){
        ResultSet res = null;
        try {
            String command;
            /* Select the last vaccination of each vaccinated patient after sec seconds ago*/            
            String lastVaccinations = "SELECT DISTINCT MAX(vaccination_date) AS 'vaccination_date', patient_id FROM vaccinations GROUP BY patient_id";
            command = "SELECT * FROM (" + lastVaccinations + ") AS last_vaccinations" +
                    " WHERE vacciantion_date >= '" + getDBdiffTime(sec) + "'";            
            System.out.println(command);
            res = dbConn.executeQuery(command);
        } catch (Exception ex) {
            Log4k.error(dbManager.class.getName(), ex.getMessage());
        }
        return res;
    }

    public void doVaccinate(int doctor_id, int patient_id){
        try {
            String command;
            command = "INSERT INTO vaccinations(patient_id, doctor_id, vaccination_date)" + 
                    " VALUES (" + patient_id + ", " + doctor_id + ", " + "(SELECT NOW())" + ")";
            dbConn.executeStatement(command);
        } catch (Exception ex) {
            Log4k.error(dbManager.class.getName(), ex.getMessage());
        }
    }
    
    public void doRegister(int patient_id, String new_pwd){
        try {
            String command;
            command = "UPDATE patients" + 
                    " SET password = " + new_pwd + ", registered = TRUE" +
                    " WHERE patient_id = " + patient_id;
            dbConn.executeStatement(command);
        } catch (Exception ex) {
            Log4k.error(dbManager.class.getName(), ex.getMessage());
        }
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
