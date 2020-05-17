package ce.yildiz.edu.tr.mobileprogrammingapp;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import androidx.annotation.Nullable;


// A service class which help us to get Incoming and Outcoming SMS
public class MySmsObserver extends Service {

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        SMSObserver myObserver = new SMSObserver(new Handler());
        ContentResolver contentResolver = this.getApplicationContext().getContentResolver();
        contentResolver.registerContentObserver(Uri.parse("content://sms"), true, myObserver);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class SMSObserver extends ContentObserver {
        private String lastSMSid;

        public SMSObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            Uri smsUri = Uri.parse("content://sms");
            Cursor cursor = getContentResolver().query(smsUri, null, null, null, null);
            if (cursor != null)
                cursor.moveToNext();
            String content = cursor.getString(cursor.getColumnIndex("body"));
            String smsNumber = cursor.getString(cursor.getColumnIndex("address"));
            if (smsNumber == null || smsNumber.length() <= 0)
                smsNumber = "Unknown";
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));

            long dateMillis = cursor.getLong(cursor.getColumnIndex("date"));
            Date dateSms = new Date(dateMillis);

            SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US); // For comparing sms time because of some bugs
            String smsTime = myFormat.format(dateSms);
            String cTime = myFormat.format(new Date());

            SimpleDateFormat actualFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US); // For show the time in the file for action
            String currentTime = actualFormat.format(new Date());

            if (smsTime.equals(cTime)) {
                if(smsChecker(id)) {
                    // Message receive
                    if (type == 1) {
                        appendLog(getBaseContext(), "Time: " + currentTime + " | SMS from: " + smsNumber + " | Content: " + content);
                        Toast.makeText(MySmsObserver.this, "Time: " + currentTime + "\nSMS from: " + smsNumber + "\nContent: " + content, Toast.LENGTH_SHORT).show();
                    }
                    // Message sent
                    else if (type == 2) {
                        appendLog(getBaseContext(), "Time: " + currentTime + " | SMS to: " + smsNumber + " | Content: " + content);
                        Toast.makeText(MySmsObserver.this, "Time: " + currentTime + "\nSMS to: " + smsNumber + "\nContent: " + content, Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
            }

        }


        // A function which handle to get SMS just one time
        public boolean smsChecker(String id) {
            boolean flagSMS = true;

            if (id.equals(lastSMSid)) {
                flagSMS = false;
            }
            else {
                lastSMSid = id;
            }
            //if flagSMS = true, those 2 sms are different
            return flagSMS;
        }
    }


    // A function which add new log to the file
    public void appendLog(Context context, String text) {
        File dir = context.getFilesDir();
        File logFile = new File(dir, "log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
