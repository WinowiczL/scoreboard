package main.java.com.sportradar.domain;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {

    void saveMatch(Match match);

    boolean isAnyUnfinishedMatch(String homeTeamCountry, String awayTeamCountry);

    Optional<Match> getNotFinishedMatch(String homeTeamCountry, String awayTeamCountry);

    List<Match> getAllNotFinishedMatchesByTotalScore();

}
