package biostat;

public class PersonDoesNotExistsException extends Exception {
    public PersonDoesNotExistsException() {}

    public PersonDoesNotExistsException(String message) {
        super(message);
    }

    public PersonDoesNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
