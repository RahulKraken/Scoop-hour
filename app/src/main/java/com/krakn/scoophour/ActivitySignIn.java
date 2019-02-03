package com.krakn.scoophour;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivitySignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String email, password, confirm_password;
    private EditText et_email, et_password, et_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_sign_in);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_password_confirm);

        mAuth = FirebaseAuth.getInstance();
    }

    public void signUpClicked(View view) {
        email = et_email.getText().toString();
        password = et_password.getText().toString();
        confirm_password = et_confirm_password.getText().toString();

        if (password.equals(confirm_password)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseHelper helper = new FirebaseHelper(getApplicationContext());
                        startActivity(new Intent(getApplicationContext(), ForYouActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Error signing up!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            et_confirm_password.setText(R.string.sign_up_passwords_not_match_error_label);
        }
    }
}
