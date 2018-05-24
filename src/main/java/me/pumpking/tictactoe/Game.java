package me.pumpking.tictactoe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

final class Game {

    private Board board;
    private GameState state;
    private GameWindow window;

    private static GameState INITIAL_STATE = GameState.X_TURN;
    private static final List<Set<Integer>> WINNING_COMBINATIONS = new ArrayList<>();

    Game(GameWindow window) {
        this.board = new Board();
        this.state = INITIAL_STATE;
        this.populateWinningCombinations();
        this.window = window;
    }

    private void populateWinningCombinations() {
        WINNING_COMBINATIONS.add(Sets.newHashSet(0, 1, 2));
        WINNING_COMBINATIONS.add(Sets.newHashSet(3, 4, 5));
        WINNING_COMBINATIONS.add(Sets.newHashSet(6, 7, 8));
        WINNING_COMBINATIONS.add(Sets.newHashSet(0, 3, 6));
        WINNING_COMBINATIONS.add(Sets.newHashSet(1, 4, 7));
        WINNING_COMBINATIONS.add(Sets.newHashSet(2, 5, 8));
        WINNING_COMBINATIONS.add(Sets.newHashSet(0, 4, 8));
        WINNING_COMBINATIONS.add(Sets.newHashSet(2, 4, 6));
    }

    void reset() {
        this.board.clear();
        this.window.clear();
        this.setAndDisplayState(INITIAL_STATE);
    }

    GameState getState() {
        return state;
    }

    Board getBoard() {
        return board;
    }

    private void setAndDisplayField(int x, int y, Field field) {
        board.setField(x, y, field);
        window.markFieldAsSelected(x, y, field);
    }

    private void setAndDisplayState(GameState state) {
        this.state = state;
        window.showStatus(state.getText());
    }

    @VisibleForTesting boolean isGameDrawn() {
        return board.getAvailableIndexes().isEmpty();
    }

    @VisibleForTesting boolean hasWon(Field field) {
        Preconditions.checkArgument(field == Field.X || field == Field.O, "Cannot check whether EMPTY field has won");

        Set<Integer> indexes = (field == Field.X ? board.getXFieldIndexes() : board.getOFieldIndexes());
        for (Set<Integer> combination : Game.WINNING_COMBINATIONS) {
            if (indexes.containsAll(combination)) {
                return true;
            }
        }
        return false;
    }

    void onFieldSelected(int x, int y) {
        switch (state) {
            case X_TURN:
                setAndDisplayField(x, y, Field.X);
                setAndDisplayState(GameState.PROCESSING);
                setAndDisplayState(hasWon(Field.X) ? GameState.X_WINS : GameState.O_TURN);
                break;
            case O_TURN:
                setAndDisplayField(x, y, Field.O);
                setAndDisplayState(GameState.PROCESSING);
                setAndDisplayState(hasWon(Field.O) ? GameState.O_WINS : GameState.X_TURN);
                break;
        }

        if (state != GameState.X_WINS && state != GameState.O_WINS && isGameDrawn()) {
            setAndDisplayState(GameState.DRAW);
        }

    }

}
