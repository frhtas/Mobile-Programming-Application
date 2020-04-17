package ce.yildiz.edu.tr.mobileprogrammingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ChoosingActivity extends AppCompatActivity implements OnClickListener {
    Button button_sendEmail, button_userList, button_userSettings, button_sensorList, button_notes, button_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing);

        button_sendEmail = (Button)findViewById(R.id.button_sendEmail);
        button_userList = (Button)findViewById(R.id.button_userList);
        button_userSettings = (Button)findViewById(R.id.button_userSettings);
        button_sensorList = (Button)findViewById(R.id.button_sensorList);
        button_notes = (Button)findViewById(R.id.button_notes);
        button_exit = (Button)findViewById(R.id.button_exit);

        button_sendEmail.setOnClickListener(this);
        button_userList.setOnClickListener(this);
        button_userSettings.setOnClickListener(this);
        button_sensorList.setOnClickListener(this);
        button_notes.setOnClickListener(this);
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
