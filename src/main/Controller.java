package main;

import chessBoard.Square;
import javafx.scene.image.ImageView;
import pieces.Piece;
import pieces.PieceConstants;

public class Controller implements PieceConstants{
    public static void initClassic(Square[][] grid,int color){
        if(grid.length!=8||grid[0].length!=8){
            throw new IllegalArgumentException();
        }
        setSquare(grid, "a1", new Piece(ROOK,WHITE),color);  setSquare(grid, "a2", new Piece(PAWN,WHITE),color);
        setSquare(grid, "b1", new Piece(KNIGHT,WHITE),color);setSquare(grid, "b2", new Piece(PAWN,WHITE),color);
        setSquare(grid, "c1", new Piece(BISHOP,WHITE),color);setSquare(grid, "c2", new Piece(PAWN,WHITE),color);
        setSquare(grid, "d1", new Piece(QUEEN,WHITE),color); setSquare(grid, "d2", new Piece(PAWN,WHITE),color);
        setSquare(grid, "e1", new Piece(KING,WHITE),color);  setSquare(grid, "e2", new Piece(PAWN,WHITE),color);
        setSquare(grid, "f1", new Piece(BISHOP,WHITE),color);setSquare(grid, "f2", new Piece(PAWN,WHITE),color);
        setSquare(grid, "g1", new Piece(KNIGHT,WHITE),color);setSquare(grid, "g2", new Piece(PAWN,WHITE),color);
        setSquare(grid, "h1", new Piece(ROOK,WHITE),color);  setSquare(grid, "h2", new Piece(PAWN,WHITE),color);

        setSquare(grid, "a8", new Piece(ROOK,BLACK),color);  setSquare(grid, "a7", new Piece(PAWN,BLACK),color);
        setSquare(grid, "b8", new Piece(KNIGHT,BLACK),color);setSquare(grid, "b7", new Piece(PAWN,BLACK),color);
        setSquare(grid, "c8", new Piece(BISHOP,BLACK),color);setSquare(grid, "c7", new Piece(PAWN,BLACK),color);
        setSquare(grid, "d8", new Piece(QUEEN,BLACK),color); setSquare(grid, "d7", new Piece(PAWN,BLACK),color);
        setSquare(grid, "e8", new Piece(KING,BLACK),color);  setSquare(grid, "e7", new Piece(PAWN,BLACK),color);
        setSquare(grid, "f8", new Piece(BISHOP,BLACK),color);setSquare(grid, "f7", new Piece(PAWN,BLACK),color);
        setSquare(grid, "g8", new Piece(KNIGHT,BLACK),color);setSquare(grid, "g7", new Piece(PAWN,BLACK),color);
        setSquare(grid, "h8", new Piece(ROOK,BLACK),color);  setSquare(grid, "h7", new Piece(PAWN,BLACK),color);
    }
    public static void setSquare(Square[][] grid,String name, Piece p,int color){
        int file = name.toCharArray()[0]-96;
        int rank = name.toCharArray()[1]-48;
        if(color==WHITE){grid[file-1][rank-1].setPiece(p);
            grid[file-1][rank-1].setGraphic(new ImageView(p.getImg()));}
        else{grid[7-(file-1)][7-(rank-1)].setPiece(p);
            grid[7-(file-1)][7-(rank-1)].setGraphic(new ImageView(p.getImg()));}

    }

    public static boolean move(Piece p, Square s0,Square s1){
        if(isLegal(p,s0,s1)){
            s1.setGraphic(new ImageView(s0.getPiece().getImg()));
            s1.setPiece(s0.getPiece());
            s0.setGraphic(null);
            s0.setPiece(null);
        }
    return false;
    }
    private static boolean isLegal(Piece p, Square s0,Square s1){
       /* if(p.getType()==PieceConstants.KING){

        }
        else if (p.getType()==PieceConstants.QUEEN){

        }
        else if (p.getType()==PieceConstants.ROOK){

        }
        else if (p.getType()==PieceConstants.BISHOP){

        }
        else if (p.getType()==PieceConstants.KNIGHT){

        }
        else if (p.getType()==PieceConstants.KING){

        }
        else if (p.getType()==PieceConstants.PAWN){

        }*/
       return true;
    }

}
