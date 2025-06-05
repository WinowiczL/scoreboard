package main.java.com.sportradar.domain;

import main.java.com.sportradar.domain.dto.MatchDto;

import java.util.UUID;

public final class Match {

    private final UUID id;
    private final Team homeTeam;
    private final Team awayTeam;
    private Score homeScore;
    private Score awayScore;
    private boolean matchFinished;

    public Match(UUID id, Team homeTeam, Team awayTeam) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = new Score(0);
        this.awayScore = new Score(0);
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
            throw new IllegalStateException("Cannot update score for a finished match");
        }
        if (homeScore.value() < 0 || awayScore.value() < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
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


    public UUID getId() {
        return id;
    }
}
