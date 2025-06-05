package main.java.com.sportradar.domain;

import main.java.com.sportradar.domain.command.EndMatchCommand;
import main.java.com.sportradar.domain.command.StartMatchCommand;
import main.java.com.sportradar.domain.command.UpdateMatchScoresCommand;
import main.java.com.sportradar.domain.dto.MatchDto;

import java.util.List;

public class ScoreBoardFacade {

    private final MatchService matchService;

    public ScoreBoardFacade(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchService = new MatchService(matchRepository, new TeamService(teamRepository));
    }

    public void startMatch(String homeTeamCountry, String awayTeamCountry) {
        StartMatchCommand startMatchCommand = new StartMatchCommand(homeTeamCountry, awayTeamCountry);
        matchService.startMatch(startMatchCommand);
    }

    public void endMatch(String homeTeamCountry, String awayTeamCountry) {
        EndMatchCommand endMatchCommand = new EndMatchCommand(homeTeamCountry, awayTeamCountry);
        matchService.endMatch(endMatchCommand);

    }

    public void updateScore(String homeTeamCountry, String awayTeamCountry, int homeTeamScore, int awayTeamScore) {
        UpdateMatchScoresCommand updateMatchScoresCommand = new UpdateMatchScoresCommand(
                homeTeamCountry, awayTeamCountry, new Score(homeTeamScore), new Score(awayTeamScore));
        matchService.updateMatchScores(updateMatchScoresCommand);

    }

    public List<MatchDto> getSummaryOfNotFinishedMatchesByTotalScore() {
        return matchService.getSummaryOfNotFinishedMatchesByTotalScore();
    }

}
