package com.boonkram.andrevina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        final Button b = findViewById(R.id.backButton);
        final TableLayout table = findViewById(R.id.rankingTable);

        Intent i = getIntent();

        Log.v("DEBUG", "Player: " + i.getStringExtra("name") + ", " + i.getStringExtra("attempts"));

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RankingActivity.this, MainActivity.class);
                startActivity(i);

            }
        });
    }
}