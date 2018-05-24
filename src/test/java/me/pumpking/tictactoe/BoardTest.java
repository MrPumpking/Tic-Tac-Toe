package me.pumpking.tictactoe;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class BoardTest {

    private Board board;

    private static final int WIDTH = 3;
    private static final int HEIGHT = 3;

    @Before
    public void before() {
        this.board = new Board();
    }

    @Test
    public void create() {
        assertThat(board).isNotNull();
        assertThat(board.getFields()).isNotNull();
        assertThat(board.getFields()).isNotEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFieldWithNegativeX() {
        board.setField(-1, 0, Field.X);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFieldWithNegativeY() {
        board.setField(0, -1, Field.X);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFieldWithXEqualToWidth() {
        board.setField(WIDTH, 0, Field.X);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFieldWithYEqualToHeight() {
        board.setField(0, HEIGHT, Field.X);
    }

    @Test(expected = NullPointerException.class)
    public void setNullField() {
        board.setField(0, 0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFieldEmpty() {
        board.setField(0, 0, Field.EMPTY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeAlreadyTakenField() {
        board.setField(0, 0, Field.X);
        board.setField(0, 0, Field.O);
    }

    @Test
    public void setFieldCheckFieldType() {
        board.setField(0, 0, Field.X);
        assertThat(board.getField(0, 0)).isEqualTo(Field.X);
    }

    @Test
    public void markIndexAsSelected() {
        board.markIndexAsSelected(Field.X, 0);
        assertThat(board.getAvailableIndexes()).doesNotContain(0);
        assertThat(board.getXFieldIndexes()).contains(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void markIndexAsSelectedForEmpty() {
        board.markIndexAsSelected(Field.EMPTY, 0);
    }

    @Test
    public void setFieldCheckXIndexAddition() {
        board.setField(0, 0, Field.X);
        assertThat(board.getXFieldIndexes()).contains(0);
    }

    @Test
    public void setFieldCheckOIndexAddition() {
        board.setField(0, 0, Field.O);
        assertThat(board.getOFieldIndexes()).contains(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFieldWithNegativeX() {
        board.getField(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFieldWithNegativeY() {
        board.getField(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFieldWithXEqualToWidth() {
        board.getField(WIDTH, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFieldWithYEqualToHeight() {
        board.getField(0, HEIGHT);
    }

    @Test
    public void clear() {
        board.setField(0, 0, Field.X);
        board.setField(1, 1, Field.O);
        board.clear();

        assertThat(board.getField(0, 0)).isEqualTo(Field.EMPTY);
        assertThat(board.getField(1, 1)).isEqualTo(Field.EMPTY);
        assertThat(board.getAvailableIndexes()).hasSize(WIDTH * HEIGHT);
        assertThat(board.getXFieldIndexes()).hasSize(0);
        assertThat(board.getOFieldIndexes()).hasSize(0);
    }

}
