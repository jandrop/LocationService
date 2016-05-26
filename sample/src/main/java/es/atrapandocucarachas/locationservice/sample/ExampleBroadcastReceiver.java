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
