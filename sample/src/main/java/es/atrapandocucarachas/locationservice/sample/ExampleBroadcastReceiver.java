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

package es.atrapandocucarachas.locationservice.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import es.atrapandocucarachas.locationservice.service.LocationService;

import static es.atrapandocucarachas.locationservice.service.LocationService.EXTRA_LOCATION;

public class ExampleBroadcastReceiver extends AppCompatActivity {

    private static final String LOG_TAG = ExampleBroadcastReceiver.class.getSimpleName();

    private TextView locationText;
    private Intent mService;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_activity);

        mService = new Intent(this, LocationService.class);
        initiUi();

        IntentFilter intentFilter = new IntentFilter("android.intent.action.MAIN");

        createReceiver();
        registerReceiver(mReceiver, intentFilter);
    }

    private void createReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Location location = intent.getParcelableExtra(EXTRA_LOCATION);
                locationText.setText(location.toString());
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    private void initiUi(){
        locationText = (TextView) findViewById(R.id.locationText);
    }

    public void startLocation(View view) {
        startService(mService);
    }

    public void stopLocation(View view) {
        stopService(mService);
    }
}
