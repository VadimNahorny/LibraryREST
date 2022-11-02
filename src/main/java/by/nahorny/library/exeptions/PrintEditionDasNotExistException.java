package by.nahorny.library.exeptions;

public class PrintEditionDasNotExistException extends Exception {

    private String customMessage ="The requested print edition das not exist";


    public PrintEditionDasNotExistException(String message) {
        this.customMessage = message;
    }

    public PrintEditionDasNotExistException() {
    }

    @Override
    public String getMessage() {
        return this.customMessage;
    }

}
