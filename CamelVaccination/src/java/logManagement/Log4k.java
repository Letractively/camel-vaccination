/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logManagement;
import org.apache.log4j.*;

/**
 *
 * @author administrator
 */



public class Log4k {
    
    private static final String logPropertiesFile = "log/log4j.properties";  
    
    public static org.apache.log4j.Logger log = Logger.getLogger(Log4k.class);
    private static boolean hasInit=false;
    
    public static void init() {        
        if (!hasInit){
            PropertyConfigurator.configure (
                    Log4k.class.getClassLoader().getResource(logPropertiesFile));
            hasInit=true;
        }
    }
    
    public static void debug (String msg){
        if(!hasInit) init();
        log.debug(msg);
    }
    
    public static void warn (String msg){
        if(!hasInit) init();
        log.warn(msg);
    }
    
    public static void error (String msg){
        if(!hasInit) init();
        log.error(msg);
    }
    
    
}