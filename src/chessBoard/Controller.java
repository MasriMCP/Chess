package chessBoard;


import javafx.scene.image.ImageView;
import pieces.Piece;
import pieces.PieceConstants;

import java.util.ArrayList;

public class Controller implements PieceConstants{
    public static void initClassic(Square[][] grid,int color){
        //puts all the pieces in classical format
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
        //translates between array coordinates and chess board
        int file = name.toCharArray()[0]-96;
        int rank = name.toCharArray()[1]-48;
            grid[file-1][rank-1].setGraphic(new ImageView(p.getImg()));
            grid[file-1][rank-1].setPiece(p);

    }

    public static boolean move(Square[][] grid,Square s0,Square s1){
        Piece p = s0.getPiece();
        if(p==null){return false;}
        if(isLegal(grid,s0,s1)){
            p.move();
            s1.setGraphic(new ImageView(s0.getPiece().getImg()));
            s1.setPiece(s0.getPiece());
            s0.setGraphic(null);
            s0.setPiece(null);
            return true;
        }
    return false;
    }
    public static boolean move(Square[][] grid,int f0,int r0,int f1,int r1){
        return move(grid,grid[f0-1][r0-1],grid[f1-1][r1-1]);
    }
    private static boolean isLegal(Square[][] grid,Square s0,Square s1){
        Piece p = s0.getPiece();
        if(p.getType()==KING){
            boolean movement = (Math.abs(s1.getRank()-s0.getRank())<=1)&&(Math.abs(s1.getFile()-s0.getFile())<=1);
            return movement&& notOccupied(s1,p.getColor());
        }
        else if (p.getType()==QUEEN){
            boolean movement = (Math.abs(s1.getRank()-s0.getRank())>0)^(Math.abs(s1.getFile()-s0.getFile())>0)
                                || Math.abs(s1.getRank()-s0.getRank())==Math.abs(s1.getFile()-s0.getFile());
            return movement&& notOccupied(s1,p.getColor())&&notBlocked(grid,s0,s1);
        }
        else if (p.getType()==PieceConstants.ROOK){
            boolean movement = (Math.abs(s1.getRank()-s0.getRank())>0)^(Math.abs(s1.getFile()-s0.getFile())>0);
            return movement&& notOccupied(s1,p.getColor())&&notBlocked(grid,s0,s1);
        }
        else if (p.getType()==BISHOP){
            boolean movement = Math.abs(s1.getRank()-s0.getRank())==Math.abs(s1.getFile()-s0.getFile());
            return movement&& notOccupied(s1,p.getColor())&&notBlocked(grid,s0,s1);
        }
        else if (p.getType()==KNIGHT){
            boolean movement = (Math.abs(s1.getRank()-s0.getRank())==2)&&(Math.abs(s1.getFile()-s0.getFile())==1)
                                ||(Math.abs(s1.getRank()-s0.getRank())==1)&&(Math.abs(s1.getFile()-s0.getFile())==2);
            return movement&& notOccupied(s1,p.getColor());
        }
        else if (p.getType()==PAWN){
            boolean movement;
            if(p.getColor()==WHITE){
                movement = (!p.hasMoved()&&(s1.getRank()-s0.getRank()==1||s1.getRank()-s0.getRank()==2))||(s1.getRank()-s0.getRank()==1);
            }
            else{
                movement = (!p.hasMoved()&&(s1.getRank()-s0.getRank()==-1||s1.getRank()-s0.getRank()==-2))||(s1.getRank()-s0.getRank()==-1);
            }
            movement&= s1.getFile()==s0.getFile();
            return movement&& notOccupied(s1,p.getColor());
        }
       return false;
    }
    public static Square[] getLegalSquares(Square[][] grid,Square s){
        ArrayList<Square> list = new ArrayList<>();
        for(int i = 0;i<8;i++){
            for (int j = 0; j < 8; j++) {
                if(isLegal(grid,s,grid[i][j])){
                    list.add(grid[i][j]);
                }
            }
        }
        return (Square[])list.toArray();
    }
    private static boolean notBlocked(Square[][] grid,Square s0,Square s1){
        Piece p = s0.getPiece();
        int file0 = s0.getFile()-96;
        int rank0 = s0.getRank();
        int file1 = s1.getFile()-96;
        int rank1 = s1.getRank();

        System.out.println(rank0+" "+rank1);

        System.out.println(file0+" "+file1);
        if (p.getType()==QUEEN){
            if(file0<file1&&rank0<rank1){
                for(int i=file0+1,j=rank0+1;i<file1;i++,j++){
                    if(grid[i-1][j-1].getPiece()!=null){return false;}
                }
                return true;
            }
            else if(file1<file0&&rank0<rank1){
                for(int i=file1+1,j=rank0+1;i<file0;i++,j++){
                    if(grid[i-1][j-1].getPiece()!=null){return false;}
                }
                return true;
            }
            else if(file0<file1&&rank1<rank0){
                for(int i=file0+1,j=rank1+1;i<file1;i++,j++){
                    if(grid[i-1][j-1].getPiece()!=null){return false;}
                }
                return true;
            }
            else if(file1<file0&&rank1<rank0){
                for(int i=file1+1,j=rank1+1;i<file0;i++,j++){
                    if(grid[i-1][j-1].getPiece()!=null){return false;}
                }
                return true;
            }
            else if(file0<file1){
                for (int i = file0+1; i < file1; i++) {
                    if(grid[i-1][rank0-1].getPiece()!=null){return false;}
                }
                return true;
            }
            else if(file1<file0){
                for (int i = file1+1; i < file0; i++) {
                    if(grid[i-1][rank0-1].getPiece()!=null){return false;}
                }
                return true;
            }

            else if(rank0<rank1){

                for (int i = rank0+1; i < rank1; i++) {
                    if(grid[file0-1][i-1].getPiece()!=null){return false;}
                }
                return true;
            }
            else if(rank1<rank0){
                for (int i = rank1+1; i < rank0; i++) {
                    if(grid[file0-1][i-1].getPiece()!=null){return false;}
                }
                return true;

            }

        }
        else if (p.getType()==ROOK){
                if(file0<file1){
                    for (int i = file0+1; i < file1; i++) {
                        if(grid[i-1][rank0-1].getPiece()!=null){return false;}
                    }
                    return true;
                }
                else if(file1<file0){
                    for (int i = file1+1; i < file0; i++) {
                        if(grid[i-1][rank0-1].getPiece()!=null){return false;}
                    }
                    return true;
                }

                else if(rank0<rank1){

                    for (int i = rank0+1; i < rank1; i++) {
                        if(grid[file0-1][i-1].getPiece()!=null){return false;}
                    }
                    return true;
                }
                else if(rank1<rank0){
                    for (int i = rank1+1; i < rank0; i++) {
                        if(grid[file0-1][i-1].getPiece()!=null){return false;}
                    }
                    return true;

            }
        }
        else if (p.getType()==PieceConstants.BISHOP){
            if(file0<file1&&rank0<rank1){
                for(int i=file0+1,j=rank0+1;i<file1;i++,j++){
                    if(grid[i-1][j-1].getPiece()!=null){return false;}
                }
                return true;
            }
            else if(file1<file0&&rank0<rank1){
                for(int i=file1+1,j=rank0+1;i<file0;i++,j++){
                    if(grid[i-1][j-1].getPiece()!=null){return false;}
                }
                return true;
            }
            else if(file0<file1&&rank1<rank0){
                for(int i=file0+1,j=rank1+1;i<file1;i++,j++){
                    if(grid[i-1][j-1].getPiece()!=null){return false;}
                }
                return true;
            }
            else if(file1<file0&&rank1<rank0){
                for(int i=file1+1,j=rank1+1;i<file0;i++,j++){
                    if(grid[i-1][j-1].getPiece()!=null){return false;}
                }
                return true;
            }
        }
        else if (p.getType()==PieceConstants.PAWN){

        }
        return false;
    }
    private static boolean notOccupied(Square s1, int color){
        return s1.getPiece()==null||s1.getPiece().getColor()!=color;
    }
    public static boolean hasWon(int color){
        return false;//TODO this thing
    }
    public static boolean isDraw(){
        return false; //TODO this too
    }
}
