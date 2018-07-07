package chessBoard;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import main.Controller;

public class Board extends GridPane implements BoardConstants{
    private Square[][] grid = new Square[8][8];
    private int player;
    public Board(int player){
        this.player=player;
        BackgroundImage bg= new BackgroundImage(new Image("chessBoard/Board.png",W,H,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);//board background image constant
        setBackground(new Background(bg));//setting the background
        for(int i = 0;i<8;i++){
            for (int j = 0; j < 8; j++) {
                Square temp = grid[i][j];
                if(player==WHITE)
                    add(temp=new Square(i+1,j+1),i,9-j);//add the squares to the array and pane
                else
                    add(temp=new Square(i+1,j+1),9-i,j);
                temp.setOnMouseDragged(e->{

                });
                temp.setOnMouseReleased(e->{

                });
            }
        }
        Controller.initClassic(grid,player);
    }

}
