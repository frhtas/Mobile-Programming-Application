package ce.yildiz.edu.tr.mobileprogrammingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

// An activity which we can find our location as latitude and longitude and share it via app chooser
public class LocationActivity extends AppCompatActivity {
    TextView textView_location, textView_userActivities;
    ImageButton button_getLocation, button_sendLocation;

    private FusedLocationProviderClient fusedLocationClient;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        textView_userActivities = (TextView) findViewById(R.id.textView_userActivities);
        textView_userActivities.setText(getUserActivities().toString());

        textView_location = (TextView) findViewById(R.id.textView_location);
        button_getLocation = (ImageButton) findViewById(R.id.imageButton_getLocation);
        button_sendLocation = (ImageButton) findViewById(R.id.imageButton_sendLocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got location. But some situations this can be null.
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                });

        button_getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loc = "latitude: " + latitude + "\nlongitude: " + longitude;
                textView_location.setText(loc);
            }
        });

        button_sendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.com/maps?q=" + latitude + "," + longitude;
                Intent locationIntent = new Intent().setAction(Intent.ACTION_SEND);
                locationIntent.setType("text/plain");
                locationIntent.putExtra(Intent.EXTRA_TEXT, uri);
                startActivity(Intent.createChooser(locationIntent, "Share via"));

                // directly share via Whatsapp
                //locationIntent.setPackage("com.whatsapp");
                //startActivity(locationIntent);
            }
        });

    }

    // A function which get user activities: walking, still etc.
    private StringBuilder getUserActivities() {
        StringBuilder userActivities = new StringBuilder();
        SharedPreferences sharedPreferences = getSharedPreferences("UserActivities", MODE_PRIVATE);
        userActivities.append(getString(R.string.still)).append("  ").append(sharedPreferences.getInt("still", 0)).append("\n");
        userActivities.append(getString(R.string.walking)).append("  ").append(sharedPreferences.getInt("walking", 0)).append("\n");
        userActivities.append(getString(R.string.running)).append("  ").append(sharedPreferences.getInt("running", 0)).append("\n");
        userActivities.append(getString(R.string.onfoot)).append("  ").append(sharedPreferences.getInt("onFoot", 0)).append("\n");
        userActivities.append(getString(R.string.invehicle)).append("  ").append(sharedPreferences.getInt("inVehicle", 0)).append("\n");
        userActivities.append(getString(R.string.bicycle)).append("  ").append(sharedPreferences.getInt("onBicycle", 0)).append("\n");
        userActivities.append(getString(R.string.tilting)).append("  ").append(sharedPreferences.getInt("tilting", 0)).append("\n");
        userActivities.append(getString(R.string.unknown_activity)).append("  ").append(sharedPreferences.getInt("unknown", 0));

        return userActivities;
    }
}
