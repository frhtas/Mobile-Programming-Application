package ce.yildiz.edu.tr.mobileprogrammingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    EditText username, password;
    Button signupButton;

    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = (EditText) findViewById(R.id.username_edittext_signup);
        password = (EditText) findViewById(R.id.password_edittext_signup);
        signupButton = (Button) findViewById(R.id.signup_button);
        
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString().trim();
                tryToSignup(userName);
            }
        });

        databaseHelper = new DatabaseHelper(this);
        user = new User();
    }


    // A function which trying to SignUp by control the database
    private void tryToSignup(String userName) {
        if (!databaseHelper.checkUser(userName)) {

            user.setUsername(userName);
            user.setPassword(password.getText().toString().trim());

            databaseHelper.addUser(user);

            // Toast to show success message that record saved successfully, and go to LoginScreen activity
            Toast.makeText(this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(SignupActivity.this, LoginScreen.class);
            startActivity(loginIntent);
            finish();
        }
        else {
            // Toast to show error message that record already exists
            Toast.makeText(this, "This username exists, try another username!", Toast.LENGTH_SHORT).show();
            username.setText("");
            password.setText("");
        }
    }
}
