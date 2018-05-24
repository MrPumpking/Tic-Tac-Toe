package me.pumpking.tictactoe;

public enum GameState {
    PROCESSING("Przetwarzanie..."),
    X_TURN("Ruch gracza X"),
    O_TURN("Ruch gracza O"),
    X_WINS("Gracz X zwyciężył!"),
    O_WINS("Gracz O zwyciężył!"),
    DRAW("Remis!");

    private final String text;

    GameState(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
