package bogdanov.treerender.exceptions;

public class EmptyInputFileException extends RuntimeException{

    public EmptyInputFileException(String message) {
        super(message);
    }

    public EmptyInputFileException() {
        this("Empty input File");
    }
}
