package main.java.com.sportradar.infra;

import main.java.com.sportradar.domain.Match;
import main.java.com.sportradar.domain.MatchRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryMatchRepository implements MatchRepository {

    private final HashMap<UUID, Match> matches = new HashMap<>();


    @Override
    public void saveMatch(Match match) {
        if (match == null) {
            throw new IllegalArgumentException("Match cannot be null");
        }
        matches.put(match.getId(), match);
    }

    @Override
    public boolean isAnyUnfinishedMatch(List<String> teamCountries) {
        if (teamCountries == null || teamCountries.isEmpty()) {
            throw new IllegalArgumentException("Team and away team cannot be null or blank");
        }
        return matches.values().stream()
                .anyMatch(match -> (teamCountries.contains(match.getHomeTeam().country()) ||
                        teamCountries.contains(match.getAwayTeam().country())) && !match.isMatchFinished());
    }

    @Override
    public Optional<Match> getNotFinishedMatch(String homeTeamCountry, String awayTeamCountry) {
        if (homeTeamCountry == null || homeTeamCountry.isBlank() || awayTeamCountry == null || awayTeamCountry.isBlank()) {
            throw new IllegalArgumentException("Team and away team cannot be null or blank");
        }
        return matches.values().stream()
                .filter(match -> match.getHomeTeam().country().equals(homeTeamCountry) &&
                        match.getAwayTeam().country().equals(awayTeamCountry) && !match.isMatchFinished())
                .findFirst();
    }

    @Override
    public List<Match> getAllNotFinishedMatchesByTotalScore() {

        return matches.values().stream()
                .filter(match -> !match.isMatchFinished())
                .sorted(InMemoryMatchRepository::compareScores)
                .toList();
    }

    private static int compareScores(Match m1, Match m2) {
        int totalPoints1 = m1.getHomeScore().value() + m1.getAwayScore().value();
        int totalPoints2 = m2.getHomeScore().value() + m2.getAwayScore().value();

        int scoreComparison = Integer.compare(totalPoints2, totalPoints1);
        if (scoreComparison != 0) {
            return scoreComparison;
        }

        return m2.getCreatedAt().compareTo(m1.getCreatedAt());
    }


}
