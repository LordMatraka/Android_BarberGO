package com.example.barbergo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class PromsAdapter extends RecyclerView.Adapter {


    private ArrayList<Promotion> promotions;

    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los EditText, TextViews, Buttons
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // en este ejemplo cada elemento consta solo de un título

        private TextView title;
        private TextView content;
        private ImageView image;

        public ViewHolder(View tv) {
            super(tv);
            title= (TextView) tv.findViewById(R.id.promsTitle);
            content= (TextView) tv.findViewById(R.id.promContent);
            image= (ImageView) tv.findViewById(R.id.promsImage);

        }
    }

    public PromsAdapter(ArrayList<Promotion> promotions){
        this.promotions = promotions;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings, etc
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vHolder = (ViewHolder)holder;
        Promotion p = promotions.get(position);
        vHolder.title.setText(p.getTitle());
        vHolder.content.setText(p.getContent());
        vHolder.image.setImageResource(p.getImageResId());
    }


    @Override
    public int getItemCount() {
        return promotions.size();
    }
}
