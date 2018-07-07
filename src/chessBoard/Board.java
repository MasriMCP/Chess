package chessBoard;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import main.Controller;

public class Board extends GridPane implements BoardConstants{
    private Square[][] grid = new Square[8][8];
    private int player;
    private Square selected;
    private boolean selecting = false;
    private Square temp;
    public Board(int player){
        this.player=player;
        BackgroundImage bg= new BackgroundImage(new Image("chessBoard/Board.png",W,H,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);//board background image constant
        setBackground(new Background(bg));//setting the background
        for(int i = 0;i<8;i++){
            for (int j = 0; j < 8; j++) {
                if(player==WHITE)
                    add(grid[i][j]=new Square(i+1,j+1),i,9-j);//add the squares to the array and pane
                else
                    add(grid[i][j]=new Square(i+1,j+1),9-i,j);
                grid[i][j].setOnMouseClicked(e->{
                    if(!selecting){
                        selected = (Square)e.getSource();
                    }
                    else{
                        if((Square)e.getSource() != selected){
                            Controller.move(grid,selected,(Square)e.getSource());
                        }
                        selected = null;
                    }
                    selecting=!selecting;
                });

            }
        }
        Controller.initClassic(grid,player);
    }

}
