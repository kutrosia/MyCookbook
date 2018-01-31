package com.pwr.mycookbook.ui.timer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ikovac.timepickerwithseconds.MyTimePickerDialog;
import com.ikovac.timepickerwithseconds.TimePicker;
import com.pwr.mycookbook.R;
import com.pwr.mycookbook.ui.settings.SettingsActivity;

import java.util.concurrent.TimeUnit;

/**
 * Created by olaku on 29.01.2018.
 */

public class TimerActivity extends AppCompatActivity {

    private TextView timer_value;
    private Button start_button;
    private Button stop_button;
    private Button pause_button;
    private Button timer_button;
    private CountDownTimer countDownTimer;
    private long timeMilis;
    private long savedInstance;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyStyle();
        setContentView(R.layout.activity_timer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        timer_value = findViewById(R.id.timer_value);
        start_button = findViewById(R.id.button_start);
        stop_button = findViewById(R.id.button_stop);
        pause_button = findViewById(R.id.button_pause);
        timer_button = findViewById(R.id.timer_button);

        timer_value.setText("00:00:00");
        start_button.setOnClickListener(onStartButtonClick());
        stop_button.setOnClickListener(onStopButtonClick());
        pause_button.setOnClickListener(onPauseButtonClick());
        timer_button.setOnClickListener(onTimerButtonClick());

        start_button.setEnabled(false);
        pause_button.setEnabled(false);
        stop_button.setEnabled(false);
    }

    private void applyStyle() {
        String color = sharedPreferences.getString(SettingsActivity.KEY_APPEARANCE_COLOR, "");
        switch (color){
            case "1":
                getTheme().applyStyle(R.style.AppTheme, true);
                break;
            case "2":
                getTheme().applyStyle(R.style.OverlayPrimaryColorGreen, true);
                break;
            case "3":
                getTheme().applyStyle(R.style.OverlayPrimaryColorBlue, true);
                break;
            case "4":
                getTheme().applyStyle(R.style.OverlayPrimaryColorRed, true);
                break;
        }
    }

    private View.OnClickListener onTimerButtonClick() {
        return  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTimePickerDialog mTimePicker = new MyTimePickerDialog(TimerActivity.this, new MyTimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds) {
                        timeMilis = (hourOfDay*3600 + minute*60 + seconds)*1000;
                        setTimerValue(timeMilis);
                        start_button.setEnabled(true);
                    }

                }, 0, 0, 0, true);
                mTimePicker.show();
            }
        };
    }

    private View.OnClickListener onStartButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(savedInstance > 0){
                    start_button.setEnabled(false);
                    pause_button.setEnabled(true);
                    stop_button.setEnabled(true);
                    countDownTimer = createNewCounterDownTimer(savedInstance).start();
                }else{
                    start_button.setEnabled(false);
                    pause_button.setEnabled(true);
                    stop_button.setEnabled(true);
                    countDownTimer = createNewCounterDownTimer(timeMilis);
                    countDownTimer.start();

                }

            }
        };
    }

    private View.OnClickListener onStopButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_button.setEnabled(false);
                stop_button.setEnabled(false);
                pause_button.setEnabled(false);
                countDownTimer.cancel();
                savedInstance = 0;
                timer_value.setText("00:00:00");
            }
        };
    }

    private View.OnClickListener onPauseButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause_button.setEnabled(false);
                stop_button.setEnabled(true);
                start_button.setEnabled(true);
                countDownTimer.cancel();

            }
        };
    }

    private CountDownTimer createNewCounterDownTimer(long milis){
        return new CountDownTimer(milis, 1000) {
            @Override
            public void onTick(long l) {
                savedInstance = l;
                setTimerValue(l);
            }

            @Override
            public void onFinish() {
                timer_value.setText("00:00:00");
                savedInstance = 0;
                start_button.setEnabled(false);
                pause_button.setEnabled(false);
                stop_button.setEnabled(false);
                showNotification();
            }
        };
    }

    private void showNotification() {
        Toast.makeText(getApplicationContext(), "Czas gotowania zakończony!", Toast.LENGTH_LONG).show();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(TimerActivity.this, AlarmReicver.class);
        PendingIntent pIntent = PendingIntent.getActivity(TimerActivity.this, 0, intent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Gotowe!")
                .setContentText("Czas przygotowania zakończył się!")
                .setSmallIcon(R.drawable.dossier_25)
                .setContentIntent(pIntent)
                .setSound(soundUri)
                .setAutoCancel(true)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, notification);
    }

    private void setTimerValue(long milis){
        String value = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milis),
                TimeUnit.MILLISECONDS.toMinutes(milis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(milis) % TimeUnit.MINUTES.toSeconds(1));
        timer_value.setText(value);
    }
}
