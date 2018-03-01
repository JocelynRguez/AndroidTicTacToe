package hu.ait.android.tictactoe.model;


import android.graphics.Color;
import android.graphics.Paint;

public class TicTacToeModel {


    private static TicTacToeModel instance = null;

    public static TicTacToeModel getInstance() {
        if (instance == null){
            instance = new TicTacToeModel();
        }
        return instance;
    }

    private TicTacToeModel(){

    }

    public static final short EMPTY = 0;
    public static final short CROSS = 1;
    public static final short CIRCLE = 2;

    private short[][] model = {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };



    private short nextPlayer = CIRCLE;

    public void setFieldContent(short x, short y, short player){
        model[x][y] = player;

    }

    public short getFieldContent(short x, short y){
        return model[x][y];
    }

    public short getNextPlayer() {
        return nextPlayer;
    }

    public void changeNextPlayer() {

//        nextPlayer = (nextPlayer == CIRCLE) ? CROSS : CIRCLE;

        if(nextPlayer == CIRCLE){
            nextPlayer = CROSS;
        } else {
            nextPlayer = CIRCLE;
        }
    }

    public Paint crossColor(){
        Paint crossPaint = new Paint();
        crossPaint.setColor(Color.MAGENTA);
        crossPaint.setStyle(Paint.Style.STROKE);
        crossPaint.setStrokeWidth(8);

        return crossPaint;
    }

    public Paint circleColor(){
        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.CYAN);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(8);

        return circlePaint;
    }


    public boolean win(short player){

        if (diagonalWin(player)) return true;
        if (linearWin(player)) return true;

        return false;
    }

    private boolean linearWin(short player) {
        for(int row = 0; row < 3;row++ ){
            if(model[row][0] == model[row][1] && model[row][1] == model[row][2] && model[row][0] == player){
                return true;
            }
        }

        for(int col = 0; col < 3;col++ ){
            if(model[0][col] == model[1][col]
                    && model[1][col] == model[2][col]
                    && model[0][col] == player){
                return true;
            }
        }
        return false;
    }

    private boolean diagonalWin(short player) {
        boolean leftDiagonal = (model[0][0] == model[1][1]) && (model[1][1] == model[2][2]) &&
                (model[0][0] == player);
        boolean rightDiagonal = (model[0][2] == model[1][1]) && (model[1][1] == model[2][0]) &&
                (model[0][2] == player);
        //check rows for diagonal win
        if(leftDiagonal || rightDiagonal){
            return true;
        }
        return false;
    }


    public short[][] gameState(){
        return model;
    }

    public void resetGame() {
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3; j++) {
                model[i][j] = EMPTY;
            }

        }
        nextPlayer = CIRCLE;
    }
}
