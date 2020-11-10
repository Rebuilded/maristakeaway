package nl.mariscollege.maristakeaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    private Button back;
    TextView location_txt;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
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