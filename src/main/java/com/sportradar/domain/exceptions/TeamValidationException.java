package main.java.com.sportradar.domain.exceptions;

public class TeamValidationException extends IllegalArgumentException {
    
    public TeamValidationException(String message) {
        super(message);
    }

}