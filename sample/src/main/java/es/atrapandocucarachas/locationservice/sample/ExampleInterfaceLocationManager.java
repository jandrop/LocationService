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
        setContentView(R.layout.activity_main);

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
        manager.startLocationUpdates();
    }

    @Override
    public void onLocationChange(Location location) {
        if(location!=null)
            locationText.setText(location.toString());
    }
}
