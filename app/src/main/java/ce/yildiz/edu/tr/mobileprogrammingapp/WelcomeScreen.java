package ce.yildiz.edu.tr.mobileprogrammingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeScreen extends AppCompatActivity {
    TextView textView_welcomeScreen, textView_by;
    ImageView imageView_welcomeScreen;
    private static int TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        textView_welcomeScreen = (TextView)findViewById(R.id.textView_welcomeScreen);
        imageView_welcomeScreen = (ImageView)findViewById(R.id.imageView_welcomeScreen);
        textView_by = (TextView)findViewById(R.id.textView_by);

        // When app starting again, choose the mode with using SharedPreferences
        final SharedPreferencesActivity sharedPreferencesActivity = new SharedPreferencesActivity();
        final SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
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
                startActivity(intent);
                WelcomeScreen.this.finish();
            }
        }, TIME);
    }

}
