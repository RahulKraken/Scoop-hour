package com.example.rahuldroid.project_news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.rahuldroid.project_news.ContentRecievers.DataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ReadLaterActivity extends AppCompatActivity {

    private static final String TAG = "ReadLaterActivity";

    private RecyclerView rlRecyclerView;
    private ProgressBar rlProgressBar;
    ArrayList<DataModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_later);

        Toolbar toolbar = findViewById(R.id.readLaterActivityToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.read_later_action_bar_title);
        }

        rlProgressBar = findViewById(R.id.rlProgress_bar);
        rlRecyclerView = findViewById(R.id.rlRecyclerView);

        data = new ArrayList<>();
        rlProgressBar.setVisibility(View.VISIBLE);

        FirebaseHelper.readLaterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue() != null) {
                        data.add(ds.getValue(DataModel.class));
                    }
                    Log.d(TAG, "onDataChange: " + Objects.requireNonNull(ds.getValue()).toString());
                }
                rlProgressBar.setVisibility(View.INVISIBLE);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(data, ReadLaterActivity.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReadLaterActivity.this);
                rlRecyclerView.setAdapter(adapter);
                rlRecyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
