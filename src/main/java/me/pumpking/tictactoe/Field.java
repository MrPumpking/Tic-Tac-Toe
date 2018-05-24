package me.pumpking.tictactoe;

public enum Field {
    X("X"), O("O"), EMPTY("");

    private String name;

    Field(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
