package logManagement;

import org.apache.log4j.*;

/**
 *
 * @author administrator
 */

public class Log4k {
    
    private static final String logPropertiesFile = "logManagement/log4k.properties";  
    
    public static org.apache.log4j.Logger log = Logger.getLogger(Log4k.class);
    private static boolean hasInit = false;
    
    private static void init() {        
        if (!hasInit){
            PropertyConfigurator.configure (
                    Log4k.class.getClassLoader().getResource(logPropertiesFile));
            hasInit = true;
        }
    }
    
    /* Mainly used to trace wrong movements of users */
    public static void trace (String className, String msg){
        if(!hasInit) init();
        log.fatal(className + ": " + msg);
    }
        
    /* Mainly used to get useful information for debug */
    public static void debug (String className, String msg){
        if(!hasInit) init();
        log.fatal(className + ": " + msg);
    }
    
    /* Mainly used to warn the programmers ourselves that the program flow is
     * entered in some unexpected, theoretically impossible, section
     */
    public static void warn (String className, String msg){
        if(!hasInit) init();
        log.warn(className + ": " + msg);
    }
    
    /* Mainly used to point out errors coming from external causes such as db
     * problems or whatsoever
     */
    public static void error (String className, String msg){
        if(!hasInit) init();
        log.error(className + ": " + msg);
    }
    
    
}