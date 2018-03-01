package hu.ait.android.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Timer;

import hu.ait.android.tictactoe.ui.TicTacToeView;

public class MainActivity extends AppCompatActivity {

    public static int count = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClear = findViewById(R.id.btnClear);
        final TicTacToeView ticTacToeView = findViewById(R.id.ticTacToe);

        final TextView endgame = findViewById(R.id.endgame);
        endgame.setVisibility(View.INVISIBLE);


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticTacToeView.clearBoard();

            }
        });

        final TextView timerText = findViewById(R.id.timerText);

        updateElapsedTime(timerText);

    }

    private void updateElapsedTime(final TextView timerText) {
        Thread time = new Thread(){
          @Override
            public void run(){
              try{
                  while (!isInterrupted()){
                      Thread.sleep(1000);
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              count++;
                              String newText = getResources().getString(R.string.elapsed_time) + count;
                              timerText.setText(newText);

                          }
                      });
                  }
              } catch (Exception e){

              }
          }
        };

        time.start();
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
