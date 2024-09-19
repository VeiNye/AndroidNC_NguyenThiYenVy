package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Spinner durationSpinner;
    private Button startButton, stopButton;
    private Vibrator vibrator;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.timePicker);
        durationSpinner = findViewById(R.id.spinner_duration);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVibration();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopVibration();
            }
        });
    }

    private void startVibration() {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        int duration = getSelectedDuration();
        long vibrationTime = duration * 60 * 1000; // chuyển thành milliseconds

        if (vibrator.hasVibrator()) {
            vibrator.vibrate(vibrationTime);
            Toast.makeText(this, "Báo thức sẽ rung trong " + duration + " phút.", Toast.LENGTH_SHORT).show();
        }
    }

    private int getSelectedDuration() {
        String selectedDuration = durationSpinner.getSelectedItem().toString();
        if (selectedDuration.equals("5 minutes")) {
            return 5;
        } else if (selectedDuration.equals("10 minutes")) {
            return 10;
        } else {
            return 15;
        }
    }

    private void stopVibration() {
        if (vibrator.hasVibrator()) {
            vibrator.cancel();
            Toast.makeText(this, "Đã dừng rung báo thức!", Toast.LENGTH_SHORT).show();
        }
    }
}
