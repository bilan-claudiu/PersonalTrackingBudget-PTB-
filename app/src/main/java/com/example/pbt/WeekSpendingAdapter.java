package com.example.pbt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeekSpendingAdapter extends RecyclerView.Adapter<WeekSpendingAdapter.ViewHolder> {

    private Context mContext;
    private List<Data> myDataList;

    public WeekSpendingAdapter(Context mContext, List<Data> myDataList) {
        this.mContext = mContext;
        this.myDataList = myDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.retrive_layout, parent, false);
        return new WeekSpendingAdapter.ViewHolder(view);
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
