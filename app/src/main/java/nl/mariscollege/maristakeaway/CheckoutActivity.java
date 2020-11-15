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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;


public class CheckoutActivity extends AppCompatActivity {

    private Button bestellen, back, groep, pauze;
    private TextView brood_prijs_txt, brood_txt, flesje_prijs_txt, flesje_txt, totaal_prijs_txt;
    private FirebaseAuth mAuth;
    String location;
    Boolean selectedBroodje, selectedFlesje;
    Boolean bovenbouw= true;
    Boolean pauzeRechts= true;
    DatabaseReference reff;
    Order order;

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
        setContentView(R.layout.activity_checkout);
        View decorView = getWindow().getDecorView();
        mAuth = FirebaseAuth.getInstance();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        bestellen= (Button) findViewById(R.id.submit);
        back= (Button) findViewById(R.id.back);
        groep= (Button) findViewById(R.id.groep_btn);
        pauze= (Button) findViewById(R.id.pauze_btn);
        brood_prijs_txt= (TextView) findViewById(R.id.broodje_prijs_txt);
        brood_txt= (TextView) findViewById(R.id.broodje_txt);
        flesje_prijs_txt= (TextView) findViewById(R.id.flesje_prijs_txt);
        flesje_txt= (TextView) findViewById(R.id.flesje_txt);
        totaal_prijs_txt= (TextView) findViewById(R.id.totaal_prijs_txt);
        order = new Order();
        groep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGroep();
            }
        });
        pauze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPauze();
            }
        });
        bestellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        getData();
        setData();
    }
    //Sorry maar ik kon niks anders bedenken
    private void selectPauze() {
        if(pauzeRechts == false){
            pauzeRechts= true;
            if(bovenbouw == true) pauze.setBackgroundResource(R.drawable.uren_zes);
            else pauze.setBackgroundResource(R.drawable.uren_vijf);
        }
        else{
            pauzeRechts= false;
            if(bovenbouw == true) pauze.setBackgroundResource(R.drawable.uren_drie);
            else pauze.setBackgroundResource(R.drawable.uren_twee);
        }
    }

    private void selectGroep() {
            if(bovenbouw == false){
                bovenbouw= true;
                groep.setBackgroundResource(R.drawable.klassen_bovenbouw);
                if(pauzeRechts == true) pauze.setBackgroundResource(R.drawable.uren_zes);
                else pauze.setBackgroundResource(R.drawable.uren_drie);
            }
            else{
                bovenbouw= false;
                groep.setBackgroundResource(R.drawable.klassen_onderbouw);
                if(pauzeRechts == true) pauze.setBackgroundResource(R.drawable.uren_vijf);
                else pauze.setBackgroundResource(R.drawable.uren_twee);
            }
    }

    private void goBack() {
        Intent intent= new Intent(this, MenuActivity.class);
        intent.putExtra("data1", location);
        startActivity(intent);
    }

    private void submit() {
        //Getting the myEmail
        FirebaseUser user = mAuth.getCurrentUser();
        String myEmail = user.getEmail();
        order.setEmail(myEmail);
        //Getting the items
        String myItems;
        if(selectedBroodje == true && selectedFlesje == true)  myItems= "Broodje Gezond en Flesje Water";
        else if(selectedBroodje == true) myItems= "Broodje Gezond";
        else myItems = "Flesje Water";
        order.setItems(myItems);
        //Getting the break
        Integer myBreak;
        if(bovenbouw == true){
            if(pauzeRechts == true) myBreak = 6;
            else myBreak = 3;
        }
        else{
            if(pauzeRechts == true) myBreak = 5;
            else myBreak = 2;
        }
        order.setBreak(myBreak);
        order.setPrice(totaal_prijs_txt.getText().toString());
        //Getting DeliveryDate
        Calendar calender = Calendar.getInstance();
        String myDeliveryDate = DateFormat.getDateInstance(DateFormat.FULL).format(calender.getTime());
        order.setDeliveryDate(myDeliveryDate);
        order.setLocation(location);
        reff.push().setValue(order);
        Toast.makeText(this, "Uw bestelling is verzonden", Toast.LENGTH_SHORT);
        Intent intent= new Intent(this, ConformationActivity.class);
        startActivity(intent);
    }

    private void getData() {
        if(getIntent().hasExtra("location") && getIntent().hasExtra("broodje") && getIntent().hasExtra("flesje")){
            location = getIntent().getStringExtra("location");
            selectedBroodje= getIntent().getBooleanExtra("broodje", false);
            selectedFlesje= getIntent().getBooleanExtra("flesje", false);
        }
        else Toast.makeText(this,"De locatie is onduidelijk", Toast.LENGTH_SHORT).show();
        reff = FirebaseDatabase.getInstance().getReference().child("orders");
    }

    private void setData() {
        if(selectedBroodje == false){
            brood_txt.setTextSize(1);
            brood_prijs_txt.setTextSize(1);
            totaal_prijs_txt.setText("€0,50");
        }
        else if(selectedFlesje == false){
            flesje_txt.setTextSize(1);
            flesje_prijs_txt.setTextSize(1);
            totaal_prijs_txt.setText("€2,00");
        }
    }
}