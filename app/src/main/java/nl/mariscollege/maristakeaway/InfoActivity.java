package nl.mariscollege.maristakeaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoActivity extends AppCompatActivity {

    private Button back;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    TextView location_txt, address_txt, drukte_txt, extra_txt;
    String location, d_address, d_drukte, d_extra;

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
        address_txt= (TextView) findViewById(R.id.address_info_txt);
        drukte_txt= (TextView) findViewById(R.id.drukte_info_txt);
        extra_txt= (TextView) findViewById(R.id.extra_info_txt);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference infoRef = database.getReference().child("information").child(getData());
        infoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                d_address = dataSnapshot.child("address").getValue().toString();
                d_drukte = dataSnapshot.child("drukte").getValue().toString();
                d_extra = dataSnapshot.child("extra").getValue().toString();
                address_txt.setText(d_address);
                drukte_txt.setText(d_drukte);
                extra_txt.setText(d_extra);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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

    private String getData() {
        if(getIntent().hasExtra("location")) location = getIntent().getStringExtra("location");
        else Toast.makeText(this,"De locatie is onduidelijk", Toast.LENGTH_SHORT).show();
        return location;
    }

    private void setData() {
        location_txt.setText(location);;
    }
}