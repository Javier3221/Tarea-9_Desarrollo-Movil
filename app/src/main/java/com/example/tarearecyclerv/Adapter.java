package com.example.tarearecyclerv;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements View.OnClickListener{
    ArrayList<Alimento> alimentos;
    Context context;
    boolean updatable;
    RecyclerView recyclerView;
    private View.OnClickListener listener;
    public Adapter(Context context, ArrayList<Alimento> alimentos,
                   RecyclerView recyclerView){
        this.alimentos = alimentos;
        this.context = context;
        this.updatable = updatable;
        this.recyclerView = recyclerView;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.layout_adapter, null, false);
        MyViewHolder vh = new MyViewHolder(v);
        v.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String nombre = alimentos.get(position).getNombre();
        int id = alimentos.get(position).getId();
        int imagen = alimentos.get(position).getImagen();
        holder.textNombre.setText(nombre);
        holder.imgImagen.setImageResource(imagen);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDLite conexion = new BDLite(context, "aliments", null, 1);
                SQLiteDatabase db = conexion.getWritableDatabase();
                int delete = db.delete("aliments", "id=" + id, null);
                db.close();
                if(delete == 1){
                    Toast.makeText(context, "Borrado con exito!",
                            Toast.LENGTH_LONG).show();
                    MainActivity.alimentos.remove(position);
                    recyclerView.removeViewAt(position);
                }
                else{
                    Toast.makeText(context, "Intentelo nuevamente!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Modify.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("nombre", nombre);
                intent.putExtras(bundle);
                intent.putExtra("alimentos", alimentos);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return alimentos.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if(listener != null) {
            listener.onClick(v);
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre;
        ImageView imgImagen;
        ImageButton delete;
        ImageButton edit;
        EditText editText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.foodName);
            imgImagen = itemView.findViewById(R.id.food);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}

