package com.example.rahuldroid.project_news;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class FirebaseHelper {
    /**
     * This is a helper class for Firebase Database Transactions.
     * This class contains the methods to add and remove items from the database
     * for a particular user.
     */

    // This is app wide Firebase Database Reference.
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private Context context;

    /**
     * Empty constructor for the class.
     * @param context
     */
    public FirebaseHelper(Context context) {
        this.context = context;
        // Initializing the mDatabase and mRef objects for a particular reference.
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        /*
        If the current user is not null it will create a reference in the database
        for the user id.
         */
        mRef = mDatabase.getReference(Objects.requireNonNull(
                mAuth.getCurrentUser()).getUid());
    }

    public void commit(Bundle bundle) {
        mRef.setValue("This is me indeed!");
    }
}
