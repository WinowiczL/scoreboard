package main.java.com.sportradar.domain.exceptions;

public class MatchNotFoundException extends IllegalStateException {

    public MatchNotFoundException(String message) {
        super(message);
    }

}