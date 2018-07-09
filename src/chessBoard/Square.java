package chessBoard;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import pieces.Piece;

public class Square extends Label implements BoardConstants{
    private int file,rank;
    private Piece piece;
    public Square(int file, int rank) {
        this.file = file;
        this.rank = rank;
        setPrefSize(W/8,H/8);
        setBG("chessBoard/null.png");

    }

    public char getFile() {
        return (char)(file+96);
    }

    public int getRank() {
        return rank;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public String toString(){
        return getFile()+""+rank+": "+(piece==null?"empty":piece.toString());
    }
    public void setBG(String s){
        BackgroundImage bg= new BackgroundImage(new Image(s,W/8,H/8,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);//an empty image because this is the only solution i found
        setBackground(new Background(bg));
    }
}
