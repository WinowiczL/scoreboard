package main.java.com.sportradar.domain.exceptions;

public class ScoreValidationException extends IllegalArgumentException {
    
    public ScoreValidationException(String message) {
        super(message);
    }
    
}