package com.example.billy.jumpit.controller.activities.gameViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.billy.jumpit.R;
import com.example.billy.jumpit.model.ClassLevel;

import java.util.ArrayList;
import java.util.List;

public class MaterialPaletteAdapterLevels extends RecyclerView.Adapter<MaterialPaletteAdapterLevels.PaletteViewHolder> {
    private List<ClassLevel> data;
    RecyclerView list;
    //private User player = new User();
    GameViewHistoria gameViewHistoria;
    Context context;
    ArrayList <Integer> imagenes = new ArrayList();
    int i = 0;

    public MaterialPaletteAdapterLevels(@NonNull List<ClassLevel> data) {
        this.data = data;
    }

    @Override
    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.elementlevel, parent, false);
        return new PaletteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PaletteViewHolder holder, int position) {

        imagenes.add(R.drawable.nivel1button);imagenes.add(R.drawable.nivel2button);imagenes.add(R.drawable.nivel3button);
        imagenes.add(R.drawable.nivel4button);imagenes.add(R.drawable.nivel5button);
        ClassLevel objecte = data.get(position);
        holder.text.setText("nivel"+position);
        holder.getImageLevel().setBackgroundResource(imagenes.get(position));
        holder.getImageLevel();
        i = i+1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PaletteViewHolder extends RecyclerView.ViewHolder {

        ImageButton imageLevel;
        TextView text;

        public PaletteViewHolder(final View elementskin) {
            super(elementskin);
            imageLevel = (ImageButton) elementskin.findViewById(R.id.photolevel);
            text = (TextView) elementskin.findViewById(R.id.textlevelid);


        }

        public ImageButton getImageLevel() { return imageLevel; }
        public TextView getText() {
            return text;
        }

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}