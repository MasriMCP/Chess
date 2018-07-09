package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Server().start(new Stage());
        new ClientGUI().start(new Stage());
        new ClientGUI().start(new Stage());
    }
}
