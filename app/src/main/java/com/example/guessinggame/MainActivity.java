package com.example.guessinggame;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private int range = 100;
    private final int RANGEONE = 10;
    private final int RANGETWO = 100;
    private final int RANGETHREE = 1000;
    private TextView labelRange;


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
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                int gamesWon = preferences.getInt("gamesWon", 0) + 1;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("gamesWon", gamesWon);
                editor.apply();
                newGame();
            }

        } catch (Exception e) {
            message = "Enter a whole number between 1 and " + range + ".";
        }
        finally {
            textClickGuess.setText(message);
            editTextNumber.requestFocus();
            editTextNumber.selectAll();

        }
    }

    public void newGame() {
        theNumber = (int)(Math.random() * range + 1);
        labelRange.setText("Enter a number between 1 and " + range + ".");
        textClickGuess.setText(R.string.enter_a_number_then_click_guess);
        editTextNumber.setText("");
//        editTextNumber.selectAll();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNumber = findViewById(R.id.editTextNumber);
        buttonGuess = findViewById(R.id.buttonGuess);
        textClickGuess = findViewById(R.id.textClickGuess);
        labelRange = findViewById(R.id.textEnterNumber);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        range = preferences.getInt("range", range);
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
                final CharSequence [] items = {"1 to 10", "1 to 100", "1 to 1000"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.setting_game_title)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogSettingsRange, int item) {
                                switch (item) {
                                    case 0:
                                        range = RANGEONE;
                                        storageRange(range);
                                        newGame();
                                        break;
                                    case 1:
                                        range = RANGETWO;
                                        storageRange(range);
                                        newGame();
                                        break;
                                    case 2:
                                        range = RANGETHREE;
                                        storageRange(range);
                                        newGame();
                                        break;
                                }
                                dialogSettingsRange.dismiss();
                            }
                        });
                AlertDialog settingGameDialog = builder.create();
                settingGameDialog.show();
                return true;
            case R.id.action_newgame:
                newGame();
                return true;
            case R.id.action_gamestats:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                int gamesWon = preferences.getInt("gamesWon", 0);
                AlertDialog.Builder statisticDialog = new AlertDialog.Builder(MainActivity.this);
                statisticDialog.setTitle(R.string.statistic_title)
                        .setMessage("You have won " + gamesWon + " games. Way to go!")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
                statisticDialog.show();
                return true;
            case R.id.action_about:
                AlertDialog.Builder aboutDialog = new AlertDialog.Builder(MainActivity.this);
                aboutDialog.setTitle(R.string.about_title)
                        .setMessage(R.string.about_message)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
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

    public void storageRange(int newRange) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("range", newRange);
        editor.apply();
    }
}
