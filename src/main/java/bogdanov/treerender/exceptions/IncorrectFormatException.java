package bogdanov.treerender.exceptions;

public class IncorrectFormatException extends RuntimeException{
    public IncorrectFormatException() {
        this("Incorrect format");
    }

    public IncorrectFormatException(String message) {
        super(message);
    }
}
