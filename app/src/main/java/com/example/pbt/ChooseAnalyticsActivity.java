package com.example.pbt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ChooseAnalyticsActivity extends AppCompatActivity {
    private CardView todayCardView, weekCardView, monthCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_analytics);

        todayCardView = findViewById(R.id.todayCardView);
        weekCardView = findViewById(R.id.weekCardView);
        monthCardView = findViewById(R.id.monthCardView);



        todayCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseAnalyticsActivity.this, DailyAnalyticsActivity.class);
                startActivity(intent);
            }
        });


        weekCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseAnalyticsActivity.this, WeeklyAnalyticsActivity.class);
                startActivity(intent);
            }
        });


        monthCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseAnalyticsActivity.this, MonthlyAnalyticsActivity.class);
                startActivity(intent);
            }
        });
    }
}