package main;

import chessBoard.Board;
import chessBoard.BoardConstants;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Board b = new Board(BoardConstants.WHITE);
        Scene s = new Scene(b,1200,1200);
        primaryStage.setScene(s);
        primaryStage.show();
    }
}
