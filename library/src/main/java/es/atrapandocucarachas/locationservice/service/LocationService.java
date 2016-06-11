/*
 *  Copyright 2016 Alejandro Platas Mallo
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package es.atrapandocucarachas.locationservice.service;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import es.atrapandocucarachas.locationservice.R;

public class LocationService extends Service
        implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    //private final static String LOG_TAG = LocationService.class.getSimpleName();
    public final static String EXTRA_LOCATION = "location";
    private GoogleApiClient mGoogleApiClient;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;
    private int notifyID = 1;
    private double lat, lng;

    @Override
    public void onCreate() {
        super.onCreate();
        //Log.i(LOG_TAG, "Service Started");

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        displayNotificationMessage(getString(R.string.notification_message));

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void displayNotificationMessage(String message) {
        String packageName = getPackageName();
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);

        builder = new NotificationCompat.Builder(this)
                .setContentTitle(message)
                .setContentText(getString(R.string.latitude_longitude, lat, lng))
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(contentIntent)
                .setOngoing(true);

        mNotificationManager.notify(notifyID, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        mNotificationManager.cancel(notifyID);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, createLocationRequest(), this);
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();

        builder.setContentText(getString(R.string.latitude_longitude, lat, lng));
        mNotificationManager.notify(
                notifyID,
                builder.build()
        );

        Intent i = new Intent("android.intent.action.MAIN");
        i.putExtra(EXTRA_LOCATION, location);
        sendBroadcast(i);
    }
}
