package ce.yildiz.edu.tr.mobileprogrammingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;


// Kurulan saatte alarm çalmasını sağlayan aktivite, isteğe göre ON ya da OFF da yapılabilir
public class AlarmActivity extends AppCompatActivity {
    ImageButton imageButton_addAlarm;
    TextView textView_time;
    Switch switch_alarm;

    TimePickerDialog timePickerDialog;
    Calendar calSet;
    int mHour, mMin;
    Boolean alarmSet;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // Alarm saati ve ON mu OFF mu tercihi SharedPreferences'tan alındı
        sharedPreferences = getSharedPreferences("AlarmPrefs", MODE_PRIVATE);
        mHour = sharedPreferences.getInt("mHour", 0);
        mMin = sharedPreferences.getInt("mMin", 0);
        alarmSet = sharedPreferences.getBoolean("alarmSet", false);

        imageButton_addAlarm = (ImageButton) findViewById(R.id.imageButton_addAlarm);
        textView_time = (TextView) findViewById(R.id.textView_time);
        switch_alarm = (Switch) findViewById(R.id.switch_alarm);

        textView_time.setText(editTimeStyle(mHour, mMin));

        if (alarmSet)
            switch_alarm.setChecked(true);
        else
            switch_alarm.setChecked(false);

        switch_alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked)
                    setAlarm();
                else
                    cancelAlarm();
            }
        });

        imageButton_addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPickerDialog();
            }
        });


        textView_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPickerDialogUpdate(mHour, mMin);
            }
        });
    }


    // A function which show the TimePickerDialog to set the time for alarm
    private void openPickerDialog() {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                AlarmActivity.this,
                TimePickerDialog.THEME_HOLO_LIGHT,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        timePickerDialog.setTitle("Set Alarm");

        timePickerDialog.show();
    }

    // A function which show the TimePickerDialog to update the time for alarm
    private void openPickerDialogUpdate(int mHour, int mMin) {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                AlarmActivity.this,
                TimePickerDialog.THEME_HOLO_LIGHT,
                onTimeSetListener,
                mHour,
                mMin,
                true);
        timePickerDialog.setTitle("Set Alarm");

        timePickerDialog.show();
    }


    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMin = minute;

            textView_time.setText(editTimeStyle(mHour, mMin));

            setAlarm();
        }};


    // A function which set the alarm with time from TimePickerDialog, make alarm ON
    private void setAlarm(){
        Calendar calNow = Calendar.getInstance();
        calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY, mHour);
        calSet.set(Calendar.MINUTE, mMin);
        calSet.set(Calendar.SECOND, 0);

        if(calSet.before(calNow)){
            calSet.add(Calendar.DATE, 1);
        }

        String time = mHour + ":" + mMin;
        Toast.makeText(this,"Alarm is ON, set for: " + time, Toast.LENGTH_SHORT).show();
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);

        // Alarm saati ve ON mu OFF mu tercihi SharedPreferences'a kaydedildi
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("mHour", mHour);
        editor.putInt("mMin", mMin);
        editor.putBoolean("alarmSet", true);
        editor.apply();
        switch_alarm.setChecked(true);
    }


    // A function which cancel the exist alarm, make alarm OFF
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("alarmSet", false);
        editor.apply();
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this,"Alarm is OFF!", Toast.LENGTH_SHORT).show();
    }


    // A function which edit the time style
    private String editTimeStyle(int mHour, int mMin) {
        String time = "";
        if ((mHour < 10) && (mMin >= 10))
            time = "0" + mHour + ":" + mMin;
        else if ((mHour >= 10) && (mMin < 10))
            time = mHour + ":" + "0" + mMin;
        else if ((mHour < 10) && (mMin < 10))
            time = "0" + mHour + ":" + "0" + mMin;
        else
            time = mHour + ":" + mMin;

        return time;
    }

}
