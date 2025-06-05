package main.java.com.sportradar.domain.command;

import main.java.com.sportradar.domain.exceptions.TeamValidationException;

public record StartMatchCommand(
        String homeTeamCountry,
        String awayTeamCountry) {

    public StartMatchCommand {
        if (homeTeamCountry == null || homeTeamCountry.isBlank()) {
            throw new TeamValidationException("Home team country cannot be null or blank");
        }
        if (awayTeamCountry == null || awayTeamCountry.isBlank()) {
            throw new TeamValidationException("Away team country cannot be null or blank");
        }
        if (homeTeamCountry.equalsIgnoreCase(awayTeamCountry)) {
            throw new TeamValidationException("Home and away teams cannot be the same");
        }
    }

}
