package main.java.com.sportradar.domain;

import main.java.com.sportradar.domain.command.EndMatchCommand;
import main.java.com.sportradar.domain.command.StartMatchCommand;
import main.java.com.sportradar.domain.command.UpdateMatchScoresCommand;
import main.java.com.sportradar.domain.dto.MatchDto;

import java.util.List;

class MatchService {

    private final MatchRepository matchRepository;

    MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    void startMatch(StartMatchCommand startMatchCommand) {
        throw new UnsupportedOperationException("Not implemented yet");

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
