package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvTime;
    ImageButton btnStart, btnHold, btnStop;

    Handler handler = new Handler();
    long startTime = 0L;
    long timeInMillis = 0L;
    boolean isRunning = false;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                timeInMillis = System.currentTimeMillis() - startTime;

                int totalSeconds = (int) (timeInMillis / 1000);
                int hours = totalSeconds / 3600;
                int minutes = (totalSeconds % 3600) / 60;
                int seconds = totalSeconds % 60;
                int milliseconds = (int) ((timeInMillis % 1000) / 10);

                tvTime.setText(String.format(
                        "%02d:%02d:%02d:%02d",
                        hours, minutes, seconds, milliseconds
                ));

                handler.postDelayed(this, 10);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = findViewById(R.id.tvTime);
        btnStart = findViewById(R.id.btnStart);
        btnHold = findViewById(R.id.btnHold);
        btnStop = findViewById(R.id.btnStop);

        btnStart.setOnClickListener(v -> {
            if (!isRunning) {
                startTime = System.currentTimeMillis() - timeInMillis;
                isRunning = true;
                handler.post(runnable);
            }
        });

        btnHold.setOnClickListener(v -> {
            isRunning = false;
            handler.removeCallbacks(runnable);
        });

        btnStop.setOnClickListener(v -> {
            isRunning = false;
            handler.removeCallbacks(runnable);
            timeInMillis = 0L;
            tvTime.setText("00:00:00:00");
        });
    }
}
