package hu.ait.android.tictactoe.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

import hu.ait.android.tictactoe.MainActivity;
import hu.ait.android.tictactoe.R;
import hu.ait.android.tictactoe.model.TicTacToeModel;


//only Views can be put on layouts and can be drawn on, so we contruct a
// subclass that will override the super methods

public class TicTacToeView extends View {

    //will be used to paint black box on canvas
    private Paint paintBackground;
    //will be used to paint line of black box
    private Paint paintLine;
    private Paint paintFont;


    private PointF tmpPlayer = null;

    private Bitmap bitmapBg = null;



    //opt + ret and select option with 2 parameters
    public TicTacToeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paintBackground = new Paint();
        paintBackground.setColor(Color.BLACK);
        paintBackground.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);


        paintFont = new Paint();
        paintFont.setColor(Color.WHITE);
        paintFont.setTextSize(60);

        bitmapBg = BitmapFactory.decodeResource(getResources(), R.drawable.pretty);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        paintFont.setTextSize(getHeight()/3);
        bitmapBg = Bitmap.createScaledBitmap(bitmapBg, getWidth(), getHeight(), false);

    }

    /*
            Need to override to specify what we want to draw on the layout.
            @canvas we can draw on this canvas with a paint object
         */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0,0, getWidth(),getHeight(), paintBackground);
        canvas.drawBitmap(bitmapBg, 0, 0, null);
        drawGameArea(canvas);
        drawPlayers(canvas);
        drawTmpPlayer(canvas);
        TextView endgame = findViewById(R.id.endgame);

        if(checkForWinner(canvas) == TicTacToeModel.CROSS){
            //endgame.setVisibility(VISIBLE);
            //endgame.setText("PLAYER 'X' WINS!");
        } else if(checkForWinner(canvas) == TicTacToeModel.CIRCLE){
            //endgame.setVisibility(VISIBLE);
            //endgame.setText("PLAYER 'O' WINS!");
        }
        //canvas.drawText("5", 80,250, paintFont);


    }

    private short checkForWinner(Canvas canvas) {


        if(TicTacToeModel.getInstance().win(TicTacToeModel.CROSS)){
            //canvas.drawText("5", 80,250, paintFont);

            clearBoard();
            return TicTacToeModel.CROSS;

        }else if(TicTacToeModel.getInstance().win(TicTacToeModel.CIRCLE)){
            //canvas.drawText("5", 80,250, paintFont);

            clearBoard();
            return TicTacToeModel.CIRCLE;
        }
        return TicTacToeModel.EMPTY;

    }

    private void drawTmpPlayer(Canvas canvas) {
        if (tmpPlayer != null) {
            if (TicTacToeModel.getInstance().getNextPlayer()
                    == TicTacToeModel.CIRCLE) {
                canvas.drawCircle(tmpPlayer.x, tmpPlayer.y, getHeight() / 6 - 2,
                        TicTacToeModel.getInstance().circleColor());
            } else {
                canvas.drawLine(tmpPlayer.x - getWidth() / 6,
                        tmpPlayer.y - getHeight() / 6,
                        tmpPlayer.x + getWidth() / 6,
                        tmpPlayer.y + getHeight() / 6, TicTacToeModel.getInstance().crossColor());

                canvas.drawLine(tmpPlayer.x - getWidth() / 6,
                        tmpPlayer.y + getHeight() / 6,
                        tmpPlayer.x + getWidth() / 6,
                        tmpPlayer.y - getHeight() / 6, TicTacToeModel.getInstance().crossColor());
            }
        }
    }

    private void drawPlayers(Canvas canvas) {
        for (short i = 0; i < 3; i++) {
            for (short j = 0; j < 3; j++) {
                if (TicTacToeModel.getInstance().getFieldContent(i,j) == TicTacToeModel.CIRCLE) {

                    // draw a circle at the center of the field

                    // X coordinate: left side of the square + half width of the square
                    float centerX = i * getWidth() / 3 + getWidth() / 6;
                    float centerY = j * getHeight() / 3 + getHeight() / 6;
                    int radius = getHeight() / 6 - 2;

                    canvas.drawCircle(centerX, centerY, radius, TicTacToeModel.getInstance().circleColor());

                } else if (TicTacToeModel.getInstance().getFieldContent(i,j) == TicTacToeModel.CROSS) {
                    canvas.drawLine(i * getWidth() / 3, j * getHeight() / 3,
                            (i + 1) * getWidth() / 3,
                            (j + 1) * getHeight() / 3, TicTacToeModel.getInstance().crossColor());

                    canvas.drawLine((i + 1) * getWidth() / 3, j * getHeight() / 3,
                            i * getWidth() / 3, (j + 1) * getHeight() / 3,
                            TicTacToeModel.getInstance().crossColor());
                }
            }
        }
    }

    private void drawGameArea(Canvas canvas) {

        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);


        canvas.drawLine(0, getHeight() / 3, getWidth(), getHeight() / 3,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 3, getWidth(),
                2 * getHeight() / 3, paintLine);

        canvas.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 3, 0, 2 * getWidth() / 3, getHeight(),
                paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction() == MotionEvent.ACTION_MOVE){
            tmpPlayer = new PointF(event.getX(), event.getY());
            invalidate();

        }else if (event.getAction() == MotionEvent.ACTION_UP) {
            tmpPlayer = null;

            int tX = ( (int) event.getX() / (getWidth() /3));
            int tY = ( (int) event.getY() / (getWidth() /3));

            if(TicTacToeModel.getInstance().getFieldContent((short) tX, (short) tY)
                    == TicTacToeModel.EMPTY){
                TicTacToeModel.getInstance().setFieldContent((short) tX, (short) tY,
                        TicTacToeModel.getInstance().getNextPlayer());
                TicTacToeModel.getInstance().changeNextPlayer();


                ((MainActivity)getContext()).showMessage(getContext().getString(R.string.text_next));
            }

            invalidate();
            MainActivity.count = 0;


        }
        return true;
    }


    public void clearBoard() {
        TicTacToeModel.getInstance().resetGame();
        invalidate();
        MainActivity.count = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }
}
