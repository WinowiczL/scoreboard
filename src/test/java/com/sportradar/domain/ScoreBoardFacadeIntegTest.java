package test.java.com.sportradar.domain;

import main.java.com.sportradar.domain.ScoreBoardFacade;
import main.java.com.sportradar.domain.dto.MatchDto;
import main.java.com.sportradar.domain.exceptions.MatchAlreadyExistsException;
import main.java.com.sportradar.domain.exceptions.MatchNotFoundException;
import main.java.com.sportradar.domain.exceptions.ScoreValidationException;
import main.java.com.sportradar.domain.exceptions.TeamValidationException;
import main.java.com.sportradar.infra.InMemoryMatchRepository;
import main.java.com.sportradar.infra.InMemoryTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreBoardFacadeIntegTest {

    private ScoreBoardFacade scoreBoardFacade;

    @BeforeEach
    public void setUp() {
        scoreBoardFacade = new ScoreBoardFacade(new InMemoryMatchRepository(), new InMemoryTeamRepository());
    }

    @Test
    void shouldStartNewMatchWithInitialScore() {
        // given
        String homeTeam = "Poland";
        String awayTeam = "Germany";

        // when
        scoreBoardFacade.startMatch(homeTeam, awayTeam);
        List<MatchDto> matches = scoreBoardFacade.getSummaryOfNotFinishedMatchesByTotalScore();

        // then
        assertEquals(1, matches.size());
        MatchDto match = matches.getFirst();
        assertEquals(homeTeam, match.homeTeamCountry());
        assertEquals(awayTeam, match.awayTeamCountry());
        assertEquals(0, match.homeScore());
        assertEquals(0, match.awayScore());
    }

    @Test
    void shouldUpdateScore() {
        // given
        String homeTeam = "Poland";
        String awayTeam = "Germany";
        int newHomeScoreValue = 2;
        int newAwayScoreValue = 1;

        scoreBoardFacade.startMatch(homeTeam, awayTeam);

        // when
        scoreBoardFacade.updateScore(homeTeam, awayTeam, newHomeScoreValue, newAwayScoreValue);
        List<MatchDto> matches = scoreBoardFacade.getSummaryOfNotFinishedMatchesByTotalScore();

        // then
        assertEquals(1, matches.size());
        MatchDto match = matches.getFirst();
        assertEquals(homeTeam, match.homeTeamCountry());
        assertEquals(awayTeam, match.awayTeamCountry());
        assertEquals(newHomeScoreValue, match.homeScore());
        assertEquals(newAwayScoreValue, match.awayScore());
    }

    @Test
    void shouldEndMatch() {
        // given
        String homeTeam = "Poland";
        String awayTeam = "Germany";

        scoreBoardFacade.startMatch(homeTeam, awayTeam);
        assertEquals(1, scoreBoardFacade.getSummaryOfNotFinishedMatchesByTotalScore().size());

        // when
        scoreBoardFacade.endMatch(homeTeam, awayTeam);
        List<MatchDto> matches = scoreBoardFacade.getSummaryOfNotFinishedMatchesByTotalScore();

        // then
        assertEquals(0, matches.size());
    }

    @Test
    void shouldReturnMatchesSortedByTotalScore() {
        // given
        scoreBoardFacade.startMatch("Mexico", "Canada");
        scoreBoardFacade.updateScore("Mexico", "Canada", 0, 5);

        scoreBoardFacade.startMatch("Spain", "Brazil");
        scoreBoardFacade.updateScore("Spain", "Brazil", 10, 2);

        scoreBoardFacade.startMatch("Germany", "France");
        scoreBoardFacade.updateScore("Germany", "France", 2, 2);

        scoreBoardFacade.startMatch("Uruguay", "Italy");
        scoreBoardFacade.updateScore("Uruguay", "Italy", 6, 6);

        scoreBoardFacade.startMatch("Argentina", "Australia");
        scoreBoardFacade.updateScore("Argentina", "Australia", 3, 1);

        // when
        List<MatchDto> matches = scoreBoardFacade.getSummaryOfNotFinishedMatchesByTotalScore();

        // then
        assertEquals(5, matches.size());

        // Verify order: Uruguay 6 - Italy 6 (total: 12)
        assertEquals("Uruguay", matches.get(0).homeTeamCountry());
        assertEquals("Italy", matches.get(0).awayTeamCountry());

        // Spain 10 - Brazil 2 (total: 12)
        assertEquals("Spain", matches.get(1).homeTeamCountry());
        assertEquals("Brazil", matches.get(1).awayTeamCountry());

        // Mexico 0 - Canada 5 (total: 5)
        assertEquals("Mexico", matches.get(2).homeTeamCountry());
        assertEquals("Canada", matches.get(2).awayTeamCountry());

        // Argentina 3 - Australia 1 (total: 4)
        assertEquals("Argentina", matches.get(3).homeTeamCountry());
        assertEquals("Australia", matches.get(3).awayTeamCountry());

        // Germany 2 - France 2 (total: 4)
        assertEquals("Germany", matches.get(4).homeTeamCountry());
        assertEquals("France", matches.get(4).awayTeamCountry());
    }

    @Test
    void shouldReturnEmptyListWhenNoMatches() {
        // when
        List<MatchDto> matches = scoreBoardFacade.getSummaryOfNotFinishedMatchesByTotalScore();

        // then
        assertTrue(matches.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenStartingMatchWithSameTeams() {
        // given
        String team = "Uruguay";

        // when & then
        assertThrows(TeamValidationException.class, () -> scoreBoardFacade.startMatch(team, team));
    }

    @Test
    void shouldThrowExceptionWhenStartingMatchWithTeamAlreadyPlaying() {
        // given
        String homeTeam = "Spain";
        String awayTeam = "Brazil";

        scoreBoardFacade.startMatch(homeTeam, awayTeam);

        // when & then
        assertThrows(MatchAlreadyExistsException.class, () -> scoreBoardFacade.startMatch(homeTeam, "Argentina"));
    }

    @Test
    void shouldThrowExceptionWhenStartingMatchWithTeamsAlreadyPlaying() {
        // given
        String homeTeam = "Argentina";
        String awayTeam = "Australia";

        scoreBoardFacade.startMatch(homeTeam, awayTeam);

        // when & then
        assertThrows(MatchAlreadyExistsException.class, () -> scoreBoardFacade.startMatch(homeTeam, awayTeam));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingScoreForNonExistentMatch() {
        // when & then
        assertThrows(MatchNotFoundException.class, () -> scoreBoardFacade.updateScore("Mexico", "Canada", 1, 1));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidScores")
    void shouldThrowExceptionWhenUpdatingScoreWithNegativeValues(int homeScore, int awayScore) {
        // given
        String homeTeam = "Germany";
        String awayTeam = "France";

        scoreBoardFacade.startMatch(homeTeam, awayTeam);

        // when & then
        assertThrows(ScoreValidationException.class, () -> scoreBoardFacade.updateScore(homeTeam, awayTeam, homeScore, awayScore));
    }

    private static Stream<Arguments> provideInvalidScores() {
        return Stream.of(
                Arguments.of(-1, 0),
                Arguments.of(0, -1),
                Arguments.of(-1, -1)
        );
    }

    @Test
    void shouldThrowExceptionWhenEndingNonExistentMatch() {
        // when & then
        assertThrows(MatchNotFoundException.class, () -> scoreBoardFacade.endMatch("Poland", "Germany"));
    }

    @Test
    void shouldHandleMultipleMatchesCorrectly() {
        // given
        scoreBoardFacade.startMatch("Poland", "Germany");
        scoreBoardFacade.startMatch("Brazil", "Argentina");
        scoreBoardFacade.startMatch("Spain", "Italy");

        // when
        scoreBoardFacade.updateScore("Poland", "Germany", 2, 1);
        scoreBoardFacade.updateScore("Brazil", "Argentina", 3, 3);
        scoreBoardFacade.endMatch("Spain", "Italy");

        List<MatchDto> matches = scoreBoardFacade.getSummaryOfNotFinishedMatchesByTotalScore();

        // then
        assertEquals(2, matches.size());

        // Brazil 3 - Argentina 3 (total: 6)
        assertEquals("Brazil", matches.getFirst().homeTeamCountry());
        assertEquals("Argentina", matches.getFirst().awayTeamCountry());
        assertEquals(3, matches.get(0).homeScore());
        assertEquals(3, matches.get(0).awayScore());

        // Poland 2 - Germany 1 (total: 3)
        assertEquals("Poland", matches.get(1).homeTeamCountry());
        assertEquals("Germany", matches.get(1).awayTeamCountry());
        assertEquals(2, matches.get(1).homeScore());
        assertEquals(1, matches.get(1).awayScore());
    }
}
