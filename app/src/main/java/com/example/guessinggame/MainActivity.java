package com.example.guessinggame;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber;
    private Button buttonGuess;
    private TextView textClickGuess;
    private int theNumber;


    public void checkGuess() {

        String userGuessNumberText;
        String message = "";
        int userGuessNumber;
        userGuessNumberText = editTextNumber.getText().toString();

        try {

            userGuessNumber = Integer.parseInt(userGuessNumberText);

            if (userGuessNumber > 100 && userGuessNumber < 0 ) {
                message = userGuessNumber + " Out of range. Try again.";
            }

            if (userGuessNumber < theNumber) {
                message = userGuessNumber + " is too low. Try again.";
            }

            if (userGuessNumber > theNumber) {
                message = userGuessNumber + " is too high. Try again.";
            }

            if (userGuessNumber == theNumber) {
                message = userGuessNumber + " is correct. You win! Let's play again!";
                newGame();
            }

        } catch (Exception e) {
            message = "Enter a whole number between 1 and 100.";
        }
        finally {
            textClickGuess.setText(message);
            editTextNumber.requestFocus();
            editTextNumber.selectAll();

        }
    }

    public void newGame() {
        theNumber = (int)(Math.random() * 100 + 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber = findViewById(R.id.editTextNumber);
        buttonGuess = findViewById(R.id.buttonGuess);
        textClickGuess = findViewById(R.id.textClickGuess);

        newGame();

        buttonGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGuess();

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
