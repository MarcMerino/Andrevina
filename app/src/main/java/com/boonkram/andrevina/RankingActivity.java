package com.boonkram.andrevina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        final Button b = findViewById(R.id.backButton);

        //make sure that the lists contain data or else display will be blank screen
        TableRow.LayoutParams params1=new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1.0f);
        TableRow.LayoutParams params2=new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        TableLayout table = (TableLayout) findViewById(R.id.rankingTable);
        for(int i=0; i < MainActivity.playerRank.size(); i++) {
            TableRow row = new TableRow(this);
            TextView txt1 = new TextView(this); txt1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView txt2 = new TextView(this); txt2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView txt3 = new TextView(this); txt3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            //setting the text
            txt1.setText(MainActivity.playerRank.get(i).getName());
            txt2.setText(String.valueOf(MainActivity.playerRank.get(i).getAttempts()));
            txt3.setText(MainActivity.playerRank.get(i).getTime());
            txt1.setLayoutParams(params1);
            txt2.setLayoutParams(params1);
            txt3.setLayoutParams(params1);
            //the textviews have to be added to the row created
            row.addView(txt1);
            row.addView(txt2);
            row.addView(txt3);
            row.setLayoutParams(params2);
            table.addView(row);
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RankingActivity.this, MainActivity.class);
                startActivity(i);

            }
        });
    }
}