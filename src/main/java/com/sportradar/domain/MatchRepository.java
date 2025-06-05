package main.java.com.sportradar.domain;

public interface MatchRepository {

    void saveMatch(Match match);

    boolean isAnyUnfinishedMatch(String homeTeamCountry, String awayTeamCountry);

}
