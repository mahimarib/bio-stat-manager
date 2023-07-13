package biostat;

public class PersonAlreadyExistsException extends Exception {
    public PersonAlreadyExistsException() {}

    public PersonAlreadyExistsException(String message) {
        super(message);
    }

    public PersonAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
