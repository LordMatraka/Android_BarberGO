package com.example.barbergo;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class EmployeeAdapter extends RecyclerView.Adapter {

    public interface OnItemClickListener {
        void onItemClick(Employee item);
    }
    private ArrayList<Employee> employees;
    private final OnItemClickListener listener;

    // Obtenemos referencias de los componentes visuales para cada elemento

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView description;
        private ImageView image;

        public ViewHolder(View tv) {
            super(tv);
            name = tv.findViewById(R.id.employeeName);
            description = tv.findViewById(R.id.employeeDescription);
            image = tv.findViewById(R.id.employeeImage);

        }

        public void bind(final Employee employee, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(employee);
                }
            });
        }
    }

    public EmployeeAdapter(ArrayList<Employee> employees, OnItemClickListener listener){
        this.employees = employees;
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_list_item, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings, etc
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vHolder = (ViewHolder)holder;
        Employee e = employees.get(position);
        vHolder.name.setText(e.getName());
        vHolder.description.setText(e.getDescription());
        vHolder.image.setImageResource(e.getImageResId());
        vHolder.bind(e, listener);
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }
}
