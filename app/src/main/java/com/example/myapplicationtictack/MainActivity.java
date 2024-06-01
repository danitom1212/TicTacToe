package com.example.myapplicationtictack;

import androidx.appcompat.app.AppCompatActivity;


        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    private final static String TAG = "DEBUG: ";
    private Button[][] buttons = new Button[3][3];//buttons of players
    private int doubleClick;//count for count the round and help to method
    private boolean player1Turn = true;//
    private int roundCount;//the round count brake an 9
    private int player1Points;
    private int player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doubleClick=0;//count start in 0
        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;//to now what button now name
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());//like $$ R.id $$ only cast to int
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetGame();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        ((Button) v).setText("X");//click set x in box
        if (doubleClick < 8) {//if we are pass the turns in game
            doubleClick = doubleClick + 2;
            ((Button) v).setEnabled(false);
            playWithPc();
        } else ((Button) v).setText("X");
        roundCount++;
        if (checkForWin()) {

            player2Wins();
        } else {
            player1Wins();
        }

        if (roundCount == 9) {
            draw();//if no winner then draw
        }
    }
    private boolean checkForWin() {
        String[][] gird = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gird[i][j] = buttons[i][j].getText().toString();//take the borde in game "all full of taxt bottnus"
            }
        }
        for (int i = 0; i < 3; i++) {
            if (gird[i][0].equals(gird[i][1]) && gird[i][0].equals(gird[i][2]) && !gird[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (gird[0][i].equals(gird[1][i]) && gird[0][i].equals(gird[2][i]) && !gird[0][i].equals("")) {
                return true;
            }
        }
        if (gird[0][0].equals(gird[1][1]) && gird[0][0].equals(gird[2][2]) && !gird[0][0].equals("")) {
            return true;
        }
        if (gird[0][2].equals(gird[1][1]) && gird[0][2].equals(gird[2][0]) && !gird[0][2].equals("")) {
            return true;
        }
        //else?
        return false;
    }
    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
    }
    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show();
        //resetBoard();
    }
    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                doubleClick=0;
            }
        }
        roundCount = 0;
        player1Turn = true;
    }
    //Picks a random number for cell in the array & fill random cells in the gameBoard
    private void playWithPc(){
        int rand1 = (int)(Math.random() * 3) ;//take a random number for cells in the array
        int rand2 = (int)(Math.random() * 3) ;
        //Check if the cell not selected
        if (buttons[rand1][rand2].isEnabled() == true) {
            buttons[rand1][rand2].setText("O");//fill the button with 'O'
            buttons[rand1][rand2].setEnabled(false);//Blocked the button
        }
        else playWithPc();//If selected run the method again
    }
    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();//update the point and then rest the borde
        resetBoard();
    }
}
