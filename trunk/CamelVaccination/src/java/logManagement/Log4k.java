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
    
    /* Private: does not need synchronization */
    private static void init() {
        if (!hasInit){
            PropertyConfigurator.configure (
                    Log4k.class.getClassLoader().getResource(logPropertiesFile));
            hasInit=true;
        }
    }
    
    synchronized public static void debug (String className, String msg){
        if(!hasInit) init();
        log.debug(className+": "+msg);
    }
    
    synchronized public static void warn (String className, String msg){
        if(!hasInit) init();
        log.warn(className+": "+msg);
    }
    
    synchronized public static void error (String className, String msg){
        if(!hasInit) init();
        log.error(className+": "+msg);
    }
    

}