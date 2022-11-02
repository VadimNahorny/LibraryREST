package by.nahorny.library.exeptions;

public class NotSuchCountEditionException extends Exception {

    private String customMessage ="The library does not have the count of edition expected" +
            " to be issued to the reader";

    public NotSuchCountEditionException(String message) {
        this.customMessage = message;
    }
    public NotSuchCountEditionException() {
    }

    @Override
    public String getMessage() {
        return this.customMessage;
    }
}
