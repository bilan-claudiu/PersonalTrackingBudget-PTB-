package com.example.pbt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anychart.AnyChartView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class WeeklyAnalyticsActivity extends AppCompatActivity {

    private Toolbar settingsToolbar;

    private FirebaseAuth mAuth;
    private String onlineUserId = "";
    private DatabaseReference expensesRef, personalRef;

    private TextView totalBudgetAmountTextView,
            analyticsTransportAmount,
            analyticsFoodAmount,
            analyticsHouseExpensesAmount,
            analyticsEntertainmentAmount,
            analyticsEducationAmount,
            analyticsCharityAmount,
            analyticsApparelAmount,
            analyticsHealthAmount,
            analyticsOtherAmount,
            monthlySpentAmount;

    private RelativeLayout relativeLayoutTransport,
            relativeLayoutFood,
            relativeLayoutHouse,
            relativeLayoutEntertainment,
            relativeLayoutEducation,
            relativeLayoutCharity,
            relativeLayoutApparel,
            relativeLayoutHealth,
            relativeLayoutOther,
            relativeLayoutAnalysis;

    private AnyChartView anyChartView;

    private TextView progress_ratio_transport,
            progress_ratio_food,
            progress_ratio_house,
            progress_ratio_ent,
            progress_ratio_edu,
            progress_ratio_cha,
            progress_ratio_app,
            progress_ratio_hea,
            progress_ratio_oth,
            monthRatioSpending;

    private ImageView status_Image_transport,
            status_Image_food,
            status_Image_house,
            status_Image_ent,
            status_Image_edu,
            status_Image_cha,
            status_Image_app,
            status_Image_hea,
            status_Image_oth,
            monthRatioSpending_Image;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_analytics);
        settingsToolbar = findViewById(R.id.my_Feed_Toolbar);
        setSupportActionBar(settingsToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Analiza saptamanala");


        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        expensesRef = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        personalRef = FirebaseDatabase.getInstance().getReference("personal").child(onlineUserId);

        totalBudgetAmountTextView = findViewById(R.id.totalBudgetAmountTextView);



        monthlySpentAmount = findViewById(R.id.monthlySpentAmount);
        relativeLayoutAnalysis = findViewById(R.id.relativeLayoutAnalysis);
        monthRatioSpending_Image = findViewById(R.id.monthRatioSpending_Image);
        monthRatioSpending = findViewById(R.id.monthRatioSpending);


        //initializations for TextView's
        totalBudgetAmountTextView = findViewById(R.id.totalBudgetAmountTextView);
        analyticsTransportAmount = findViewById(R.id.analyticsTransportAmount);
        analyticsFoodAmount = findViewById(R.id.analyticsFoodAmount);
        analyticsHouseExpensesAmount = findViewById(R.id.analyticsHouseExpensesAmount);
        analyticsEntertainmentAmount = findViewById(R.id.analyticsEntertainmentAmount);
        analyticsEducationAmount = findViewById(R.id.analyticsEducationAmount);
        analyticsCharityAmount = findViewById(R.id.analyticsCharityAmount);
        analyticsApparelAmount = findViewById(R.id.analyticsApparelAmount);
        analyticsHealthAmount = findViewById(R.id.analyticsHealthAmount);
        analyticsOtherAmount = findViewById(R.id.analyticsOtherAmount);


        progress_ratio_transport = findViewById(R.id.progress_ratio_transport);
        progress_ratio_food = findViewById(R.id.progress_ratio_food);
        progress_ratio_house = findViewById(R.id.progress_ratio_house);
        progress_ratio_ent = findViewById(R.id.progress_ratio_ent);
        progress_ratio_edu = findViewById(R.id.progress_ratio_edu);
        progress_ratio_cha = findViewById(R.id.progress_ratio_cha);
        progress_ratio_app = findViewById(R.id.progress_ratio_app);
        progress_ratio_hea = findViewById(R.id.progress_ratio_hea);
        progress_ratio_oth = findViewById(R.id.progress_ratio_oth);


        //initializations for relative layout's
        relativeLayoutTransport = findViewById(R.id.relativeLayoutTransport);
        relativeLayoutFood = findViewById(R.id.relativeLayoutFood);
        relativeLayoutHouse = findViewById(R.id.relativeLayoutHouse);
        relativeLayoutEntertainment = findViewById(R.id.relativeLayoutEntertainment);
        relativeLayoutEducation = findViewById(R.id.relativeLayoutEducation);
        relativeLayoutCharity = findViewById(R.id.relativeLayoutCharity);
        relativeLayoutApparel = findViewById(R.id.relativeLayoutApparel);
        relativeLayoutHealth = findViewById(R.id.relativeLayoutHealth);
        relativeLayoutOther = findViewById(R.id.relativeLayoutOther);
        relativeLayoutAnalysis = findViewById(R.id.relativeLayoutAnalysis);


        //initializations for ImageView's
        status_Image_transport = findViewById(R.id.status_Image_transport);
        status_Image_food = findViewById(R.id.status_Image_food);
        status_Image_house = findViewById(R.id.status_Image_house);
        status_Image_ent = findViewById(R.id.status_Image_ent);
        status_Image_edu = findViewById(R.id.status_Image_edu);
        status_Image_cha = findViewById(R.id.status_Image_cha);
        status_Image_app = findViewById(R.id.status_Image_app);
        status_Image_hea = findViewById(R.id.status_Image_hea);
        status_Image_oth = findViewById(R.id.status_Image_oth);
        monthRatioSpending_Image = findViewById(R.id.monthRatioSpending_Image);


        //initialization for anyChartView
        anyChartView = findViewById(R.id.anyChartView);
    }
}