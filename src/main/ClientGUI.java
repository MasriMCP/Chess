package main;

import chessBoard.Board;
import chessBoard.BoardConstants;
import chessBoard.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pieces.PieceConstants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ClientGUI extends Application implements PieceConstants {
    int color;
    private Label title = new Label();
    private Label status = new Label("not connected");
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private boolean continuePlay = true;

    private String host = "localhost";
    private Board b;
    @Override
    public void start(Stage primaryStage) throws Exception {
         b = new Board();
        BorderPane pane = new BorderPane();
        pane.setTop(title);
        pane.setCenter(b);
        pane.setBottom(status);
        Scene s = new Scene(pane,1200,1200);
        primaryStage.setScene(s);
        primaryStage.setTitle("Chess");
        primaryStage.show();
        connect();
    }
    private void connect(){
        try{
            Socket socket = new Socket(host,8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        new Thread(()->{
            try {
                color = fromServer.readInt();
                if(color==WHITE){
                Platform.runLater(()->{
                    b.init(color);
                    title.setText("you play white");
                    status.setText("waiting for player 2 to join");});
                    fromServer.readInt();//wait for player 2
                    Platform.runLater(()->status.setText("your turn"));
                }
                if(color==BLACK){
                    Platform.runLater(()->{
                        b.init(color);
                        title.setText("you play black");
                        status.setText("waiting for player 1 to make a move");});
                }
                while(continuePlay){
                    if(color==WHITE){
                        waitForAction();
                        sendMove();
                        b.waiting=true;
                        receiveAction();
                    }
                    else{
                        receiveAction();
                        waitForAction();
                        sendMove();
                        b.waiting=true;
                    }
                }
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }

        }).start();
    }
    private void waitForAction() throws InterruptedException{
        while(b.waiting){
            Thread.sleep(100);
        }

    }
    private void sendMove() throws IOException{
        int[] move = b.getMove();
        toServer.writeInt(move[0]);
        toServer.writeInt(move[1]);
        toServer.writeInt(move[2]);
        toServer.writeInt(move[3]);
    }
    private void receiveAction()throws IOException{
        int s = fromServer.readInt();
        if(s==WIN_WHITE){
            continuePlay = false;
        }
        else if (s==WIN_BLACk){
            continuePlay=false;
        }
        else if(s==DRAW){
            continuePlay=false;
        }
        else{
            receiveMove();
            Platform.runLater(()->status.setText("your turn"));
            b.myTurn = true;
        }
    }
    private void receiveMove() throws IOException{
        b.setMove(fromServer.readInt(),fromServer.readInt(),
                fromServer.readInt(),fromServer.readInt());

        Platform.runLater(()->{
            int[] move = b.getMove();
            Arrays.toString(move);
            Controller.move(b.getGrid(),move[0],move[1],move[2],move[3]);
            status.setText("other player's turn");
        });
    }
}
