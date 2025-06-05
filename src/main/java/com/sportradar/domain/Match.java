package main.java.com.sportradar.domain;

import main.java.com.sportradar.domain.dto.MatchDto;
import main.java.com.sportradar.domain.exceptions.FinishedMatchUpdateException;
import main.java.com.sportradar.domain.exceptions.ScoreValidationException;

import java.time.LocalDateTime;
import java.util.UUID;

public final class Match {

    private static final int INITIAL_SCORE = 0;

    private final UUID id;
    private final Team homeTeam;
    private final Team awayTeam;
    private final LocalDateTime createdAt;
    private Score homeScore;
    private Score awayScore;
    private boolean matchFinished;

    public Match(UUID id, Team homeTeam, Team awayTeam) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = new Score(INITIAL_SCORE);
        this.awayScore = new Score(INITIAL_SCORE);
        this.createdAt = LocalDateTime.now();
    }

    public MatchDto toDto() {
        return new MatchDto(
                homeTeam.country(),
                awayTeam.country(),
                homeScore.value(),
                awayScore.value()
        );
    }

    public void updateScore(Score homeScore, Score awayScore) {
        if (matchFinished) {
            throw new FinishedMatchUpdateException("Cannot update score for a finished match");
        }
        if (homeScore.value() < 0 || awayScore.value() < 0) {
            throw new ScoreValidationException("Scores cannot be negative");
        }
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public void finishMatch() {
        this.matchFinished = true;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public boolean isMatchFinished() {
        return matchFinished;
    }

    public Score getHomeScore() {
        return homeScore;
    }

    public Score getAwayScore() {
        return awayScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getId() {
        return id;
    }
}
