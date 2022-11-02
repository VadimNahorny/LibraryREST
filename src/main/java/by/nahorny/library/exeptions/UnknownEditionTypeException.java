package by.nahorny.library.exeptions;

public class UnknownEditionTypeException extends Exception {

    private String customMessage = "Unknown edition type";

    public UnknownEditionTypeException() {
    }

    public UnknownEditionTypeException(String message) {
        this.customMessage = message;
    }

    @Override
    public String getMessage() {
        return this.customMessage;
    }
}
