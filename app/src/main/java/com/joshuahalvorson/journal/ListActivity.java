package com.joshuahalvorson.journal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListActivity extends AppCompatActivity {
    public static final int EDIT_ENTRY_REQUEST_CODE = 2;

    private ArrayList<JournalEntry> journalEntries;

    private Context context;

    private LinearLayout entryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        entryList = findViewById(R.id.entry_list);

        context = this;

        journalEntries = new ArrayList<>();
        addTestEntries();

        for(JournalEntry journalEntry : journalEntries){
            entryList.addView(generateTextView(journalEntry));
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entryList.addView(generateTextView(new JournalEntry("03/23/19", "text", "awdaD", 5, 1)));
            }
        });
    }



    private TextView generateTextView(final JournalEntry entry){
        TextView textView = new TextView(context);
        textView.setText(String.format("Date: %s Rating: %d", entry.getDate(), entry.getDayRating()));
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(30);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsIntent = new Intent(context, JournalDetails.class);
                detailsIntent.putExtra(JournalEntry.TAG, entry);
                startActivityForResult(detailsIntent, EDIT_ENTRY_REQUEST_CODE);
            }
        });
        return textView;
    }

    private void addTestEntries() {
        journalEntries.add(new JournalEntry("03/23/19", "entry1", "awdaD", 5, 1));
        journalEntries.add(new JournalEntry("03/22/19", "entry2", "awdaD", 3, 2));
        journalEntries.add(new JournalEntry("03/24/19", "entry3", "awdaD", 4, 3));
        journalEntries.add(new JournalEntry("03/21/19", "entry4", "awdaD", 1, 4));
    }

}
