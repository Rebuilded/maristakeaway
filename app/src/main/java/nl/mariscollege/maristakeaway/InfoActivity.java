package nl.mariscollege.maristakeaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InfoActivity extends AppCompatActivity {

    private Button back;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    TextView location_txt;
    String location;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            goMain();
        }
    }

    private void goMain() {
        Toast.makeText(this,"Alstublieft Inloggen", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        View decorView = getWindow().getDecorView();
        mAuth = FirebaseAuth.getInstance();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        location_txt = findViewById(R.id.title_location_txt);
        getData();
        setData();
        back= (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void goBack() {
        Intent intent= new Intent(this, MenuActivity.class);
        intent.putExtra("data1", location);
        startActivity(intent);
    }

    private void getData() {
        if(getIntent().hasExtra("location")) location = getIntent().getStringExtra("location");

        else Toast.makeText(this,"De locatie is onduidelijk", Toast.LENGTH_SHORT).show();
    }

    private void setData() {
        location_txt.setText(location);
    }
}