package com.boonkram.andrevina;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Record> playerRank = new ArrayList<Record>();
    private int n = (int) (Math.random() * 100 + 1);
    private boolean win = false;
    private String name;
    private int c = 0;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView t = findViewById(R.id.editTextNumber);
        final TextView attempts = findViewById(R.id.attempts);
        final Button b = findViewById(R.id.button);
        final Button r = findViewById(R.id.rankingActivityOpener);

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
                    b.setText("Comprovar");

                    c = 0;
                    attempts.setText("Attempts: " + c);

                } else {
                    attempts.setText("Attempts: " + c);

                    if (n == x) {
                        b.setText("Reset");
                        Toast.makeText(MainActivity.this, "Congratulations, you've found the number!", Toast.LENGTH_LONG).show();
                        win = true;
                    } else if (n > x) {
                        Toast.makeText(MainActivity.this, "The number you're looking for is higher", Toast.LENGTH_SHORT).show();
                    } else if (n < x) {
                        Toast.makeText(MainActivity.this, "The number you're looking for is lower", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


        r.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (win) {
                    getNameByDialog();
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
                            r.setTime("00:00");
                            match = true;
                            break;
                        }
                    }
                }
                if (!match) {
                    playerRank.add(new Record(name, c, "00:00"));
                }
                win = false;
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