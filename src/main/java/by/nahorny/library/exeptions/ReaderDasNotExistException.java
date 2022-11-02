package by.nahorny.library.exeptions;

public class ReaderDasNotExistException extends Exception {

    private String customMessage = "The requested reader das not exist";

    public ReaderDasNotExistException(String message) {
        this.customMessage = message;
    }

    public ReaderDasNotExistException() {
    }

    @Override
    public String getMessage() {
        return this.customMessage;
    }
}
