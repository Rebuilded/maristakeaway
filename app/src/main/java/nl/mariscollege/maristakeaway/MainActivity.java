package nl.mariscollege.maristakeaway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView email_txt, password_txt;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email_txt = (EditText) findViewById(R.id.email_box);
        password_txt = (EditText) findViewById(R.id.password_box);
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String email = email_txt.getText().toString().trim();
        String password= password_txt.getText().toString().trim();
        if(email.isEmpty()){
            email_txt.setError("Vul een emailadres in!");
            email_txt.requestFocus();
            return;
        }
        if(password.isEmpty()){
            password_txt.setError("Vul een wachtwoord in!");
            password_txt.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goLocations(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Het inloggen is niet gelukt",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void goLocations(FirebaseUser u) {
        Intent intent= new Intent(this, LocationsActivity.class);
        intent.putExtra("name", u.getEmail());
        startActivity(intent);
        Toast.makeText(this,"Er is ingelogd met " + u.getEmail(), Toast.LENGTH_SHORT).show();
    }
}