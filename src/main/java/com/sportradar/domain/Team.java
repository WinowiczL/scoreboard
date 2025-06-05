package main.java.com.sportradar.domain;

import main.java.com.sportradar.domain.exceptions.TeamValidationException;

public record Team(String country) {

    public Team {
        if (country == null || country.isBlank()) {
            throw new TeamValidationException("Country cannot be null or blank");
        }
    }

}
