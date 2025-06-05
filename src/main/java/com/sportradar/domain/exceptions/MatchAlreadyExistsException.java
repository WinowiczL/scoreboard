package main.java.com.sportradar.domain.exceptions;

public class MatchAlreadyExistsException extends IllegalStateException {

    public MatchAlreadyExistsException(String message) {
        super(message);
    }

}