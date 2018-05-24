package me.pumpking.tictactoe;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

public class GameTest {

    private Game game;
    private static final Field INITIAL_TURN_FIELD = Field.X;

    @Before
    public void before() {
        GameWindow window = Mockito.mock(GameWindow.class);
        this.game = new Game(window);
    }

    @Test
    public void create() {
        assertThat(game).isNotNull();
        assertThat(game.getBoard()).isNotNull();
        assertThat(game.getState()).isEqualTo(GameState.X_TURN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void hasWonNull() {
        game.hasWon(null);
    }

    @Test
    public void hasWon() {
        game.getBoard().setField(0, 0, Field.X);
        game.getBoard().setField(0, 1, Field.X);
        game.getBoard().setField(0, 2, Field.X);
        assertThat(game.hasWon(Field.X)).isEqualTo(true);
    }

    @Test
    public void isGameDrawn() {
        game.getBoard().getAvailableIndexes().clear();
        assertThat(game.isGameDrawn()).isEqualTo(true);
    }

    @Test
    public void onFieldSelected() {
        game.onFieldSelected(0, 0);
        assertThat(game.getBoard().getField(0, 0)).isEqualTo(INITIAL_TURN_FIELD);
        assertThat(game.getState()).isEqualTo(GameState.O_TURN);
    }

    @Test
    public void onFieldSelectedXWins() {
        game.onFieldSelected(0, 0);
        game.onFieldSelected(0, 1);
        game.onFieldSelected(1, 0);
        game.onFieldSelected(1, 1);
        game.onFieldSelected(2, 0);
        assertThat(game.getState()).isEqualTo(GameState.X_WINS);
    }

    @Test
    public void onFieldSelectedDraw() {
        game.onFieldSelected(1, 0);
        game.onFieldSelected(0, 0);
        game.onFieldSelected(2, 0);
        game.onFieldSelected(2, 1);
        game.onFieldSelected(0, 1);
        game.onFieldSelected(0, 2);
        game.onFieldSelected(1, 1);
        game.onFieldSelected(1, 2);
        game.onFieldSelected(2, 2);
        assertThat(game.getState()).isEqualTo(GameState.DRAW);
    }

}
