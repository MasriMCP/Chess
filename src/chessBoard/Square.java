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
        BackgroundImage bg= new BackgroundImage(new Image("chessBoard/null.png",W/8,H/8,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);//an empty image because this is the only solution i found
        setBackground(new Background(bg));
        setOnMouseClicked(e->{
            System.out.println(this+", "+piece);
        });
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
        return getFile()+""+rank;
    }
}
