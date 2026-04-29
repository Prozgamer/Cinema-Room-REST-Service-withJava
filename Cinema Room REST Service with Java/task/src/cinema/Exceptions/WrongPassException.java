package cinema.Exceptions;

public class WrongPassException extends RuntimeException {
    public WrongPassException() {
        super("The password is wrong!");
    }
}
