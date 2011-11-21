package dbManagement;

/**
 *
 * @author FastLDL
 * Thrown whenever a user tries to register, but the patient is not present into the DB
 */
public class NotInDBException extends Exception {

    public NotInDBException() {
        super("Impossibile terminare la registrazione: "
                + "l'utente non e' presente nel database");
    }
}
