package com.example.dellcom.passenger;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import static com.example.dellcom.passenger.navigation.latLng;
import static com.example.dellcom.passenger.navigation.latitude;
import static com.example.dellcom.passenger.navigation.longitude;
import static com.example.dellcom.passenger.navigation.mMap;
import static com.example.dellcom.passenger.navigation.mapFragment;

public class dropoff extends AppCompatActivity implements OnMapReadyCallback{
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        com.google.android.gms.location.LocationListener{

    Button drop, skip;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES= 1;   // 1 meter
    private static final long MIN_TIME_BW_UPDATES= 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropoff);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        drop= (Button)findViewById(R.id.button4);
        skip= (Button)findViewById(R.id.button3);


    }
    public void skip(View v){
        startActivity(new Intent(getBaseContext(), Ride.class));
    }

    public void search(View view){

        startActivity(new Intent(getBaseContext(), SearchDroppOff.class));
    }

//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//        locationRequest = new LocationRequest();
//        locationRequest.setInterval(1000);
//        locationRequest.setFastestInterval(1000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//        location= location;
//        latLng = new LatLng(location.getLongitude(), location.getLatitude());
//        mMap.addMarker(new MarkerOptions().position(latLng).title("You"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        mMap.setMyLocationEnabled(true);
        mMap.setBuildingsEnabled(true);

        latLng = new LatLng(longitude, latitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title("You").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }
//    protected synchronized void buildGoogleApiClient(){
//
//        googleApiClient= new GoogleApiClient.Builder(this).
//                addConnectionCallbacks(this).
//                addOnConnectionFailedListener(this).
//                addApi(LocationServices.API).build();
//
//        googleApiClient.connect();
//
//    }
}
