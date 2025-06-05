package main.java.com.sportradar.domain;

import main.java.com.sportradar.domain.command.EndMatchCommand;
import main.java.com.sportradar.domain.command.StartMatchCommand;
import main.java.com.sportradar.domain.command.UpdateMatchScoresCommand;
import main.java.com.sportradar.domain.dto.MatchDto;

import java.util.List;
import java.util.Optional;
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
        Optional<Match> notFinishedMatch = matchRepository.getNotFinishedMatch(endMatchCommand.homeTeamCountry(), endMatchCommand.awayTeamCountry());
        notFinishedMatch.ifPresentOrElse((match -> {
            match.finishMatch();
            matchRepository.saveMatch(match);
        }), () -> {
            throw new IllegalStateException("No unfinished match found between " +
                    endMatchCommand.homeTeamCountry() + " and " + endMatchCommand.awayTeamCountry());
        });
    }

    void updateMatchScores(UpdateMatchScoresCommand updateMatchScoresCommand) {
        Optional<Match> notFinishedMatch = matchRepository.getNotFinishedMatch(updateMatchScoresCommand.homeTeamCountry(), updateMatchScoresCommand.awayTeamCountry());
        notFinishedMatch.ifPresentOrElse((match -> {
            match.updateScore(updateMatchScoresCommand.homeTeamScore(), updateMatchScoresCommand.awayTeamScore());
            matchRepository.saveMatch(match);
        }), () -> {
            throw new IllegalStateException("No unfinished match found between " +
                    updateMatchScoresCommand.homeTeamCountry() + " and " + updateMatchScoresCommand.awayTeamCountry());
        });

    }

    public List<MatchDto> getSummaryOfNotFinishedMatchesByTotalScore() {
        return matchRepository.getAllNotFinishedMatchesByTotalScore()
                .stream()
                .map(Match::toDto)
                .toList();

    }
}
