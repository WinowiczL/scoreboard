package main.java.com.sportradar.domain;

import main.java.com.sportradar.domain.command.EndMatchCommand;
import main.java.com.sportradar.domain.command.StartMatchCommand;
import main.java.com.sportradar.domain.command.UpdateMatchScoresCommand;
import main.java.com.sportradar.domain.dto.MatchDto;

import java.util.List;
import java.util.UUID;

class MatchService {

    private final MatchRepository matchRepository;
    private final TeamService teamService;

    MatchService(MatchRepository matchRepository, TeamService teamService) {
        this.matchRepository = matchRepository;
        this.teamService = teamService;
    }

    void startMatch(StartMatchCommand startMatchCommand) {
        boolean unfinishedMatch = matchRepository.isAnyUnfinishedMatch(startMatchCommand.homeTeamCountry(), startMatchCommand.awayTeamCountry());
        if (unfinishedMatch) {
            throw new IllegalStateException("There is already an unfinished match between " +
                    startMatchCommand.homeTeamCountry() + " and " + startMatchCommand.awayTeamCountry());
        }

        Team homeTeam = teamService.getTeamByCountry(startMatchCommand.homeTeamCountry())
                .orElseGet(() -> teamService.saveTeam(startMatchCommand.homeTeamCountry()));
        Team awayTeam = teamService.getTeamByCountry(startMatchCommand.awayTeamCountry())
                .orElseGet(() -> teamService.saveTeam(startMatchCommand.awayTeamCountry()));

        Match match = new Match(UUID.randomUUID(), homeTeam, awayTeam);
        matchRepository.saveMatch(match);
    }

    void endMatch(EndMatchCommand endMatchCommand) {
        throw new UnsupportedOperationException("Not implemented yet");

    }

    void updateMatchScores(UpdateMatchScoresCommand updateMatchScoresCommand) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public List<MatchDto> getSummaryOfNotFinishedMatchesByTotalScore() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
