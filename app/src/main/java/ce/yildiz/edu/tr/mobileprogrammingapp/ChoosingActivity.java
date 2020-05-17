package ce.yildiz.edu.tr.mobileprogrammingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


// An activity which is a menu for help us to find what we are looking for in the app
public class ChoosingActivity extends AppCompatActivity implements OnClickListener {
    Button button_sendEmail, button_userList, button_userSettings, button_sensorList, button_notes;
    Button button_asyncTask, button_alarm, button_location, button_exit, button_phoneSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing);

        this.getApplicationContext().startService(new Intent(this, MySmsObserver.class));

        button_sendEmail = (Button) findViewById(R.id.button_sendEmail);
        button_userList = (Button) findViewById(R.id.button_userList);
        button_userSettings = (Button) findViewById(R.id.button_userSettings);
        button_sensorList = (Button) findViewById(R.id.button_sensorList);
        button_notes = (Button) findViewById(R.id.button_notes);
        button_asyncTask = (Button) findViewById(R.id.button_asyncTask);
        button_alarm = (Button) findViewById(R.id.button_alarm);
        button_location = (Button) findViewById(R.id.button_location);
        button_phoneSms = (Button) findViewById(R.id.button_phoneSms);
        button_exit = (Button) findViewById(R.id.button_exit);

        button_sendEmail.setOnClickListener(this);
        button_userList.setOnClickListener(this);
        button_userSettings.setOnClickListener(this);
        button_sensorList.setOnClickListener(this);
        button_notes.setOnClickListener(this);
        button_asyncTask.setOnClickListener(this);
        button_alarm.setOnClickListener(this);
        button_location.setOnClickListener(this);
        button_phoneSms.setOnClickListener(this);
        button_exit.setOnClickListener(this);
    }


    // An override function which do something by the Button ID
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sendEmail:
                Intent emailActivityIntent = new Intent(ChoosingActivity.this, EmailActivity.class);
                startActivity(emailActivityIntent);
                break;

            case R.id.button_userList:
                Intent listActivityIntent = new Intent(ChoosingActivity.this, ListActivity.class);
                startActivity(listActivityIntent);
                break;

            case R.id.button_userSettings:
                Intent sharedPreferencesActivityIntent = new Intent(ChoosingActivity.this, SharedPreferencesActivity.class);
                startActivity(sharedPreferencesActivityIntent);
                break;

            case R.id.button_sensorList:
                Intent sensorActivityIntent = new Intent(ChoosingActivity.this, SensorActivity.class);
                startActivity(sensorActivityIntent);
                break;

            case R.id.button_notes:
                Intent notesActivityIntent = new Intent(ChoosingActivity.this, NotesActivity.class);
                startActivity(notesActivityIntent);
                break;

            case R.id.button_asyncTask:
                Intent asyncTaskActivityIntent = new Intent(ChoosingActivity.this, AsyncTaskActivity.class);
                startActivity(asyncTaskActivityIntent);
                break;

            case R.id.button_alarm:
                Intent alarmActivityIntent = new Intent(ChoosingActivity.this, AlarmActivity.class);
                startActivity(alarmActivityIntent);
                break;

            case R.id.button_location:
                Intent locationActivityIntent = new Intent(ChoosingActivity.this, LocationActivity.class);
                startActivity(locationActivityIntent);
                break;

            case R.id.button_phoneSms:
                Intent phoneSmsActivityIntent = new Intent(ChoosingActivity.this, PhoneSmsActivity.class);
                startActivity(phoneSmsActivityIntent);
                break;

            case R.id.button_exit:
                Toast.makeText(ChoosingActivity.this, "App is closing...", Toast.LENGTH_SHORT).show();

                // A delay for closing app with Exit button
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishAffinity();
                        System.exit(0);
                    }
                }, 1000);
                break;

        }
    }
}
