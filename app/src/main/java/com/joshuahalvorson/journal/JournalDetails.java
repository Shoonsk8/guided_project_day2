package com.joshuahalvorson.journal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Date;

public class JournalDetails extends AppCompatActivity {
    public static final int IMAGE_REQUEST_CODE = 1;
    private TextView dateText;
    private EditText journalEntry;
    private ImageView journalImage;
    private SeekBar dayRating;
    private Button addImageButton, shareEntryButton;

    private JournalEntry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(getLocalClassName(), "onCreate");
        setContentView(R.layout.activity_journal_details);

        dateText = findViewById(R.id.journal_date);
        journalEntry = findViewById(R.id.journal_entry);
        journalImage = findViewById(R.id.journal_image);
        dayRating = findViewById(R.id.journal_day_rating);
        addImageButton = findViewById(R.id.journal_add_image);
        shareEntryButton = findViewById(R.id.share_entry_button);

        if(entry != null){
            dateText.setText(entry.getDate());
            journalEntry.setText(entry.getEntryText());
            dayRating.setProgress(entry.getDayRating());
            //journalImage.setImageURI(entry.getImage());
        }

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                imageIntent.setType("image/*");
                startActivityForResult(imageIntent, IMAGE_REQUEST_CODE);
            }
        });

        shareEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, journalEntry.getText().toString());
                startActivity(Intent.createChooser(sharingIntent, "Share Using"));
            }
        });

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null){
            if(type.startsWith("image/")){
                entry = new JournalEntry(ListActivity.nextId++);
                Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                entry.setImageUrl(uri);
                journalImage.setVisibility(View.VISIBLE);
                journalImage.setImageURI(uri);
            }
        }else{
            entry = (JournalEntry) getIntent().getSerializableExtra(JournalEntry.TAG);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(getLocalClassName(), "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(getLocalClassName(), "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(getLocalClassName(), "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(getLocalClassName(), "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(getLocalClassName(), "onDestroy");
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        JournalEntry newEntry = new JournalEntry(
                entry.getDate(),
                journalEntry.getText().toString(),
                "",
                dayRating.getProgress(),
                entry.getId());
        resultIntent.putExtra(JournalEntry.TAG, newEntry);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                Uri uri = data.getData();
                entry.setImageUrl(uri);
                journalImage.setImageURI(uri);
                journalImage.setVisibility(View.VISIBLE);
            }
        }
    }
}
