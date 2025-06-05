package main.java.com.sportradar.domain.command;

public record StartMatchCommand(
        String homeTeamCountry,
        String awayTeamCountry) {

    public StartMatchCommand {
        if (homeTeamCountry == null || homeTeamCountry.isBlank()) {
            throw new IllegalArgumentException("Home team country cannot be null or blank");
        }
        if (awayTeamCountry == null || awayTeamCountry.isBlank()) {
            throw new IllegalArgumentException("Away team country cannot be null or blank");
        }
        if (homeTeamCountry.equalsIgnoreCase(awayTeamCountry)) {
            throw new IllegalArgumentException("Home and away teams cannot be the same");
        }
    }

}
