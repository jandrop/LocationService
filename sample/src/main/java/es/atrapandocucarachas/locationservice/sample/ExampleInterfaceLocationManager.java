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

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import es.atrapandocucarachas.locationservice.service.LocationManager;

public class ExampleInterfaceLocationManager extends AppCompatActivity implements LocationManager.LocationIF {

    private static final String LOG_TAG = ExampleInterfaceLocationManager.class.getSimpleName();

    private TextView locationText;
    private LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_activity);

        initiUi();
        manager = new LocationManager(this);
        manager.registerLocationListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.destroyReceiver();

    }

    private void initiUi(){
        locationText = (TextView) findViewById(R.id.locationText);
    }

    public void startLocation(View view) {
        manager.startLocationUpdates();
    }

    public void stopLocation(View view) {
        manager.stopLocationUpdates();
    }

    @Override
    public void onLocationChange(Location location) {
        if(location!=null)
            locationText.setText(location.toString());
    }
}
