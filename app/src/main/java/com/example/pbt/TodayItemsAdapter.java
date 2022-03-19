package com.example.pbt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class TodayItemsAdapter extends RecyclerView.Adapter<TodayItemsAdapter.ViewHolder> {

    private Context mContext;
    private List<Data> myDataList;

    private String post_key = "";
    private String item = "";
    private String note = "";
    private int amount = 0;

    public TodayItemsAdapter(Context mContext, List<Data> myDataList) {
        this.mContext = mContext;
        this.myDataList = myDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.retrive_layout, parent, false);
        return new TodayItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Data data = myDataList.get(position);

        holder.item.setText("Categorie " + data.getItem());
        holder.amount.setText("Suma " + data.getAmount());
        holder.date.setText("La data " + data.getDate());
        holder.notes.setText("Detalii: " + data.getNotes());

        switch (data.getItem()) {
            case "Transport":
                holder.imageView.setImageResource(R.drawable.transport);
                break;
            case "Facturi/Casa":
                holder.imageView.setImageResource(R.drawable.house);
                break;
            case "Entertainment":
                holder.imageView.setImageResource(R.drawable.enter);
                break;
            case "Caritate":
                holder.imageView.setImageResource(R.drawable.ic_consultancy);
                break;
            case "Imbracaminte/Incaltaminte":
                holder.imageView.setImageResource(R.drawable.ic_shirt);
                break;
            case "Sanatate":
                holder.imageView.setImageResource(R.drawable.health);
                break;
            case "Educatie":
                holder.imageView.setImageResource(R.drawable.education);
                break;
            case "Alimente":
                holder.imageView.setImageResource(R.drawable.fastfood);
                break;
            case "Altele":
                holder.imageView.setImageResource(R.drawable.applogo2);
                break;

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_key = data.getId();
                item = data.getItem();
                amount = data.getAmount();
                note = data.getNotes();
                updateData();

            }
        });
    }

    private void updateData() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mView = inflater.inflate(R.layout.update_layout, null);

        myDialog.setView(mView);
        final AlertDialog dialog = myDialog.create();


        final TextView mItem = mView.findViewById(R.id.itemNameUpdate);
        final EditText mAmount = mView.findViewById(R.id.updateAmount);
        final EditText mNotes = mView.findViewById(R.id.updateNote);


        mItem.setText(item);

        mAmount.setText(String.valueOf(amount));
        mAmount.setSelection(String.valueOf(amount).length());

        mNotes.setText(note);
        mNotes.setSelection(note.length());

        Button delBut = mView.findViewById(R.id.btnDelete);
        Button btnUpdate = mView.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                amount = Integer.parseInt(mAmount.getText().toString());
                note = mNotes.getText().toString();

                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar cal = Calendar.getInstance();
                String date = dateFormat.format(cal.getTime());

                MutableDateTime epoch = new MutableDateTime();
                epoch.setDate(0);
                DateTime now = new DateTime();
                Months months = Months.monthsBetween(epoch, now);
                Weeks weeks = Weeks.weeksBetween(epoch, now);

                String itemNday = item + date;
                String itemNweek = item + weeks.getWeeks();
                String itemNmonth = item + months.getMonths();


                Data data = new Data(item, date, post_key, itemNday, itemNweek, itemNmonth,
                        amount, weeks.getWeeks(), months.getMonths(), note);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                reference.child(post_key).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "Budget item updaded successfuly", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, task.getException().toString(), Toast.LENGTH_SHORT).show();

                        }

                    }

                });
                dialog.dismiss();
            }
        });

        delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("expenses").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                reference.child(post_key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "Deleted successfuly", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, task.getException().toString(), Toast.LENGTH_SHORT).show();

                        }

                    }

                });
                dialog.dismiss();

            }
        });

        dialog.show();

    }

    @Override
    public int getItemCount() {
        return myDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView item, amount, date, notes;
        public ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.dateItem);
            notes = itemView.findViewById(R.id.notes);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }


}
