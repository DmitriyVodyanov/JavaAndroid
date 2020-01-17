package com.example.guessinggame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber;
    private Button buttonGuess;
    private TextView textClickGuess;
    private int theNumber;
    private int numberOfTries = 0;
//    private int range = 100;
//    private TextView labelRange;


    public void checkGuess() {


        String userGuessNumberText;
        String message = "";
        String messageTries;
        int userGuessNumber;
        userGuessNumberText = editTextNumber.getText().toString();

        try {

            userGuessNumber = Integer.parseInt(userGuessNumberText);
            numberOfTries++;
            if (userGuessNumber > 100) {
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
                messageTries = numberOfTries + " tries!";
                Toast.makeText(MainActivity.this, messageTries, Toast.LENGTH_LONG).show();
                newGame();
            }

        } catch (Exception e) {
            message = "Enter a whole number between 1 and 100";
        }
        finally {
            textClickGuess.setText(message);
            editTextNumber.requestFocus();
            editTextNumber.selectAll();

        }
    }

    public void newGame() {
        theNumber = (int)(Math.random() * 100 + 1);
//        labelRange.setText("Enter a number between 1 and 100");
//        editTextNumber.requestFocus();
//        editTextNumber.selectAll();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber = findViewById(R.id.editTextNumber);
        buttonGuess = findViewById(R.id.buttonGuess);
        textClickGuess = findViewById(R.id.textClickGuess);
//        labelRange = findViewById(R.id.textView2);

        newGame();

        buttonGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGuess();
            }
        });

        editTextNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                checkGuess();
                return true;
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

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
//                final CharSequence [] items = {"1 to 10", "1 to 100", "1 to 1000"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Select the Range: ");
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int item) {
//                        switch (item) {
//                            case 0:
//                                range = 10;
//                                newGame();
//                                break;
//                            case 1:
//                                range = 100;
//                                newGame();
//                                break;
//                            case 2:
//                                range = 1000;
//                                newGame();
//                                break;
//                        }
//                        dialogInterface.dismiss();
//                    }
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
                return true;
            case R.id.action_newgame:
                newGame();
                return true;
            case R.id.action_gamestats:
                return true;
            case R.id.action_about:
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("About Guessing Game");
                aboutDialog.setMessage("(c)2018 Your Name.");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                aboutDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
