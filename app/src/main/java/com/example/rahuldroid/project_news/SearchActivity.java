package com.example.rahuldroid.project_news;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rahuldroid.project_news.ContentRecievers.DataModel;
import com.example.rahuldroid.project_news.ContentRecievers.NetworkUtils;
import com.example.rahuldroid.project_news.ContentRecievers.UrlCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    // holds the keyword to be searched
    private String searchKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Setting the toolbar
        Toolbar toolbar = findViewById(R.id.searchActivityToolbar);
        setSupportActionBar(toolbar);

        // setting up the back button in toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // setting up the search edit text and search btn
        final EditText et_search = findViewById(R.id.et_search);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    searchKeyword = et_search.getText().toString();
                    searchForContent(searchKeyword);
                    handled = true;
                }
                return handled;
            }
        });
        ImageButton searchBtn = findViewById(R.id.btn_search);

        // setting the on click listener to the searchBtn
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchKeyword = et_search.getText().toString();
                searchForContent(searchKeyword);
            }
        });
    }

    /**
     * initiates search for the info
     * @param keyword
     */
    private void searchForContent(String keyword) {
        new SearchNetworkTask().execute(keyword);
        hideSoftKeyboard();
    }

    /**
     * hides on screen soft keyboard on search button press
     */
    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    class SearchNetworkTask extends AsyncTask<String, Void, Void> {

        // This is the data model that we are going to use to populate the recycler view.
        private List<DataModel> data;
        ProgressBar progressBar;
        RecyclerView recyclerView;

        /**
         * Initiate the json request for the keywords
         * @param strings
         * @return null
         */
        @Override
        protected Void doInBackground(String... strings) {
            data = new ArrayList<>();
            NetworkUtils utils = new NetworkUtils();
            data = utils.returnContent(15, strings[0]);
            for (DataModel dataModel : data) {
                Log.d(TAG, dataModel.getTitle() + "\t" + dataModel.getArticleUrl());
            }
            return null;
        }

        /**
         * Executes before start of the network request and makes the progressbar visible
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.searchProgress_bar);
            progressBar.setVisibility(View.VISIBLE);
            recyclerView = findViewById(R.id.rv_search_result);
            recyclerView.setVisibility(View.INVISIBLE);
        }

        /**
         * Invoked at the end of Async task and will be used to inflate the recyclerView.
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.INVISIBLE);

            recyclerView.setVisibility(View.VISIBLE);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(data, getApplicationContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
        }
    }
}
