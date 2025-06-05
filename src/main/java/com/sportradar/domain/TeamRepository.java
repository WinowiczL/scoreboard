package main.java.com.sportradar.domain;

import java.util.Optional;

public interface TeamRepository {

    Optional<Team> getTeamByCountry(String country);

    Team saveTeam(Team team);

}
