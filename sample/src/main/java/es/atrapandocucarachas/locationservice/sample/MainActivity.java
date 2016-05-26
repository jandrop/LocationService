package es.atrapandocucarachas.locationservice.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author Alejandro Platas Mallo
 * @version X.XX
 * @since 26/5/16
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startActivity1(View view) {
        startActivity(new Intent(this,ExampleBroadcastReceiver.class));
    }

    public void startActivity2(View view) {
        startActivity(new Intent(this,ExampleInterfaceLocationManager.class));

    }
}
