package edu.virginia.cs4720.scavenger;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class GPSService extends Service {
    LocationManager locationManager;
    LocationListener locationListener;

    public GPSService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Intent Example", "Service Binded");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        Log.i("Intent Example", "Service onStart");
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                doServiceWork(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onCreate() {
        Log.i("Intent Example", "Service onCreate");

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        Log.i("Intent Example", "Service onDestroy");
        _shutdownService();

    }

    private void doServiceWork(Location location) {
        String currentDateTimeString = DateFormat.format("MM/dd/yy h:mm:ssaa", new Date()).toString();
        Toast.makeText(this, "Location: " + location.toString() + " / " + currentDateTimeString, Toast.LENGTH_LONG).show();

    }

    private void _shutdownService() {

        locationManager.removeUpdates(locationListener);

        Log.i("Intent Example", "Timer stopped...");
    }


}
