package com.example.dellcom.passenger;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.dellcom.passenger.SearchDroppOff.latLng1;
import static com.example.dellcom.passenger.navigation.latLng;
import static com.example.dellcom.passenger.navigation.latitude;
import static com.example.dellcom.passenger.navigation.longitude;
import static com.example.dellcom.passenger.navigation.mMap;
import static com.example.dellcom.passenger.navigation.mapFragment;

public class Ride extends AppCompatActivity implements OnMapReadyCallback{

    Button go;
    ArrayList<LatLng> listpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        go= (Button)findViewById(R.id.button6);
        listpoints= new ArrayList<>();
    }
    public void Go(View v){

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Nearby Drivers Found!");
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        mMap.setMyLocationEnabled(true);
        mMap.setBuildingsEnabled(true);

        latLng = new LatLng(longitude, latitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title("You").draggable(true));
        if(!(latLng1 == null)) {
            mMap.addMarker(new MarkerOptions().position(latLng1).title("DropOff"));

//            if(listpoints.size() >= 2){
//
//                listpoints.clear();
//                mMap.clear();
//            }
//            listpoints.add(latLng);
//            listpoints.add(latLng1);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

//            if(listpoints.size() ==  2){
//
//                String url = getrequesturl(listpoints.get(0), listpoints.get(1));
//                TaskRequestDirections taskRequestDirections= new TaskRequestDirections();
//                taskRequestDirections.execute(url);
//            }
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(latLng1, latLng)
                    .width(5)
                    .color(Color.BLUE).geodesic(true));
        }
        else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }
//    private String getrequesturl(LatLng origin, LatLng dest){
//
//        String str_or= "origin"+origin.latitude+ ","+ origin.longitude;
//        String str_des= "destination"+dest.latitude+ ","+ dest.longitude;
//        String sensor= "sensor=false";
//        String mode= "mode=driving";
//        String param= str_or + "&" + str_des + "&" + sensor + "&" + mode;
//        String output= "json";
//        String url= "https://maps.googleapis.com/maps/api/directions/" + output +"?" + param;
//        return url;
//    }
//    private String requestdirection(String requrl) throws IOException{
//
//        String responsestring= "";
//        InputStream inputStream= null;
//        HttpURLConnection httpURLConnection= null;
//        try{
//            URL url= new URL(requrl);
//            httpURLConnection= (HttpURLConnection)url.openConnection();
//            httpURLConnection.connect();
//
//            inputStream= httpURLConnection.getInputStream();
//            InputStreamReader inputStreamReader= new InputStreamReader(inputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//            StringBuffer stringBuffer= new StringBuffer();
//            String line= "";
//            while((line= bufferedReader.readLine()) != null){
//
//                stringBuffer.append(line);
//            }
//            responsestring= stringBuffer.toString();
//            bufferedReader.close();
//            inputStreamReader.close();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//        finally {
//
//            if(inputStream != null){
//                inputStream.close();
//            }
//            httpURLConnection.disconnect();
//        }
//        return responsestring;
//    }
//    public class TaskRequestDirections extends AsyncTask<String, Void, String>{
//
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            String responsestring= "";
//            try{
//                responsestring= requestdirection(strings[0]);
//            }
//            catch(IOException e){
//                e.printStackTrace();
//            }
//            return responsestring;
//        }
//        @Override
//        protected void onPostExecute(String s){
//            super.onPostExecute(s);
//
//            TaskParser taskParser= new TaskParser();
//            taskParser.execute();
//        }
//    }
//    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>>{
//
//
//        @Override
//        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
//
//            JSONObject jsonObject = null;
//            List<List<HashMap<String, String>>>routes= null;
//            try{
//                jsonObject =new JSONObject(strings[0]);
//                DirectionsParser directionsParser= new DirectionsParser();
//                routes = directionsParser.parse(jsonObject);
//            }
//            catch(JSONException e){
//                e.printStackTrace();
//            }
//            return routes;
//        }
//
//        @Override
//        protected void onPostExecute(List<List<HashMap<String, String>>> lists){
//
//            ArrayList points= null;
//            PolylineOptions polylineOptions= null;
//
//            for(List<HashMap<String, String>>path : lists){
//
//                points = new ArrayList();
//                polylineOptions= new PolylineOptions();
//
//                for(HashMap<String, String>point : path){
//
//                    double lat= Double.parseDouble(point.get("Lat"));
//                    double lon= Double.parseDouble(point.get("lon"));
//
//                    points.add(new LatLng(lat, lon));
//                }
//
//                polylineOptions.addAll(points);
//                polylineOptions.width(15);
//                polylineOptions.color(Color.BLUE);
//                polylineOptions.geodesic(true);
//            }
//
//            if(polylineOptions != null){
//                mMap.addPolyline(polylineOptions);
//            }
//            else{
//                Toast.makeText(getBaseContext(), "Directions Not Found", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
