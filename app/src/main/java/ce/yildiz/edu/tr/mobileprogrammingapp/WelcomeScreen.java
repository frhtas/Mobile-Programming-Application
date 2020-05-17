package ce.yildiz.edu.tr.mobileprogrammingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.DetectedActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

// A welcome screen activity
public class WelcomeScreen extends AppCompatActivity {
    TextView textView_welcomeScreen, textView_by;
    ImageView imageView_welcomeScreen;
    private final static int TIME = 3000;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        textView_welcomeScreen = (TextView)findViewById(R.id.textView_welcomeScreen);
        imageView_welcomeScreen = (ImageView)findViewById(R.id.imageView_welcomeScreen);
        textView_by = (TextView)findViewById(R.id.textView_by);

        // When app starting again, choose the mode with using SharedPreferences
        final SharedPreferencesActivity sharedPreferencesActivity = new SharedPreferencesActivity();
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Boolean mode = sharedPreferences.getBoolean("modeKey", false);
        sharedPreferencesActivity.appMode(mode);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.welcome_animation);

        textView_welcomeScreen.startAnimation(animation);
        imageView_welcomeScreen.startAnimation(animation);
        textView_by.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeScreen.this, LoginScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        }, TIME);
    }


    // An override function which unregister the Broadcast Receiver and start up the ActivityDetectionService service
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG_ACTIVITY", "onStart():start ActivityDetectionService");
        LocalBroadcastManager.getInstance(this).registerReceiver(mActivityBroadcastReceiver,
                new IntentFilter("activity_intent"));

        startService(new Intent(this, ActivityRecognizedService.class));
    }


    BroadcastReceiver mActivityBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("activity_intent")) {
                int type = intent.getIntExtra("type", -1);
                int confidence = intent.getIntExtra("confidence", 0);
                handleUserActivity(type, confidence);
            }
        }
    };

    // A function which handle user activities: walking, still etc.
    private void handleUserActivity(int type, int confidence) {
        sharedPreferences = getSharedPreferences("UserActivities", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String label = "Unknown";
        switch (type) {
            case DetectedActivity.IN_VEHICLE: {
                label = "In_Vehicle";
                int inVehicle = sharedPreferences.getInt("inVehicle", 0);
                if (confidence > 10)
                    inVehicle++;
                editor.putInt("inVehicle", inVehicle).apply();
                break;
            }
            case DetectedActivity.ON_BICYCLE: {
                label = "On_Bicycle";
                int onBicycle = sharedPreferences.getInt("onBicycle", 0);
                if (confidence > 10)
                    onBicycle++;
                editor.putInt("onBicycle", onBicycle).apply();
                break;
            }
            case DetectedActivity.ON_FOOT: {
                label = "On_Foot";
                int onFoot = sharedPreferences.getInt("onFoot", 0);
                if (confidence > 10)
                    onFoot++;
                editor.putInt("onFoot", onFoot).apply();;
            }
            case DetectedActivity.RUNNING: {
                label = "Running";
                int running = sharedPreferences.getInt("running", 0);
                if (confidence > 10)
                    running++;
                editor.putInt("running", running).apply();
                break;
            }
            case DetectedActivity.STILL: {
                label = "Still";
                int still = sharedPreferences.getInt("still", 0);
                if (confidence > 10)
                    still++;
                editor.putInt("still", still).apply();
                break;
            }
            case DetectedActivity.TILTING: {
                label = "Tilting";
                int tilting = sharedPreferences.getInt("tilting", 0);
                if (confidence > 10)
                    tilting++;
                editor.putInt("tilting", tilting).apply();
                break;
            }
            case DetectedActivity.WALKING: {
                label = "Walking";
                int walking = sharedPreferences.getInt("walking", 0);
                if (confidence > 10)
                    walking++;
                editor.putInt("walking", walking).apply();
                break;
            }
            case DetectedActivity.UNKNOWN: {
                int unknown = sharedPreferences.getInt("unknown", 0);
                if (confidence > 10)
                    unknown++;
                editor.putInt("unknown", unknown).apply();;
            }
        }

        Log.d("TAG_ACTIVITY", "broadcast:onReceive(): Activity is " + label
                + " and confidence level is: " + confidence);

    }

}
