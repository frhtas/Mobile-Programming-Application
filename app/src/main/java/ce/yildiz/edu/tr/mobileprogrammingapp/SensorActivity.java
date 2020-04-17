package ce.yildiz.edu.tr.mobileprogrammingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends AppCompatActivity implements SensorEventListener{
    ListView listView_sensors;
    TextView textView_lux, textView_acceleration;

    private ArrayList<String> sensorNames = new ArrayList<>();
    private SensorManager sensorManager;
    private Sensor mLightSensor, mAccelerometerSensor;
    ArrayAdapter<String> sensorAdapter;

    private int control = 0;
    Thread thread = new Thread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        listView_sensors = (ListView)findViewById(R.id.listView_sensors);
        textView_lux = (TextView)findViewById(R.id.textView_lux);
        textView_acceleration = (TextView)findViewById(R.id.textView_acceleration);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mLightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mAccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : deviceSensors) {
            String sensorName = (sensorNames.size() + 1) + " - " + s.getName();
            sensorNames.add(sensorName);
        }

        sensorAdapter = new ArrayAdapter<String>(SensorActivity.this, R.layout.simple_list_item, sensorNames);
        listView_sensors.setAdapter(sensorAdapter);
    }


    // An override function which do something by SensorType
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lux = event.values[0];
            String myLux = "Lux: " + lux;
            textView_lux.setText(myLux);

            if (lux < 10)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            String acceleration = "x: " + x + "\n" +
                    "y: " + y + "\n" +
                    "z: " + z + "\n";
            textView_acceleration.setText(acceleration);
            //textView_acceleration.setText("Control: " + control + "\n" + acceleration);

            if ((Math.abs(x)<0.3) && (Math.abs(y)<0.6) && (9.7<Math.abs(z) && Math.abs(z)<9.95))
                control++;
            else
                control = 0;

            if (control > 20) {  // For make time 5 seconds I choosed 20 as control times of AccelerationSensor
                Toast.makeText(SensorActivity.this, "App is closing...", Toast.LENGTH_SHORT).show();

                // A delay for closing app after phone is on the desk 5 seconds without movement
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeApp();
                    }
                }, 1000);
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }


    // A function which close the app
    public void closeApp() {
        finishAffinity();
        System.exit(0);
    }

}
