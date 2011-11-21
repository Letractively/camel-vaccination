package dbManagement;

/**
 *
 * @author FastLDL
 */
public class YetRegisteredException extends Exception {

    public YetRegisteredException() {        
        super("Impossibile terminare la registrazione: "
                + "utente gia' registrato");
    }
}
