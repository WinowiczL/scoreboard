package main.java.com.sportradar.infra;

import main.java.com.sportradar.domain.Match;
import main.java.com.sportradar.domain.MatchRepository;

import java.util.HashMap;
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
    public boolean isAnyUnfinishedMatch(String homeTeamCountry, String awayTeamCountry) {
        if (homeTeamCountry == null || homeTeamCountry.isBlank() || awayTeamCountry == null || awayTeamCountry.isBlank()) {
            throw new IllegalArgumentException("Team and away team cannot be null or blank");
        }
        return matches.values().stream()
                .anyMatch(match -> match.getHomeTeam().country().equals(homeTeamCountry) &&
                        match.getAwayTeam().country().equals(awayTeamCountry) && !match.isMatchFinished());
    }


}
