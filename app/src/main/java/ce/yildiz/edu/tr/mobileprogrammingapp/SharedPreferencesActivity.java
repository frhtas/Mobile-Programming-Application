package ce.yildiz.edu.tr.mobileprogrammingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


// An activity which handle user settings with Shared Preferences
public class SharedPreferencesActivity extends AppCompatActivity {
    EditText editText_username, editText_age, editText_height, editText_weight;
    RadioGroup radioGroup_gender, radioGroup_language;
    Switch switch_mode;
    Button button_save;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "usernameKey";
    public static final String AGE = "ageKey";
    public static final String HEIGHT = "heightKey";
    public static final String WEIGHT = "weightKey";
    public static final String GENDER = "genderKey";
    public static final String LANGUAGE = "languageKey";
    public static final String MODE = "modeKey";

    private String username, age, height, weight;
    private int genderID, languageID;
    private Boolean mode;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        editText_username = (EditText)findViewById(R.id.editText_username);
        editText_username.setEnabled(false);

        editText_age = (EditText)findViewById(R.id.editText_age);
        editText_height = (EditText)findViewById(R.id.editText_height);
        editText_weight = (EditText)findViewById(R.id.editText_weight);
        radioGroup_gender = (RadioGroup)findViewById(R.id.radioGroup_gender);
        radioGroup_language = (RadioGroup)findViewById(R.id.radioGroup_language);
        switch_mode = (Switch)findViewById(R.id.switch_mode);
        button_save = (Button)findViewById(R.id.button_save);


        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        loadData();
        updateViews();
    }


    // A function which saving User Settings to the Shared Preferences
    public void saveData() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        age  = editText_age.getText().toString();
        height  = editText_height.getText().toString();
        weight  = editText_weight.getText().toString();
        genderID = radioGroup_gender.getCheckedRadioButtonId();
        languageID = radioGroup_language.getCheckedRadioButtonId();
        mode = switch_mode.isChecked();
        appMode(mode);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(AGE, age);
        editor.putString(HEIGHT, height);
        editor.putString(WEIGHT, weight);
        editor.putInt(GENDER, genderID);
        editor.putInt(LANGUAGE, languageID);
        editor.putBoolean(MODE, mode);

        editor.apply();
        Toast.makeText(SharedPreferencesActivity.this,"Shared preferences are saved.",Toast.LENGTH_LONG).show();
    }


    // A function which getting User Settings from Shared Preferences
    public void loadData() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        username = sharedpreferences.getString(USERNAME, "");
        age = sharedpreferences.getString(AGE, "");
        height = sharedpreferences.getString(HEIGHT, "");
        weight = sharedpreferences.getString(WEIGHT, "");
        genderID = sharedpreferences.getInt(GENDER, 0);
        languageID = sharedpreferences.getInt(LANGUAGE, 0);
        mode = sharedpreferences.getBoolean(MODE, false);
    }


    // A function which update views after getting User Settings from Shared Preferences
    public void updateViews() {
        editText_username.setText(username);
        editText_age.setText(age);
        editText_height.setText(height);
        editText_weight.setText(weight);
        radioGroup_gender.check(genderID);
        radioGroup_language.check(languageID);
        switch_mode.setChecked(mode);
    }


    // A function which determine the app mode by User Settings from Shared Preferences
    public void appMode(Boolean mode) {
        if (mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

}
