package com.example.dellcom.passenger;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.example.dellcom.passenger.navigation.mMap;

public class SearchDroppOff extends AppCompatActivity {

    EditText search;
    Button b;
    public static LatLng latLng1;
    List<Address> a= new ArrayList<Address>();
    Geocoder geocoder;
    public static Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dropp_off);

        search= (EditText)findViewById(R.id.editText8);
        b= (Button)findViewById(R.id.button5);

        geocoder= new Geocoder(this);
    }

    public void list(View view){

        String s=search.getText().toString();


        try {
             a= geocoder.getFromLocationName(s, 3);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!a.isEmpty()) {
            address = a.get(0);
            latLng1 = new LatLng(address.getLatitude(), address.getLongitude());

            startActivity(new Intent(getBaseContext(), Ride.class));
        }
        else{
            Toast.makeText(getBaseContext(), "No Result ! ", Toast.LENGTH_LONG).show();
        }


    }
}
