package com.example.tarearecyclerv;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Modify extends AppCompatActivity implements
        View.OnClickListener{
    MainActivity mainActivity = new MainActivity();
    EditText modifyEdit;
    Button modify;
    int id;
    String nombre;
    Adapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_layout);
        modifyEdit = findViewById(R.id.modifyEdit);
        modify = findViewById(R.id.modify);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        nombre = bundle.getString("nombre");
        modifyEdit.setText(nombre);
        modify.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int position = 0;
        for(int i =0; i < MainActivity.alimentos.size(); i++){
            if(MainActivity.alimentos.get(i).getId() == id){
                position = i;
                break;
            }
        }
        BDLite conexion = new BDLite(this, "aliments", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", modifyEdit.getText().toString());
        int update = db.update("aliments",registro, "id=" + id, null);
        db.close();
        if(update == 1){
            Toast.makeText(this, "Se ha modificado con exito!",
                    Toast.LENGTH_LONG).show();
            Alimento alimento = MainActivity.alimentos.get(position);
            alimento.setNombre(modifyEdit.getText().toString());
            MainActivity.alimentos.set(position, alimento);
            MainActivity.adapter.notifyItemChanged(position);
            this.finish();
        }
else{
            Toast.makeText(this, "Intentelo nuevamente!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
