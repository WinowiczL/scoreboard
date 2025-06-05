package main.java.com.sportradar.infra;

import main.java.com.sportradar.domain.Team;
import main.java.com.sportradar.domain.TeamRepository;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class InMemoryTeamRepository implements TeamRepository {

    private final HashMap<UUID, Team> teams = new HashMap<>();

    @Override
    public Team saveTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }
        UUID teamId = UUID.randomUUID();
        teams.put(teamId, team);
        return team;
    }

    @Override
    public Optional<Team> getTeamByCountry(String country) {
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be null or blank");
        }
        return teams.values().stream()
                .filter(team -> team.country().equals(country))
                .findFirst();
    }
}
