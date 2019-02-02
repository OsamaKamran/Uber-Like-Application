package com.example.dellcom.passenger;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.os.Bundle;
import android.os.IBinder;
import android.content.Intent;
import android.content.DialogInterface;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.dellcom.passenger.navigation.latLng;
import static com.example.dellcom.passenger.navigation.mMap;


public class gpstracker extends Service implements LocationListener{

    private Context mcontext;
    boolean isgpsenabled= false;
    boolean isnetworkenabled= false;
    boolean cangetlocation = false;
    Location location;
    double longitude, latitude;
    String mPermission= Manifest.permission.ACCESS_FINE_LOCATION;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES= 1;   // 1 meter
    private static final long MIN_TIME_BW_UPDATES= 6000;  // 6 sec;
    protected LocationManager locationmanager;

    public gpstracker(){}
    public gpstracker(Context context){
        mcontext=context;
        getlocation();
    }
    public Location getlocation(){

        try{
            locationmanager= (LocationManager)mcontext.getSystemService(LOCATION_SERVICE);
            isgpsenabled= locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isnetworkenabled= locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isgpsenabled && !isnetworkenabled) {
                // no network provider is enabled
            } else {
                this.cangetlocation = true;
                if (isnetworkenabled) {

                    try{

                        locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationmanager != null) {

                            location = locationmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if(location !=null) {

                                longitude = location.getLongitude();
                                latitude = location.getLatitude();
                            }
                        }
                    } catch (java.lang.SecurityException ex) {
                        Log.i("ee", "fail to request location update, ignore", ex);
                    } catch (IllegalArgumentException ex) {
                        Log.d("eee", "gps provider does not exist " + ex.getMessage());
                    }
                }
                if(isgpsenabled){


                    try{

                        if(location == null){
                            locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                            if(locationmanager !=null){

                                location= locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if(location !=null) {

                                    longitude = location.getLongitude();
                                    latitude = location.getLatitude();
                                }
                            }
                        }
                    } catch (java.lang.SecurityException ex) {
                        Log.i("ee", "fail to request location update, ignore", ex);
                    } catch (IllegalArgumentException ex) {
                        Log.d("eee", "gps provider does not exist " + ex.getMessage());
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }
    public void stopusinggps(){

        if(locationmanager != null){
            locationmanager.removeUpdates(gpstracker.this);
        }
    }
    public double getlatitude(){

        if(location != null){
            latitude=location.getLatitude();
        }
        return latitude;
    }
    public double getlongitude(){

        if(location != null){
            longitude=location.getLongitude();
        }
        return longitude;
    }
    public boolean cangetlocation(){
        return this.cangetlocation;
    }
    public void showsettingsalert(){

        AlertDialog.Builder alertdailog= new AlertDialog.Builder(mcontext);
        alertdailog.setTitle("GPS SETTINGS");
        alertdailog.setMessage("GPS is not enabled , do you want to go to settings.");

        alertdailog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mcontext.startActivity(intent);
            }
        });
        alertdailog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });
        alertdailog.show();
    }
    @Override
    public void onLocationChanged(Location location) {

        location= location;
        latLng = new LatLng(location.getLongitude(), location.getLatitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("You").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
