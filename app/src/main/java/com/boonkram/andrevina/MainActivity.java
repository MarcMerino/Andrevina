package com.boonkram.andrevina;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.SeekBar.*;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Record> playerRank = new ArrayList<>();
    private int n = (int) (Math.random() * 100 + 1);
    private boolean win = false;
    private String name;
    private int c = 0;

    private int min = 0;
    private int max = 100;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText t = findViewById(R.id.editTextNumber);
        final TextView attempts = findViewById(R.id.attempts); attempts.setText(("Attempts: " + c));
        final SeekBar seekBar = findViewById(R.id.seekBarNumber);
        final Button b = findViewById(R.id.button);
        final Button r = findViewById(R.id.rankingActivityOpener);

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                t.setText(String.valueOf(progress));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        t.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    seekBar.setProgress(Integer.parseInt(String.valueOf(t.getText())));
                    b.callOnClick();
                    t.setText("");
                }
                return false;
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 0;
                try {
                    if (t.getText() != null) {
                        x = Integer.parseInt(String.valueOf(t.getText()));
                        c++;
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Insert a number before checking", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    // Pass
                }

                if (b.getText().equals("Reset")) {
                    n = (int) (Math.random() * 100 + 1);
                    b.setText(R.string.check);

                    c = 0;
                    attempts.setText(("Attempts: " + c));
                } else {
                    attempts.setText(("Attempts: " + c));

                    if (n == x) {
                        b.setText(R.string.reset);
                        Toast.makeText(MainActivity.this, "Congratulations, you've found the number!", Toast.LENGTH_LONG).show();
                        win = true;
                        max = 100;
                        min = 0;
                        t.setHint("Number between " + min + " and " + max);

                    } else if (n > x) {
                        Toast.makeText(MainActivity.this, "The number you're looking for is higher", Toast.LENGTH_SHORT).show();
                        if (x > min) {
                            min = x;
                        }
                        t.setHint("Number between " + min + " and " + max);
                    } else if (n < x) {
                        Toast.makeText(MainActivity.this, "The number you're looking for is lower", Toast.LENGTH_SHORT).show();
                        if (x < max) {
                            max = x;
                        }
                        t.setHint("Number between " + min + " and " + max);

                    }
                    t.setText("");
                }
            }
        });

        r.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (win) {
                    getNameByDialog();
                } else {
                    if (playerRank.size() == 0) {
                        Toast.makeText(MainActivity.this, "You need to win to save a score", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, RankingActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void getNameByDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Register");
        alert.setMessage("Add new registry:");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean match = false;
                name = input.getText().toString();
                Intent intent = new Intent(MainActivity.this, RankingActivity.class);
                if (!playerRank.isEmpty()) {
                    for (Record r : playerRank) {
                        if (r.getName().equalsIgnoreCase(name)) {
                            r.setAttempts(c);
                            match = true;
                            break;
                        }
                    }
                }
                if (!match) {
                    playerRank.add(new Record(name, c));
                }
                startActivity(intent);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close
            }
        });
        alert.show();
    }

}