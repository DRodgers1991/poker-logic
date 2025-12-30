package com.poker.logic.timers;

import com.poker.logic.config.PokerProperties;
import com.poker.logic.game.GameStateManager;
import com.poker.logic.game.PokerGame;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetTimeTests {

    PokerProperties properties;
    PokerGame game;
    AbstractPokerTimer pokerTimer;
    GameStateManager gameStateManager;

    @BeforeEach
    public void reset() {
        properties = mock(PokerProperties.class);
        game = mock(PokerGame.class);
        gameStateManager = mock(GameStateManager.class);
        pokerTimer = new TestTimer();
        when(game.getProperties()).thenReturn(properties);
        when(game.getGameStateManager()).thenReturn(gameStateManager);
        pokerTimer.configure(game);
    }

    @Test
    public void getRoundTimeTests() {
        assertEquals(0L, pokerTimer.getRoundTimeToGo(LocalTime.now()));
        when(properties.getRoundTime()).thenReturn(1);
        pokerTimer.run();
        Awaitility.await().atMost(Duration.FIVE_SECONDS).until(() ->pokerTimer.getRoundTimeToGo(LocalTime.now()) > 4000L);
    }

    @Test
    public void getTotalTimeTests() {
        assertEquals(0L, pokerTimer.getTotalGameTime(LocalTime.now()));
        when(properties.getRoundTime()).thenReturn(1);
        pokerTimer.run();
        Awaitility.await().atMost(Duration.FIVE_SECONDS).until(() ->pokerTimer.getTotalGameTime(LocalTime.now()) > 4000L);
    }
}
