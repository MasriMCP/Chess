package main;

import chessBoard.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import pieces.PieceConstants;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server extends Application implements PieceConstants {
    private int sessionNo = 1;
    @Override
    public void start(Stage primaryStage) throws Exception {
        TextArea log = new TextArea();
        primaryStage.setTitle("chess server");
        primaryStage.setScene(new Scene(new ScrollPane(log),400,250));
        primaryStage.show();
        new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(()->log.appendText(new Date()+": server started at port 8000\n"));
                while(true){
                    Socket p1 = serverSocket.accept(); // socket to represent the connection with player 1
                    Platform.runLater(()->{
                        log.appendText("player 1 connected with IP: "+p1.getInetAddress().getHostAddress()+"\n");
                    });
                    new DataOutputStream(p1.getOutputStream()).writeInt(WHITE);//send a message to the player that the connection was established
                    Socket p2 = serverSocket.accept();
                    Platform.runLater(()->{
                        log.appendText("player 2 connected with IP: "+p2.getInetAddress().getHostAddress()+"\n");

                    });
                    new DataOutputStream(p2.getOutputStream()).writeInt(BLACK);
                    Platform.runLater(()->{
                        log.appendText("game at session "+(sessionNo++)+" started");
                    });
                    new Thread(new HandelSession(p1,p2)).start();
                }
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }).start();
    }
    private class HandelSession implements Runnable{
        private Socket p1,p2;
        HandelSession(Socket p1,Socket p2){
            this.p1=p1;this.p2=p2;
        }

        @Override
        public void run() {
            try{
                DataInputStream fromP1 = new DataInputStream(p1.getInputStream());
                DataInputStream fromP2 = new DataInputStream(p2.getInputStream());
                DataOutputStream toP1 = new DataOutputStream(p1.getOutputStream());
                DataOutputStream toP2 = new DataOutputStream(p2.getOutputStream());
                toP1.writeInt(1);//alert player 1 that the game started
                while(p1.isConnected()&&p2.isConnected()){
                    int file0 = fromP1.readInt();
                    int rank0 = fromP1.readInt();
                    int file1 = fromP1.readInt();
                    int rank1 = fromP1.readInt();
                    if(Controller.hasWon(WHITE)){
                        break;
                    }
                    else if(Controller.isDraw()){
                        break;
                    }
                    else{
                        toP2.writeInt(CONTINUE);

                    }
                    sendMove(toP2,file0,rank0,file1,rank1);
                    file0 = fromP2.readInt();
                    rank0 = fromP2.readInt();
                    file1 = fromP2.readInt();
                    rank1 = fromP2.readInt();
                    if(Controller.hasWon(BLACK)){
                        break;
                    }
                    else if(Controller.isDraw()){
                        break;
                    }
                    else{
                        toP1.writeInt(CONTINUE);
                    }
                    sendMove(toP1,file0,rank0,file1,rank1);
                }

            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
        private void sendMove(DataOutputStream out,int file0,int rank0,int file1,int rank1)throws IOException{
            out.writeInt(file0);
            out.writeInt(rank0);
            out.writeInt(file1);
            out.writeInt(rank1);
        }
    }
}
