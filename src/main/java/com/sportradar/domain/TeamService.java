package main.java.com.sportradar.domain;

import main.java.com.sportradar.domain.exceptions.TeamValidationException;

import java.util.Optional;

public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Team> getTeamByCountry(String country) {
        if (country == null || country.isBlank()) {
            throw new TeamValidationException("Country cannot be null or blank");
        }
        return teamRepository.getTeamByCountry(country);
    }

    public Team saveTeam(String country) {
        if (country == null || country.isBlank()) {
            throw new TeamValidationException("Country cannot be null or blank");
        }
        Team team = new Team(country);
        return teamRepository.saveTeam(team);
    }

}
