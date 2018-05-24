package me.pumpking.tictactoe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class Board {

    private Field[] fields;
    private Set<Integer> xFieldIndexes;
    private Set<Integer> oFieldIndexes;
    private List<Integer> availableIndexes;

    static final int WIDTH = 3;
    static final int HEIGHT = 3;

    Board() {
        this.fields = new Field[WIDTH * HEIGHT];
        this.xFieldIndexes = new HashSet<>();
        this.oFieldIndexes = new HashSet<>();
        this.availableIndexes = new ArrayList<>();
        this.clear();
    }

    /**
     * Clear the board by resetting all fields
     * to EMPTY and restoring available indexes
     */
    @VisibleForTesting void clear() {
        xFieldIndexes.clear();
        oFieldIndexes.clear();
        availableIndexes.clear();

        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            fields[i] = Field.EMPTY;
            availableIndexes.add(i);
        }
    }

    /**
     * Get field from the board
     * @param x must be between [0, board width)
     * @param y must be between [0, board height)
     * @return field positioned on the given coordinates
     */
    Field getField(int x, int y) {
        Preconditions.checkArgument(x >= 0 && x < WIDTH, "Invalid x coordinate");
        Preconditions.checkArgument(y >= 0 && y < HEIGHT, "Invalid y coordinate");

        return fields[y * WIDTH + x];
    }

    /**
     * Set a field to a given value and add the corresponding field
     * index to the proper Set containing indexes for given field value.
     * The field to be set must be {@code Field.EMPTY}
     *
     * @param x must be between [0, board width)
     * @param y must be between [0, board height)
     * @param field value to be set. Must be either {@code Field.X} or {@code Field.O}
     */
    void setField(int x, int y, Field field) {
        Preconditions.checkNotNull(field);
        Preconditions.checkArgument(field == Field.X || field == Field.O, "Field cannot be set to be EMPTY manually");
        Preconditions.checkArgument(getField(x, y) == Field.EMPTY, "Cannot change the value of already taken field");

        int index = y * WIDTH + x;
        fields[index] = field;
        markIndexAsSelected(field, index);
    }

    @VisibleForTesting void markIndexAsSelected(Field fieldType, int index) {
        Preconditions.checkArgument(fieldType == Field.X || fieldType == Field.O, "Cannot add index for EMPTY field");
        Preconditions.checkArgument(availableIndexes.contains(index), "This field has already been marked");

        availableIndexes.remove(availableIndexes.indexOf(index));
        (fieldType == Field.X ? xFieldIndexes : oFieldIndexes).add(index);
    }

    List<Integer> getAvailableIndexes() {
        return availableIndexes;
    }

    Set<Integer> getXFieldIndexes() {
        return xFieldIndexes;
    }

    Set<Integer> getOFieldIndexes() {
        return oFieldIndexes;
    }

    @VisibleForTesting Field[] getFields() {
        return fields;
    }

}
