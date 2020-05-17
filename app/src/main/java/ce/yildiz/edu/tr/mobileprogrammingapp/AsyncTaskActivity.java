package ce.yildiz.edu.tr.mobileprogrammingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

// An activity which we use progress bar for a background work
public class AsyncTaskActivity extends AppCompatActivity {
    Button button_download;
    ProgressBar progressBar;
    ImageView imageView_icon;
    TextView textView_percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        textView_percentage = (TextView) findViewById(R.id.textView_percentage);
        imageView_icon = (ImageView) findViewById(R.id.imageView_icon);
        button_download = (Button) findViewById(R.id.button_download);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setIndeterminate(false);
        progressBar.setProgress(0);
        progressBar.setMax(5);

        button_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView_icon.setVisibility(View.INVISIBLE);
                AsyncTaskExample task = new AsyncTaskExample();
                task.setProgressBar(progressBar);
                task.execute(5);
            }
        });
    }


    private class AsyncTaskExample extends AsyncTask<Integer, Integer, String> {
        ProgressBar pb;
        int status = 0;
        public void setProgressBar(ProgressBar progressBar) {
            this.pb = progressBar;
        }
        @Override
        protected String doInBackground(Integer[] objects) {
            for (int i = 0; i < objects[0]; i++){
                status++;
                try {
                    publishProgress(status);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer[] values) {
            pb.setProgress(values[0]);
            textView_percentage.setText("% " + values[0]*20);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            imageView_icon.setVisibility(View.VISIBLE);
            super.onPostExecute(s);
            ringing();
        }
    }

    // Ringing after downloading
    public void ringing() {
        long ringDelay = 2000;
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final Ringtone alarmRingtone= RingtoneManager.getRingtone(getApplicationContext(), notification);
        alarmRingtone.play();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                alarmRingtone.stop();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, ringDelay);
    }
}
