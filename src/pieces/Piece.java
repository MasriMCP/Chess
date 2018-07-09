package pieces;

import javafx.scene.image.Image;

public class Piece implements PieceConstants {
    private Image img;
    private int type,color;
    private boolean moved=false;
    public Piece(int type,int color){
        this.type=type;
        this.color = color;
        switch (type){
            case KING:
                img = (color==WHITE?new Image("pieces/whiteKing.png"):new Image("pieces/blackKing.png"));
                break;
            case QUEEN:
                img = (color==WHITE?new Image("pieces/whiteQueen.png"):new Image("pieces/blackQueen.png"));
                break;
            case ROOK:
                img = (color==WHITE?new Image("pieces/whiteRook.png"):new Image("pieces/blackRook.png"));
                break;
            case BISHOP:
                img = (color==WHITE?new Image("pieces/whiteBishop.png"):new Image("pieces/blackBishop.png"));
                break;
            case KNIGHT:
                img = (color==WHITE?new Image("pieces/whiteKnight.png"):new Image("pieces/blackKnight.png"));
                break;
            case PAWN:
                img = (color==WHITE?new Image("pieces/whitePawn.png"):new Image("pieces/blackPawn.png"));
                break;
            default:
                throw new IllegalArgumentException("piece constant not defined");

        }
    }

    public Image getImg() {
        return img;
    }

    public int getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public boolean hasMoved() {
        return moved;
    }

    public void move() {
        this.moved =true;
    }

    public String toString(){
        String c = color==WHITE?"white ":"black ";
        String t;
        switch (type){
            case KING:
                t="King";
                break;
            case QUEEN:
                t="Queen";
                break;
            case ROOK:
                t="Rook";
                break;
            case BISHOP:
                t="Bishop";
                break;
            case KNIGHT:
                t="Knight";
                break;
            case PAWN:
                t="Pawn";
                break;
            default:
                t="null";

        }
        return c+t;
    }

}
