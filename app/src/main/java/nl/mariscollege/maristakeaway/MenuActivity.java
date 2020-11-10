package nl.mariscollege.maristakeaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private Button back;
    private Button info;
    private Button bestellen;
    private Button broodje;
    private Button flesje;
    TextView location;
    ImageView flag;
    ImageView bg;
    String data1;
    boolean selectedBroodje= false;
    boolean selectedFlesje= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        location = findViewById(R.id.location);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        flag= (ImageView) findViewById(R.id.flag);
        bg= (ImageView) findViewById(R.id.background);
        back= (Button) findViewById(R.id.back);
        info= (Button) findViewById(R.id.information);
        bestellen= (Button) findViewById(R.id.submit);
        broodje= (Button) findViewById(R.id.broodje_btn);
        flesje= (Button) findViewById(R.id.flesje_btn);
        getData();
        setData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goInfo();
            }
        });
        bestellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        broodje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBroodje();
            }
        });
        flesje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFlesje();
            }
        });
    }

    private void selectFlesje() {
        if(selectedFlesje == false){
            selectedFlesje= true;
            flesje.setBackgroundResource(R.drawable.flesje_water_selected);
        }
        else if(selectedFlesje == true){
            selectedFlesje= false;
            flesje.setBackgroundResource(R.drawable.flesje_water);
        }
    }

    private void selectBroodje() {
        if(selectedBroodje == false){
            selectedBroodje= true;
            broodje.setBackgroundResource(R.drawable.broodje_gezond_selected);
        }
        else if(selectedBroodje == true){
            selectedBroodje= false;
            broodje.setBackgroundResource(R.drawable.broodje_gezond);
        }
    }

    private void submit() {
        if(selectedBroodje == true || selectedFlesje == true) {
            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putExtra("broodje", selectedBroodje);
            intent.putExtra("flesje", selectedFlesje);
            intent.putExtra("location", data1);
            startActivity(intent);
        }
        else Toast.makeText(this,"De bestelling is leeg", Toast.LENGTH_SHORT).show();
    }

    private void goInfo() {
        Intent intent= new Intent(this, InfoActivity.class);
        intent.putExtra("location", data1);
        startActivity(intent);
    }

    private void getData() {
        if(getIntent().hasExtra("data1")) data1 = getIntent().getStringExtra("data1");
        else Toast.makeText(this,"De locatie is onduidelijk", Toast.LENGTH_SHORT).show();
    }

    private void setData() {
        location.setText(data1);
        //Sorry ik kon geen andere manier bedenken
        if(bg != null){
            switch (data1) {
                case "Mariscollege Belgisch Park":
                    bg.setImageResource(R.drawable.b_belgisch_park);
                    flag.setImageResource(R.drawable.belgisch_park);
                    break;
                case "Mariscollege Houtrust":
                    bg.setImageResource(R.drawable.b_houtrust);
                    flag.setImageResource(R.drawable.houtrust);
                    break;
                case "Mariscollege Bohemen":
                    bg.setImageResource(R.drawable.b_bohemen);
                    flag.setImageResource(R.drawable.bohemen);
                    break;
                case "Mariscollege Statenkwartier":
                    bg.setImageResource(R.drawable.b_statenkwartier);
                    flag.setImageResource(R.drawable.statenkwartier);
                    break;
                case "Mariscollege Waldeck":
                    bg.setImageResource(R.drawable.b_waldeck);
                    flag.setImageResource(R.drawable.waldeck);
                    break;
                case "Mariscollege Kijkduin":
                    bg.setImageResource(R.drawable.b_kijkduin);
                    flag.setImageResource(R.drawable.kijkduin);
                    break;
            }
        }
    }


    private void goBack() {
        Intent intent= new Intent(this, LocationsActivity.class);
        startActivity(intent);
    }
}