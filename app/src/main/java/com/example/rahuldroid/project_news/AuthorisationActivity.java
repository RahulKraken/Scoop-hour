package com.example.rahuldroid.project_news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorisationActivity extends AppCompatActivity {

    private final String TAG = AuthorisationActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        /*
        If there is a user already signed in then don't bring this activity and if there is no user signed in then inflate
         */
        if (mUser != null) {
            startActivity(new Intent(this, ForYouActivity.class));
            Log.d(TAG, "Sign in Activity invoked!");
            finish();
        } else {
            setContentView(R.layout.activity_authorisation);
        }
    }

    // This method handles the sign in flows if the user clicks the sign in button.
    public void signInClicked(View view) {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), 1);
    }

    // This method handles the response from the Authentication success from the AuthUI intent builder.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, ForYouActivity.class));
            Toast.makeText(this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Error signing in!", Toast.LENGTH_LONG).show();
        }
    }

    // This method creates no user if skip btn is clicked and straight get them into the app.
    public void skipBtnClicked(View view) {
        startActivity(new Intent(this, ForYouActivity.class));
        finish();
    }
}
