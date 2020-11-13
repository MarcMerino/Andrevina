package com.boonkram.andrevina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RankingActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        final Button b = findViewById(R.id.backButton);
        final ListView l = findViewById(R.id.rankingList);

        ArrayAdapter<Record> adapter = new ArrayAdapter<Record>(this, R.layout.list_item, MainActivity.playerRank) {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.name)).setText(getItem(pos).getName());
                ((TextView) convertView.findViewById(R.id.attempts)).setText("Attempts: " + String.valueOf(getItem(pos).getAttempts()));
                ((ImageView) convertView.findViewById(R.id.imageView)).setImageURI(getItem(pos).getFileUri());
                return convertView;
            }
        };
        
        l.setAdapter(adapter);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RankingActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}