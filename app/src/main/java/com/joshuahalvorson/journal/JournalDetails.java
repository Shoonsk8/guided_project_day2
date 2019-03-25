package com.joshuahalvorson.journal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private Button addImageButton;

    private JournalEntry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_details);

        entry = (JournalEntry) getIntent().getSerializableExtra(JournalEntry.TAG);

        dateText = findViewById(R.id.journal_date);
        journalEntry = findViewById(R.id.journal_entry);
        journalImage = findViewById(R.id.journal_image);
        dayRating = findViewById(R.id.journal_day_rating);
        addImageButton = findViewById(R.id.journal_add_image);

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
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        JournalEntry newEntry = new JournalEntry(entry.getDate(), journalEntry.getText().toString(), "", dayRating.getProgress(), entry.getId());
        resultIntent.putExtra(JournalEntry.TAG, newEntry);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                Uri uri = data.getData();
                journalImage.setImageURI(uri);
                journalImage.setVisibility(View.VISIBLE);
            }
        }
    }
}
