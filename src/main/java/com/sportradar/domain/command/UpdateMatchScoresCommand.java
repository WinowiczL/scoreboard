package main.java.com.sportradar.domain.command;

import main.java.com.sportradar.domain.Score;

public record UpdateMatchScoresCommand(
        String homeTeamCountry,
        String awayTeamCountry,
        Score homeTeamScore,
        Score awayTeamScore) {

    public UpdateMatchScoresCommand {
        if (homeTeamCountry == null || homeTeamCountry.isBlank()) {
            throw new IllegalArgumentException("Home team country cannot be null or blank");
        }
        if (awayTeamCountry == null || awayTeamCountry.isBlank()) {
            throw new IllegalArgumentException("Away team country cannot be null or blank");
        }
        if (homeTeamCountry.equalsIgnoreCase(awayTeamCountry)) {
            throw new IllegalArgumentException("Home and away teams cannot be the same");
        }
        if (homeTeamScore == null || homeTeamScore.value() < 0) {
            throw new IllegalArgumentException("Home team score cannot be negative");
        }
        if (awayTeamScore == null || awayTeamScore.value() < 0) {
            throw new IllegalArgumentException("Away team score cannot be negative");
        }
    }
}
