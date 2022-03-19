package com.example.pbt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
    private CardView budgetCardView, todayCardView;
    private ImageView weekBtnImageView, todayBtnImageView, budgetBtnImageView, monthBtnImageView, analitycsImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        budgetCardView = findViewById(R.id.budgetCardView);
        todayCardView = findViewById(R.id.todayCardView);

        weekBtnImageView = findViewById(R.id.weekBtnImageView);
        todayBtnImageView = findViewById(R.id.todayBtnImageView);
        budgetBtnImageView = findViewById(R.id.budgetBtnImageView);
        monthBtnImageView = findViewById(R.id.monthBtnImageView);
        analitycsImageView = findViewById(R.id.analitycsImageView);


//        budgetBtnImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, BudgetActivity.class);
//                startActivity(intent);
//
//            }
//        });

        todayCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TodaySpendingActivity.class);
                startActivity(intent);
            }
        });

        todayBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TodaySpendingActivity.class);
                startActivity(intent);
            }
        });

        weekBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeekSpendingActivity.class);
                intent.putExtra("type", "week");
                startActivity(intent);

            }
        });

        monthBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeekSpendingActivity.class);
                intent.putExtra("type", "mounth");
                startActivity(intent);
            }
        });

        analitycsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseAnalyticsActivity.class);
                startActivity(intent);

            }
        });

    }
}