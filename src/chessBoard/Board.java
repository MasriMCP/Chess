package chessBoard;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Arrays;

public class Board extends GridPane implements BoardConstants {
    private Square[][] grid = new Square[8][8];
    private int[] move = new int[4];//array stores the last move played by either player
    private int color;
    private Square selected;
    private boolean selecting = false;
    private Square temp;
    public boolean myTurn=true, waiting=true;
    public Board() {
        setPrefSize(W, H);
        BackgroundImage bg = new BackgroundImage(new Image("chessBoard/Board.png", W, H, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);//board background image constant
        setBackground(new Background(bg));//setting the background

    }

    public void init(int color) {
        if(color==BLACK) myTurn = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (color == WHITE)
                    add(grid[i][j] = new Square(i + 1, j + 1), i, 9 - j);//add the squares to the array and pane
                else
                    add(grid[i][j] = new Square(i + 1, j + 1), 9 - i, j);
                grid[i][j].setOnMouseClicked(e -> {
                    if(myTurn)
                    if (!selecting&&((Square) e.getSource()).getPiece()!=null&&((Square) e.getSource()).getPiece().getColor()==color) {
                        selected = (Square) e.getSource();
                        ((Square) e.getSource()).setBG("chessBoard/selected.png");
                    } else {
                        if ((Square) e.getSource() != selected) {
                            if(Controller.move(grid, selected, (Square) e.getSource())) {
                                setMove(selected.getFile() - 96,
                                        selected.getRank(),
                                        ((Square) e.getSource()).getFile() - 96,
                                        ((Square) e.getSource()).getRank());
                                //System.out.println(Arrays.toString(move));
                                waiting = false;
                                myTurn = false;
                            }
                        }
                        selected.setBG("chessBoard/null.png");
                        selected = null;

                    }
                    selecting = !selecting;
                });

            }
        }
        Controller.initClassic(grid, color);

    }

    public int[] getMove() {
        return move;
    }

    public void setMove(int f0, int r0, int f1, int r1) {
        move[0] = f0;
        move[1] = r0;
        move[2] = f1;
        move[3] = r1;
    }
    public Square[][] getGrid(){
        return grid;
    }
}
