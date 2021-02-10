package com.shazaibsarwar.spector;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable {

    Context context;
    ArrayList<Model> models, filterList;
    CustomFilter filter;

    public MyAdapter(Context context, ArrayList<Model> models) {
        this.context = context;
        this.models = models;
        this.filterList = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Converting XML to OBJ

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, null);
        MyHolder holder = new MyHolder(v);

        return holder;
    }

    // Data bound to Views
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.nameText.setText(models.get(position).getName());
        holder.img.setImageResource(models.get(position).getImg());

        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onItemClicked(View v, int position) {


                // For General ItemClick
                if (models.get(position).getName().equals("General")) {
                    Toast.makeText(context, "General", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, GeneralActivity.class);
                    context.startActivity(intent);
                }


                // For General ItemClick
                if (models.get(position).getName().equals("Device Id")) {
                    Toast.makeText(context, "Device Id", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, DeviceID_Activity.class);
                    context.startActivity(intent);
                }


                // For General ItemClick
                if (models.get(position).getName().equals("Display")) {
                    Toast.makeText(context, "Display", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, DisplayAvtivity.class);
                    context.startActivity(intent);
                }


                // For Battery ItemClick
                if (models.get(position).getName().equals("Battery")) {
                    Toast.makeText(context, "Battery", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, BatteryActivity.class);
                    context.startActivity(intent);

                }


                // For User Apps ItemClick
                if (models.get(position).getName().equals("User Apps")) {
                    Toast.makeText(context, "User Apps", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, UserAppsActivity.class);
                    context.startActivity(intent);
                }


                // For System Apps ItemClick
                if (models.get(position).getName().equals("System Apps")) {
                    Toast.makeText(context, "System Apps", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, SystemAppsActivity.class);
                    context.startActivity(intent);
                }


                // For Memory ItemClick
                if (models.get(position).getName().equals("Memory")) {
                    Toast.makeText(context, "Memory", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, MemoryDetailsActivity.class);
                    context.startActivity(intent);
                }


                // For CPU ItemClick
                if (models.get(position).getName().equals("CPU")) {
                    Toast.makeText(context, "CPU", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, CPU_Activity.class);
                    context.startActivity(intent);

                }


                // For Sensors ItemClick
                if (models.get(position).getName().equals("Sensors")) {

                    Intent intent = new Intent(context, SensorsActivity.class);
                    context.startActivity(intent);
                }


                // For SIM ItemClick
                if (models.get(position).getName().equals("SIM")) {
                    Toast.makeText(context, "SIM", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, SIM_Activity.class);
                    context.startActivity(intent);
                }


            }
        });
    }

    // Getting total number of Items
    @Override
    public int getItemCount() {
        return models.size();
    }

    // return Filter Object
    @Override
    public Filter getFilter() {

        if (filter == null) {
            filter = new CustomFilter(filterList, this);
        }

        return filter;
    }
}

