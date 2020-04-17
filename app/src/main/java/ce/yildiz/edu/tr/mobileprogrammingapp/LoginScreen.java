package ce.yildiz.edu.tr.mobileprogrammingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {
    EditText uname, passwd;
    Button loginButton, tosignup_button;

    private DatabaseHelper databaseHelper;

    private int numberOfTry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);

        uname = (EditText) findViewById(R.id.username_edittext);
        passwd = (EditText) findViewById(R.id.password_edittext);
        loginButton = (Button) findViewById(R.id.login_button);
        tosignup_button = (Button) findViewById(R.id.tosignup_button);

        databaseHelper = new DatabaseHelper(this);

        numberOfTry = 3;
        loginButton.setOnClickListener(this);
        tosignup_button.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeApp();
    }


    // An override function which do something by the button, LOGIN or SIGN UP
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                String username = uname.getText().toString().trim();
                String password = passwd.getText().toString().trim();
                if (!username.equals("") && !password.equals("")) {
                    tryToLogin(username, password);
                }
                break;

            case R.id.tosignup_button:
                Intent signupIntent = new Intent(LoginScreen.this, SignupActivity.class);
                startActivity(signupIntent);
                break;
        }
    }


    // A function which trying to Login by control the database
    public void tryToLogin(String username, String password) {
        if (databaseHelper.checkUser(username, password)) {
            // A message after successfull login
            Toast.makeText(LoginScreen.this, "Login successfully!", Toast.LENGTH_SHORT).show();

            // Getting username directly from LoginScreen with using SharedPreferences
            final SharedPreferencesActivity sharedPreferencesActivity = new SharedPreferencesActivity();
            final SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("usernameKey", username);
            editor.apply();
            Intent choosingIntent = new Intent(LoginScreen.this, ChoosingActivity.class);
            startActivity(choosingIntent);
        }
        else {
            numberOfTry--;
            if (numberOfTry > 0) {
                Toast.makeText(LoginScreen.this, "Wrong Username or Password.\nYou can try " + numberOfTry + " more times!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(LoginScreen.this, "You failed 3 times!\nApp is closing...", Toast.LENGTH_SHORT).show();

                // A delay for closing app after failed 3 login attempts
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeApp();
                    }
                }, 1000);
            }
        }
    }


    // A function which close the app
    public void closeApp() {
        finishAffinity();
        System.exit(0);
    }

}
