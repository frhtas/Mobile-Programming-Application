package ce.yildiz.edu.tr.mobileprogrammingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// A class which broadcasting for Incoming and Outgoing Calls
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
        MySmsObserver mySmsObserver = new MySmsObserver();
        String action = intent.getAction();

        // If it is incoming phone call
        if (action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String state = bundle.getString(TelephonyManager.EXTRA_STATE);
                Log.w("MY_TAG", state);
                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    String phoneNumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    String currentTime = sdf.format(new Date());

                    Log.w("MY_TAG", phoneNumber);
                    mySmsObserver.appendLog(context, "Time: " + currentTime + " | Phone call from: " + phoneNumber);
                    Toast.makeText(context, "Time: " + currentTime + "\nPhone call from: " + phoneNumber, Toast.LENGTH_SHORT).show();
                }
            }
        }

        // If it is outgoing phone call
        if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            String currentTime = sdf.format(new Date());

            Log.w("MY_TAG", phoneNumber);
            mySmsObserver.appendLog(context, "Time: " + currentTime + " | Phone call to: " + phoneNumber);
            Toast.makeText(context, "Time: " + currentTime + "\nPhone call to: " + phoneNumber, Toast.LENGTH_SHORT).show();
        }
    }

}
