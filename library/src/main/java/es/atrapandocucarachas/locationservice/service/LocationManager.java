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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;

import static es.atrapandocucarachas.locationservice.service.LocationService.EXTRA_LOCATION;

/**
 * @author Alejandro Platas Mallo
 * @version 1.00
 * @since 26/5/16
 */
public class LocationManager {

    private final String MAIN_INTENT_FILTER = "android.intent.action.MAIN";
    private Context mContext;
    private Intent mService;
    private BroadcastReceiver mReceiver;
    private LocationIF mLocationListener;

    public LocationManager(Context mContext) {
        this.mContext = mContext;
        mService = new Intent(mContext, LocationService.class);
        IntentFilter intentFilter = new IntentFilter(MAIN_INTENT_FILTER);
        createReceiver();
        mContext.registerReceiver(mReceiver, intentFilter);
    }

    public void startLocationUpdates() {
        mContext.startService(mService);
    }

    public void stopLocationUpdates() {
        mContext.stopService(mService);
    }

    private void createReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Location location = intent.getParcelableExtra(EXTRA_LOCATION);
                if(mLocationListener!=null){
                    mLocationListener.onLocationChange(location);
                }
            }
        };
    }

    /**
     * Do this into onPause();
     */
    public void destroyReceiver() {
        mContext.unregisterReceiver(mReceiver);
    }

    public interface LocationIF {
        void onLocationChange(Location location);
    }

    public void registerLocationListener(LocationIF mLocationListener){
        this.mLocationListener = mLocationListener;
    }


}
