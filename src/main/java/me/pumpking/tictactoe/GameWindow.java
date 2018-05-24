package me.pumpking.tictactoe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameWindow extends Application implements EventHandler<ActionEvent> {

    private Game game;
    private Label status;
    private GridPane fields;

    private static final String BUTTON_NORMAL_STYLE = "-fx-font: 24 monospaced; -fx-font-weight: bold; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;";
    private static final String BUTTON_DISABLED_STYLE = BUTTON_NORMAL_STYLE + "-fx-opacity: 1.0;";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        this.game = new Game(this);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tic Tac Toe");
        stage.setResizable(false);

        BorderPane controls = new BorderPane();
        controls.setStyle("-fx-padding: 10;");

        status = new Label(game.getState().getText());
        status.setStyle("-fx-font: 16 arial;");

        Button reset = new Button("Reset");
        reset.setOnAction(event -> game.reset());

        controls.setLeft(status);
        controls.setRight(reset);

        fields = new GridPane();

        for (int i = 0; i < Board.WIDTH; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS);
            cc.setFillWidth(true);
            fields.getColumnConstraints().add(cc);
        }

        for (int i = 0; i < Board.HEIGHT; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS);
            rc.setFillHeight(true);
            fields.getRowConstraints().add(rc);
        }

        for (int y = 0; y < Board.HEIGHT; y++) {
            for (int x = 0; x < Board.WIDTH; x++) {
                Button field = new Button(" ");
                field.setOnAction(this);
                field.setStyle(BUTTON_NORMAL_STYLE);
                field.getProperties().put("X", x);
                field.getProperties().put("Y", y);
                field.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                fields.add(field, x, y);
            }
        }

        BorderPane root = new BorderPane();
        root.setTop(controls);
        root.setCenter(fields);

        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if (!(event.getSource() instanceof Button)) return;

        Button field = (Button) event.getSource();
        int x = (int) field.getProperties().get("X");
        int y = (int) field.getProperties().get("Y");
        game.onFieldSelected(x, y);

        event.consume();
    }

    void clear() {
        for (Node field : fields.getChildren()) {
            Button button = (Button) field;
            button.setText(" ");
            button.setStyle(BUTTON_NORMAL_STYLE);
            button.setDisable(false);
        }
    }

    void showStatus(String text) {
        this.status.setText(text);
    }

    void markFieldAsSelected(int x, int y, Field type) {
        Button field = (Button) fields.getChildren().get(y * Board.WIDTH + x);
        field.setText(type.getName());
        field.setDisable(true);
        field.setStyle(BUTTON_DISABLED_STYLE);
    }

}
