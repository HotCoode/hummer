package com.rise;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.base.L;
import com.rise.common.Const;
import com.rise.component.TimePreference;

import java.util.Calendar;

/**
 * Created by kai.wang on 3/11/14.
 */
public class SettingActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final String ID = "alarm_id";
    private final String TIME = "alarm_time";

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        addPreferencesFromResource(R.layout.activity_setting);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        getListView().setBackgroundResource(R.color.theme_lighter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (Const.PREFERENCE_KEY_REMINDER.equals(key)) {
            boolean reminder = sharedPreferences.getBoolean(Const.PREFERENCE_KEY_REMINDER, false);
            if (reminder) {
                int hour = TimePreference.getHour(sharedPreferences.getString(Const.PREFERENCE_KEY_REMINDER_TIME, ""));
                int minute = TimePreference.getMinute(sharedPreferences.getString(Const.PREFERENCE_KEY_REMINDER_TIME, ""));
                if (hour != 0 || minute != 0) {
                    startAlarm(hour, minute);
                }
            } else {
                stopAlarm();
            }
        } else {
            boolean reminder = sharedPreferences.getBoolean(Const.PREFERENCE_KEY_REMINDER, false);
            if (reminder) {
                int hour = TimePreference.getHour(sharedPreferences.getString(Const.PREFERENCE_KEY_REMINDER_TIME, ""));
                int minute = TimePreference.getMinute(sharedPreferences.getString(Const.PREFERENCE_KEY_REMINDER_TIME, ""));
                if (hour != 0 || minute != 0) {
                    startAlarm(hour, minute);
                }
            }
        }
    }

    private void startAlarm(int hour, int minute) {
        L.i("start alarm");
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AlarmReceiver.ALARM_INTENT);
        intent.setData(Uri.parse("content://calendar/calendar_alerts/1"));
        intent.setClass(this, AlarmReceiver.class);
        intent.putExtra(ID, 1);

        Calendar calendar = Calendar.getInstance();

        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        if (hour < currentHour || (hour == currentHour && minute <= currentMinute)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        long alarmTime = calendar.getTimeInMillis();
        intent.putExtra(TIME, alarmTime);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);
    }


    private void stopAlarm() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AlarmReceiver.ALARM_INTENT);
        intent.setClass(this, AlarmReceiver.class);
        intent.setData(Uri.parse("content://calendar/calendar_alerts/1"));
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_NO_CREATE);
        if (sender != null) {
            L.i("cancel alarm");
            am.cancel(sender);
        }
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(0, R.anim.exit);
    }
}