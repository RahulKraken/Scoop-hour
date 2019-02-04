package com.krakn.scoophour;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.krakn.scoophour.ContentRecievers.DataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FirebaseHelper {
    /**
     * This is a helper class for Firebase Database Transactions.
     * This class contains the methods to add and remove items from the database
     * for a particular user.
     */

    private static final String TAG = "FirebaseHelper";
    private int READ_LATER_COUNT;

    // This is app wide Firebase Database Reference.
    private FirebaseDatabase mDatabase;
    public static DatabaseReference mRef, articleRef, readLaterRef, counterRef;
    private SharedPreferences firebasePreferences;
    private SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;
    private Context context;

    /**
     * Empty constructor for the class.
     * @param context
     */
    public FirebaseHelper(final Context context) {
        this.context = context;
        // Initializing the mDatabase and mRef objects for a particular reference.
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        firebasePreferences = context.getSharedPreferences(context.getResources().getString(R.string.firebase_preference_title), context.MODE_PRIVATE);
        editor = firebasePreferences.edit();

        /*
        If the current user is not null it will create a reference in the database
        for the user id.
         */
        if (mAuth.getCurrentUser() != null) {
            mRef = mDatabase.getReference().child(context.getString(R.string.users_node_label)).child(mAuth.getCurrentUser().getUid());
            counterRef = mRef.child(context.getResources().getString(R.string.node_counter));
            readLaterRef = mRef.child(context.getResources().getString(R.string.node_articles))
                    .child(context.getResources().getString(R.string.node_read_later));

            updateArticleCount();
        } else {
            Log.d(TAG, "FirebaseHelper: current ref is null");
        }
    }

    private void updateArticleCount() {
        counterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Log.d(TAG, "onDataChange: counter value - " + dataSnapshot.getValue());
                if (dataSnapshot.getValue() != null) {
                    editor.putInt(context.getResources().getString(R.string.num_read_later_articles),
                            Integer.valueOf(dataSnapshot.getValue().toString()));
                    editor.apply();
                    editor.commit();
                    READ_LATER_COUNT = firebasePreferences.getInt(context.getResources().getString(R.string.num_read_later_articles), 0);
//                        Log.d(TAG, "onDataChange: READ_LATER_COUNT - " + READ_LATER_COUNT);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*
    --------------------------------------------------------- Add article to database --------------------------------------------------------
     */

    public void addArticleReadLater(DataModel article) {
        if (mAuth.getCurrentUser() != null) {
            Log.d(TAG, "addArticleReadLater: current ref - " + mRef.toString());
            new FirebaseAddTask().execute(article);
        } else {
            Toast.makeText(context, "No user signed in!", Toast.LENGTH_SHORT).show();
        }
    }

    class FirebaseAddTask extends AsyncTask<DataModel, Void, Void> {

        @Override
        protected Void doInBackground(DataModel... dataModels) {
            final DataModel model = new DataModel(READ_LATER_COUNT,
                    dataModels[0].getTitle(),
                    dataModels[0].getDescription(),
                    dataModels[0].getImageUrl(),
                    dataModels[0].getArticleUrl(),
                    dataModels[0].getSourceName());
            readLaterRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean alreadyAdded = false;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.child("articleUrl").getValue() == model.getArticleUrl()) {
                            alreadyAdded = true;
                        }
                        Log.d(TAG, "onDataChange: " + Objects.requireNonNull(ds.getValue()).toString());
                    }
                    if (!alreadyAdded) {
                        readLaterRef.child(String.valueOf(READ_LATER_COUNT)).setValue(model);
                        READ_LATER_COUNT = READ_LATER_COUNT + 1;
                        counterRef.setValue(READ_LATER_COUNT);
                        Toast.makeText(context, "Article Bookmarked", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(context, "Article is already Bookmarked!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return null;
        }
    }

    /*
    ------------------------------------------------------ Delete article from database ----------------------------------------------------
     */

    public void deleteArticle(int adapterPosition) {
        readLaterRef.child(String.valueOf(adapterPosition)).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(context, "Article removed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
