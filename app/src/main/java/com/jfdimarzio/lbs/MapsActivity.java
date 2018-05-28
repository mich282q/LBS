package com.jfdimarzio.lbs;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    final private int REQUEST_COURSE_ACCESS = 123;
 boolean permissionGranted = false;
    LocationManager lm;
 LocationListener locationListener;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_maps);
         // Obtain the SupportMapFragment and get notified
         // when the map is ready to be used.
         SupportMapFragment mapFragment =
                 (SupportMapFragment) getSupportFragmentManager()
         .findFragmentById(R.id.map);
         mapFragment.getMapAsync(this);
         }
 @Override
 public void onPause() {
         super.onPause();
         //---remove the location listener---
         if (ActivityCompat.checkSelfPermission(this,
                 android.Manifest.permission.ACCESS_FINE_LOCATION)
         != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                 this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
         != PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(this,
                     new String[]{
                     android.Manifest.permission.ACCESS_COARSE_LOCATION},
                     REQUEST_COURSE_ACCESS);
             return;
             }else{
             permissionGranted = true;
             }
         if(permissionGranted) {
             lm.removeUpdates(locationListener);
             }
         }
 @Override
 public void onMapReady(GoogleMap googleMap) {
         mMap = googleMap;
         lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
         locationListener = new MyLocationListener();
         if (ActivityCompat.checkSelfPermission(this,
                 android.Manifest.permission.ACCESS_FINE_LOCATION)
         != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                 this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
         != PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(this,
                     new String[]{
                     android.Manifest.permission.ACCESS_COARSE_LOCATION},
                     REQUEST_COURSE_ACCESS);
             return;
             }else{
             permissionGranted = true;
             }
         if(permissionGranted) {
             lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                     locationListener);
             }
         }
 @Override
 public void onRequestPermissionsResult(int requestCode, String[] permissions,
 int[] grantResults) {
         switch (requestCode) {
             case REQUEST_COURSE_ACCESS:
                 if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                     permissionGranted = true;
                     } else {
                     permissionGranted = false;
                    }
                 break;
             default:
                 super.onRequestPermissionsResult(requestCode, permissions,
                         grantResults);
                 }
         }
private class MyLocationListener implements LocationListener
 {
        public void onLocationChanged(Location loc) {
             if (loc != null) {
                 Toast.makeText(getBaseContext(),
                         "Location changed : Lat: " + loc.getLatitude() +
                                 " Lng: " + loc.getLongitude(),
                        Toast.LENGTH_SHORT).show();
                 LatLng p = new LatLng(
                        (int) (loc.getLatitude()),
                         (int) (loc.getLongitude()));
                 mMap.moveCamera(CameraUpdateFactory.newLatLng(p));
                 mMap.animateCamera(CameraUpdateFactory.zoomTo(7));
                 }
        }
         public void onProviderDisabled(String provider) {
             }
         public void onProviderEnabled(String provider) {
            }
         public void onStatusChanged(String provider, int status,
         Bundle extras) {
            }
         }
}