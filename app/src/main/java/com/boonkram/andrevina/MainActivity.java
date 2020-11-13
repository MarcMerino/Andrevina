package com.boonkram.andrevina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private Uri getLatestPhoto() {
        File f = new File("/sdcard/Android/data/com.boonkram.Andrevina/files/Pictures");
        if (f.exists()) {
            if (f.listFiles() != null) {
                return FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", f.listFiles()[f.listFiles().length - 1]);
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Intent intent = new Intent(MainActivity.this, RankingActivity.class);

            playerRank.add(new Record(name, c, getLatestPhoto()));
            Log.v("DEBUG", getLatestPhoto().toString());

            startActivity(intent);
        }
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
                    dispatchTakePictureIntent();
                }

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