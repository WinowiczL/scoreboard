package main.java.com.sportradar.domain.exceptions;

public class FinishedMatchUpdateException extends IllegalStateException {

    public FinishedMatchUpdateException(String message) {
        super(message);
    }

}