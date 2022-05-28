package com.example.pbt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Analiza saptamanala");


        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        expensesRef = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        personalRef = FirebaseDatabase.getInstance().getReference("personal").child(onlineUserId);

        totalBudgetAmountTextView = findViewById(R.id.totalBudgetAmountTextView);


        monthlySpentAmount = findViewById(R.id.monthlySpentAmount);
        relativeLayoutAnalysis = findViewById(R.id.relativeLayoutAnalysis);
        monthRatioSpending = findViewById(R.id.monthRatioSpending);
        monthRatioSpending_Image = findViewById(R.id.monthRatioSpending_Image);


        //initializations for TextView's
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


        //initialization for anyChartView
        anyChartView = findViewById(R.id.anyChartView);

        getTotalWeekTransportExpenses();
        getTotalWeekFoodExpenses();
        getTotalWeekHouseExpenses();
        getTotalWeekEntertainnmentExpenses();
        getTotalWeekEducationExpenses();
        getTotalWeekCharityExpenses();
        getTotalWeekApparelExpenses();
        getTotalWeekHealthExpenses();
        getTotalWeekOtherExpenses();
        getTotalWeekSpending();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        loadGrapph();
                        setStatusAndImageResurce();
                    }
                },
                2000
        );


    }

    private void getTotalWeekTransportExpenses() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        String itemNweek = "Transport" + weeks.getWeeks();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        Query query = reference.orderByChild("itemNweek").equalTo(itemNweek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsTransportAmount.setText("Cheltuiti: " + totalAmount);
                    }
                    personalRef.child("weekTrans").setValue(totalAmount);
                } else {
                    relativeLayoutTransport.setVisibility(View.GONE);
                    personalRef.child("weekTrans").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalWeekFoodExpenses() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        String itemNweek = "Alimente" + weeks.getWeeks();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        Query query = reference.orderByChild("itemNweek").equalTo(itemNweek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsFoodAmount.setText("Cheltuiti: " + totalAmount);
                    }
                    personalRef.child("weekFood").setValue(totalAmount);
                } else {
                    relativeLayoutFood.setVisibility(View.GONE);
                    personalRef.child("weekFood").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getTotalWeekEntertainnmentExpenses() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        String itemNweek = "Entertainment" + weeks.getWeeks();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        Query query = reference.orderByChild("itemNweek").equalTo(itemNweek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsEntertainmentAmount.setText("Cheltuiti: " + totalAmount);
                    }
                    personalRef.child("weekEnt").setValue(totalAmount);
                } else {
                    relativeLayoutEntertainment.setVisibility(View.GONE);
                    personalRef.child("weekEnt").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalWeekHouseExpenses() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        String itemNweek = "Facturi/Casa" + weeks.getWeeks();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        Query query = reference.orderByChild("itemNweek").equalTo(itemNweek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsHouseExpensesAmount.setText("Cheltuiti: " + totalAmount);
                    }
                    personalRef.child("weekHouse").setValue(totalAmount);
                } else {
                    relativeLayoutHouse.setVisibility(View.GONE);
                    personalRef.child("weekHouse").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalWeekEducationExpenses() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        String itemNweek = "Educatie" + weeks.getWeeks();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        Query query = reference.orderByChild("itemNweek").equalTo(itemNweek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsEducationAmount.setText("Cheltuiti: " + totalAmount);
                    }
                    personalRef.child("weekEdu").setValue(totalAmount);
                } else {
                    relativeLayoutEducation.setVisibility(View.GONE);
                    personalRef.child("weekEdu").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalWeekCharityExpenses() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        String itemNweek = "Caritate" + weeks.getWeeks();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        Query query = reference.orderByChild("itemNweek").equalTo(itemNweek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsCharityAmount.setText("Cheltuiti: " + totalAmount);
                    }
                    personalRef.child("weekCha").setValue(totalAmount);
                } else {
                    relativeLayoutCharity.setVisibility(View.GONE);
                    personalRef.child("weekCha").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalWeekApparelExpenses() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        String itemNweek = "Imbracaminte/Incaltaminte" + weeks.getWeeks();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        Query query = reference.orderByChild("itemNweek").equalTo(itemNweek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsApparelAmount.setText("Cheltuiti: " + totalAmount);
                    }
                    personalRef.child("weekApp").setValue(totalAmount);
                } else {
                    relativeLayoutApparel.setVisibility(View.GONE);
                    personalRef.child("weekApp").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalWeekHealthExpenses() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        String itemNweek = "Sanatate" + weeks.getWeeks();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        Query query = reference.orderByChild("itemNweek").equalTo(itemNweek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsHealthAmount.setText("Cheltuiti: " + totalAmount);
                    }
                    personalRef.child("weekHea").setValue(totalAmount);
                } else {
                    relativeLayoutHealth.setVisibility(View.GONE);
                    personalRef.child("weekHea").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalWeekOtherExpenses() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        String itemNweek = "Altele" + weeks.getWeeks();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        Query query = reference.orderByChild("itemNweek").equalTo(itemNweek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsOtherAmount.setText("Cheltuiti: " + totalAmount);
                    }
                    personalRef.child("weekOther").setValue(totalAmount);
                } else {
                    relativeLayoutOther.setVisibility(View.GONE);
                    personalRef.child("weekOther").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalWeekSpending() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(onlineUserId);
        Query query = reference.orderByChild("week").equalTo(weeks.getWeeks());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                    }
                    totalBudgetAmountTextView.setText("Buget saptamanal cheltuit: " + totalAmount + " RON");
                    monthlySpentAmount.setText(" Toatal cheltuit: " + totalAmount + " RON");
                } else {
                    anyChartView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadGrapph() {

        personalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int traTotal;
                    if (snapshot.hasChild("weekTrans")) {
                        traTotal = Integer.parseInt(snapshot.child("weekTrans").getValue().toString());
                    } else {
                        traTotal = 0;
                    }

                    int foodTotal;
                    if (snapshot.hasChild("weekFood")) {
                        foodTotal = Integer.parseInt(snapshot.child("weekFood").getValue().toString());
                    } else {
                        foodTotal = 0;
                    }

                    int houseTotal;
                    if (snapshot.hasChild("weekHouse")) {
                        houseTotal = Integer.parseInt(snapshot.child("weekHouse").getValue().toString());
                    } else {
                        houseTotal = 0;
                    }

                    int entTotal;
                    if (snapshot.hasChild("weekEnt")) {
                        entTotal = Integer.parseInt(snapshot.child("weekEnt").getValue().toString());
                    } else {
                        entTotal = 0;
                    }

                    int eduTotal;
                    if (snapshot.hasChild("weekEdu")) {
                        eduTotal = Integer.parseInt(snapshot.child("weekEdu").getValue().toString());
                    } else {
                        eduTotal = 0;
                    }

                    int chaTotal;
                    if (snapshot.hasChild("weekCha")) {
                        chaTotal = Integer.parseInt(snapshot.child("weekCha").getValue().toString());
                    } else {
                        chaTotal = 0;
                    }

                    int appTotal;
                    if (snapshot.hasChild("weekApp")) {
                        appTotal = Integer.parseInt(snapshot.child("weekApp").getValue().toString());
                    } else {
                        appTotal = 0;
                    }

                    int heaTotal;
                    if (snapshot.hasChild("weekHea")) {
                        heaTotal = Integer.parseInt(snapshot.child("weekHea").getValue().toString());
                    } else {
                        heaTotal = 0;
                    }

                    int othTotal;
                    if (snapshot.hasChild("weekOther")) {
                        othTotal = Integer.parseInt(snapshot.child("weekOther").getValue().toString());
                    } else {
                        othTotal = 0;
                    }


                    Pie pie = AnyChart.pie();
                    List<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry("Transport", traTotal));
                    data.add(new ValueDataEntry("Alimente", foodTotal));
                    data.add(new ValueDataEntry("Facturi/Casa", houseTotal));
                    data.add(new ValueDataEntry("Entertainment", entTotal));
                    data.add(new ValueDataEntry("Educatie", eduTotal));
                    data.add(new ValueDataEntry("Caritate", chaTotal));
                    data.add(new ValueDataEntry("Imbracaminte/Incaltaminte", appTotal));
                    data.add(new ValueDataEntry("Sanatate", heaTotal));
                    data.add(new ValueDataEntry("Altele", othTotal));

                    pie.data(data);
                    pie.title("Cheltuieli saptamanale");
                    pie.labels().position("outside");
                    pie.legend().title().enabled(true);
                    pie.legend().title()
                            .text("cheltuit pe:  ")
                            .padding(0d, 0d, 10d, 0d);
                    pie.legend()
                            .position("center-bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL)
                            .align(Align.CENTER);
                    anyChartView.setChart(pie);

                } else {
                    Toast.makeText(WeeklyAnalyticsActivity.this, "Categorie de buget inexistenta", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setStatusAndImageResurce() {
        personalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    float traTotal;
                    if (snapshot.hasChild(("weekTrans"))) {
                        traTotal = Integer.parseInt(snapshot.child("weekTrans").getValue().toString());
                    } else {
                        traTotal = 0;
                    }

                    float foodTotal;
                    if (snapshot.hasChild(("weekFood"))) {
                        foodTotal = Integer.parseInt(snapshot.child("weekFood").getValue().toString());
                    } else {
                        foodTotal = 0;
                    }

                    float houseTotal;
                    if (snapshot.hasChild(("weekHouse"))) {
                        houseTotal = Integer.parseInt(snapshot.child("weekHouse").getValue().toString());
                    } else {
                        houseTotal = 0;
                    }

                    float entTotal;
                    if (snapshot.hasChild(("weekEnt"))) {
                        entTotal = Integer.parseInt(snapshot.child("weekCha").getValue().toString());
                    } else {
                        entTotal = 0;
                    }

                    float eduTotal;
                    if (snapshot.hasChild(("weekEnt"))) {
                        eduTotal = Integer.parseInt(snapshot.child("weekCha").getValue().toString());
                    } else {
                        eduTotal = 0;
                    }

                    float chaTotal;
                    if (snapshot.hasChild(("weekEdu"))) {
                        chaTotal = Integer.parseInt(snapshot.child("weekEdu").getValue().toString());
                    } else {
                        chaTotal = 0;
                    }

                    float appTotal;
                    if (snapshot.hasChild(("weekApp"))) {
                        appTotal = Integer.parseInt(snapshot.child("weekApp").getValue().toString());
                    } else {
                        appTotal = 0;
                    }

                    float heaTotal;
                    if (snapshot.hasChild(("weekHea"))) {
                        heaTotal = Integer.parseInt(snapshot.child("weekHea").getValue().toString());
                    } else {
                        heaTotal = 0;
                    }

                    float othTotal;
                    if (snapshot.hasChild(("weekOther"))) {
                        othTotal = Integer.parseInt(snapshot.child("weekOther").getValue().toString());
                    } else {
                        othTotal = 0;
                    }

                    float monthTotalSpentAmount;
                    if (snapshot.hasChild(("week"))) {
                        monthTotalSpentAmount = Integer.parseInt(snapshot.child("week").getValue().toString());
                    } else {
                        monthTotalSpentAmount = 0;
                    }


                    float traRatio;
                    if (snapshot.hasChild("weekTransRatio")) {
                        traRatio = Integer.parseInt(snapshot.child("weekTransRatio").getValue().toString());
                    } else {
                        traRatio = 0;
                    }


                    float foodRatio;
                    if (snapshot.hasChild("weekFoodRatio")) {
                        foodRatio = Integer.parseInt(snapshot.child("weekFoodRatio").getValue().toString());
                    } else {
                        foodRatio = 0;
                    }

                    float houseRatio;
                    if (snapshot.hasChild("weekHouseRatio")) {
                        houseRatio = Integer.parseInt(snapshot.child("weekHouseRatio").getValue().toString());
                    } else {
                        houseRatio = 0;
                    }

                    float entRatio;
                    if (snapshot.hasChild("weekEntRatio")) {
                        entRatio = Integer.parseInt(snapshot.child("weekEntRatio").getValue().toString());
                    } else {
                        entRatio = 0;
                    }

                    float eduRatio;
                    if (snapshot.hasChild("weekEduRatio")) {
                        eduRatio = Integer.parseInt(snapshot.child("weekEduRatio").getValue().toString());
                    } else {
                        eduRatio = 0;
                    }

                    float chaRatio;
                    if (snapshot.hasChild("weekChaRatio")) {
                        chaRatio = Integer.parseInt(snapshot.child("weekChaRatio").getValue().toString());
                    } else {
                        chaRatio = 0;
                    }

                    float appRatio;
                    if (snapshot.hasChild("weekAppRatio")) {
                        appRatio = Integer.parseInt(snapshot.child("weekAppRatio").getValue().toString());
                    } else {
                        appRatio = 0;
                    }

                    float heaRatio;
                    if (snapshot.hasChild("weekHealthRatio")) {
                        heaRatio = Integer.parseInt(snapshot.child("weekHealthRatio").getValue().toString());
                    } else {
                        heaRatio = 0;
                    }

                    float othRatio;
                    if (snapshot.hasChild("weekOtherRatio")) {
                        othRatio = Integer.parseInt(snapshot.child("weekOtherRatio").getValue().toString());
                    } else {
                        othRatio = 0;
                    }

                    float monthTotalSpentAmountRatio;
                    if (snapshot.hasChild("weeklyBudget")) {
                        monthTotalSpentAmountRatio = Integer.parseInt(snapshot.child("weeklyBudget").getValue().toString());
                    } else {
                        monthTotalSpentAmountRatio = 0;
                    }

                    float monthPercent = (monthTotalSpentAmount / monthTotalSpentAmountRatio) * 100;
                    if (monthPercent < 50) {
                        monthRatioSpending.setText(monthPercent + " % folositi din " + (int) monthTotalSpentAmountRatio + " RON/zi. Status:");
                        monthRatioSpending_Image.setImageResource(R.drawable.green);
                    } else if (monthPercent >= 50 && monthPercent <= 100) {
                        monthRatioSpending.setText(monthPercent + " % folositi din " + (int) monthTotalSpentAmountRatio + " RON/zi. Status:");
                        monthRatioSpending_Image.setImageResource(R.drawable.yellow);
                    } else {
                        monthRatioSpending.setText(monthPercent + " % folositi din " + (int) monthTotalSpentAmountRatio + " RON/zi. Status:");
                        monthRatioSpending_Image.setImageResource(R.drawable.red);

                    }

                    float transportPercent = (traTotal / traRatio) * 100;
                    if (transportPercent < 50) {
                        progress_ratio_transport.setText(transportPercent + " % folositi din" + (int) traRatio + " RON/zi. Status:");
                        status_Image_transport.setImageResource(R.drawable.green);
                    } else if (transportPercent >= 50 && transportPercent <= 100) {
                        progress_ratio_transport.setText(transportPercent + " % folositi din" + (int) traRatio + " RON/zi. Status:");
                        status_Image_transport.setImageResource(R.drawable.yellow);
                    } else {
                        progress_ratio_transport.setText(transportPercent + "  % folositi din" + (int) traRatio + " RON/zi. Status:");
                        status_Image_transport.setImageResource(R.drawable.red);

                    }

                    float foodPercent = (foodTotal / foodRatio) * 100;
                    if (foodPercent < 50) {
                        progress_ratio_food.setText(foodPercent + " % folositi din " + (int) foodRatio + " RON/zi. Status:");
                        status_Image_food.setImageResource(R.drawable.green);
                    } else if (foodPercent >= 50 && foodPercent <= 100) {
                        progress_ratio_food.setText(foodPercent + " % folositi din " + (int) foodRatio + " RON/zi. Status:");
                        status_Image_food.setImageResource(R.drawable.yellow);
                    } else {
                        progress_ratio_food.setText(foodPercent + " % folositi din " + (int) foodRatio + " RON/zi. Status:");
                        status_Image_food.setImageResource(R.drawable.red);

                    }

                    float housePercent = (houseTotal / houseRatio) * 100;
                    if (housePercent < 50) {
                        progress_ratio_house.setText(housePercent + " % folositi din " + (int) houseRatio + " RON/zi. Status::");
                        status_Image_house.setImageResource(R.drawable.green);
                    } else if (housePercent >= 50 && housePercent <= 100) {
                        progress_ratio_house.setText(housePercent + " % folositi din " + (int) houseRatio + " RON/zi. Status:");
                        status_Image_house.setImageResource(R.drawable.yellow);
                    } else {
                        progress_ratio_house.setText(housePercent + " % folositi din " + (int) houseRatio + " RON/zi. Status::");
                        status_Image_house.setImageResource(R.drawable.red);

                    }

                    float entPercent = (entTotal / entRatio) * 100;
                    if (entPercent < 50) {
                        progress_ratio_ent.setText(entPercent + " % folositi din " + (int) entRatio + " RON/zi. Status::");
                        status_Image_ent.setImageResource(R.drawable.green);
                    } else if (entPercent >= 50 && entPercent <= 100) {
                        progress_ratio_ent.setText(entPercent + " % folositi din " + (int) entRatio + " RON/zi. Status:");
                        status_Image_ent.setImageResource(R.drawable.yellow);
                    } else {
                        progress_ratio_ent.setText(entPercent + " % folositi din " + (int) entRatio + " RON/zi. Status:");
                        status_Image_ent.setImageResource(R.drawable.red);

                    }

                    float eduPercent = (eduTotal / eduRatio) * 100;
                    if (eduPercent < 50) {
                        progress_ratio_edu.setText(eduPercent + " % folositi din " + (int) eduRatio + " RON/zi. Status:");
                        status_Image_edu.setImageResource(R.drawable.green);
                    } else if (eduPercent >= 50 && eduPercent <= 100) {
                        progress_ratio_edu.setText(eduPercent + " % folositi din " + (int) eduRatio + " RON/zi. Status:");
                        status_Image_edu.setImageResource(R.drawable.yellow);
                    } else {
                        progress_ratio_edu.setText(eduPercent + " % folositi din " + (int) eduRatio + " RON/zi. Status:");
                        status_Image_edu.setImageResource(R.drawable.red);

                    }

                    float chaPercent = (chaTotal / chaRatio) * 100;
                    if (chaPercent < 50) {
                        progress_ratio_cha.setText(chaPercent + " % folositi din " + (int) chaRatio + " RON/zi. Status:");
                        status_Image_cha.setImageResource(R.drawable.green);
                    } else if (chaPercent >= 50 && chaPercent <= 100) {
                        progress_ratio_cha.setText(chaPercent + " % folositi din " + (int) chaRatio + " RON/zi. Status:");
                        status_Image_cha.setImageResource(R.drawable.yellow);
                    } else {
                        progress_ratio_cha.setText(chaPercent + " % folositi din " + (int) chaRatio + " RON/zi. Status:");
                        status_Image_cha.setImageResource(R.drawable.red);

                    }

                    float appPercent = (appTotal / appRatio) * 100;
                    if (appPercent < 50) {
                        progress_ratio_app.setText(appPercent + " % folositi din " + (int) appRatio + " RON/zi. Status::");
                        status_Image_app.setImageResource(R.drawable.green);
                    } else if (appPercent >= 50 && appPercent <= 100) {
                        progress_ratio_app.setText(appPercent + " % folositi din " + (int) appRatio + " RON/zi. Status:");
                        status_Image_app.setImageResource(R.drawable.yellow);
                    } else {
                        progress_ratio_app.setText(appPercent + " % folositi din " + (int) appRatio + " RON/zi. Status:");
                        status_Image_app.setImageResource(R.drawable.red);

                    }

                    float heaPercent = (heaTotal / heaRatio) * 100;
                    if (heaPercent < 50) {
                        progress_ratio_hea.setText(heaPercent + " % folositi din " + (int) heaRatio + " RON/zi. Status:");
                        status_Image_hea.setImageResource(R.drawable.green);
                    } else if (heaPercent >= 50 && heaPercent <= 100) {
                        progress_ratio_hea.setText(heaPercent + " % folositi din " + (int) heaRatio + " RON/zi. Status:");
                        status_Image_hea.setImageResource(R.drawable.yellow);
                    } else {
                        progress_ratio_hea.setText(heaPercent + " % folositi din " + (int) heaRatio + " RON/zi. Status:");
                        status_Image_hea.setImageResource(R.drawable.red);

                    }


                    float otherPercent = (othTotal / othRatio) * 100;
                    if (otherPercent < 50) {
                        progress_ratio_oth.setText(otherPercent + " % folositi din " + (int) othRatio + " RON/zi. Status:");
                        status_Image_oth.setImageResource(R.drawable.green);
                    } else if (otherPercent >= 50 && otherPercent <= 100) {
                        progress_ratio_oth.setText(otherPercent + " % folositi din " + (int) othRatio + " RON/zi. Status:");
                        status_Image_oth.setImageResource(R.drawable.yellow);
                    } else {
                        progress_ratio_oth.setText(otherPercent + " % folositi din " + (int) othRatio + " RON/zi. Status:");
                        status_Image_oth.setImageResource(R.drawable.red);

                    }

                } else {
                    Toast.makeText(WeeklyAnalyticsActivity.this, "setStatusAndImageResource Errors", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}



