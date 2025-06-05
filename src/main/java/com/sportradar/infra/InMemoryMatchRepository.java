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

}
