package ce.yildiz.edu.tr.mobileprogrammingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


// An activity which show us Incoming and Outgoing Phone Calls and SMS'
public class PhoneSmsActivity extends AppCompatActivity {
    TextView textView_logs;
    Button button_updateLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_sms);

        textView_logs = (TextView) findViewById(R.id.textView_logs);
        textView_logs.setMovementMethod(new ScrollingMovementMethod());
        readLogFile();

        button_updateLog = (Button) findViewById(R.id.button_updateLog);
        button_updateLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readLogFile();
                Toast.makeText(PhoneSmsActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // A function which read the log file for getting Phone Calls and SMS'
    public void readLogFile() {
        //Get the text file
        File dir = getFilesDir();
        File file = new File(dir,"log.txt");

        //Read text from file
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        textView_logs.setText(text.toString());
    }


}
