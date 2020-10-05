package com.boonkram.andrevina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int n = (int) (Math.random() * 100 + 1);
    int c = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView t = findViewById(R.id.editTextNumber);
        final TextView attempts = findViewById(R.id.attempts);
        final Button b = findViewById(R.id.button);

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
                    } else if (n > x) {
                        Toast.makeText(MainActivity.this, "The number you're looking for is higher", Toast.LENGTH_SHORT).show();
                    } else if (n < x) {
                        Toast.makeText(MainActivity.this, "The number you're looking for is lower" + n, Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
}