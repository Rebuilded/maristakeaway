package nl.mariscollege.maristakeaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LocationsActivity extends AppCompatActivity {

    private Button back;
    RecyclerView recyclerView;
    String s1[], s2[];
    int images[]= {R.drawable.l_belgisch_park, R.drawable.l_houtrust, R.drawable.l_bohemen, R.drawable.l_statenkwartier, R.drawable.l_waldeck, R.drawable.l_kijkduin};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        recyclerView= findViewById(R.id.recyclerView);
        s1= getResources().getStringArray(R.array.locations);
        s2= getResources().getStringArray(R.array.address);
        myAdapter MyAdapter = new myAdapter(this, s1, s2, images);
        recyclerView.setAdapter(MyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        back= (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }
    private void goBack() {
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}