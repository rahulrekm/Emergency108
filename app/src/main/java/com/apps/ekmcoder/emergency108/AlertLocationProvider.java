package com.apps.ekmcoder.emergency108;

/**
 * Created by alan
 */


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

/**
 * Created by alan on 10/16/2016.
 */
public class AlertLocationProvider implements LocationListener {
    // Flag for GPS status
    boolean isGPSEnabled = false;
    // Flag for network status
    boolean isNetworkEnabled = false;
    // Flag for GPS status
    boolean canGetLocation = false;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    LocationManager locationManager;

     Location location; // Location
    double latitude; // Latitude
    double longitude; // Longitude

    private final Context mContext;

    public AlertLocationProvider(Context context) {
        this.mContext = context;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        location=getLocation();
    }

    private Location getLocation() {
        try {
            // Getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            // Getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                // No network provider is enabled
               // showSettingsAlert();
                turnGPSOn();
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // If GPS enabled, get latitude/longitude using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (SecurityException e) {
           // Toast.makeText(mContext, "" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.d("### log ###", "---alrtlocprovide---excptn-----" + e.getMessage());

            e.printStackTrace();
        }


        return location;
    }


    public String showPlace() {

        StringBuilder builder = new StringBuilder();
        Geocoder geoCoder = new Geocoder(mContext, Locale.getDefault());

        try {
            List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i = 0; i < maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);

                builder.append(addressStr);
                builder.append(" ");
            }

            String finalAddress = builder.toString();
            //Toast.makeText(mContext, finalAddress, Toast.LENGTH_SHORT).show();
            Log.d("### log ###", "----geocoder--address--" +finalAddress );

        } catch (IOException e) {
            Log.d("### log ###", "----geocoder--address--" + e.getMessage());
        } catch (NullPointerException e) {
            Log.d("### log ###", "----geocoder--address--" + e.getMessage());
        }
        return builder.toString();
    }
    private void turnGPSOn(){
        String provider = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            mContext.sendBroadcast(poke);
        }
    }

    private void turnGPSOff(){
        String provider = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
           // sendBroadcast(poke);
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    @Override
    public void onLocationChanged(Location location) {
        //send broadcast for live updates -alan
        //location contains new location -alan
        //check for good location with new and old -alan
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


