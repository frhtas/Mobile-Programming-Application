package ce.yildiz.edu.tr.mobileprogrammingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

// A class which broadcasting for alarm
public class AlarmReceiver extends BroadcastReceiver {
    Ringtone ringtone;
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(10000);
                ringtone.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm time!", Toast.LENGTH_LONG).show();

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        ringtone = RingtoneManager.getRingtone(context, alarmUri);

        ringtone.play();
        thread.start();
    }
}
