package com.example.tarearecyclerv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener{
    public static Adapter adapter;
    public static ArrayList<Alimento> alimentos;
    public static RecyclerView recyclerView;
    LinearLayoutManager manager;
    EditText editText;
    Button agregar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        agregar = findViewById(R.id.agregar);
        recyclerView = findViewById(R.id.recyclerView);
        alimentos = new ArrayList<Alimento>();
        selectAll();
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new Adapter(this, alimentos, recyclerView);
        recyclerView.setAdapter(adapter);
        agregar.setOnClickListener(this);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                    alimentos.get(recyclerView.getChildAdapterPosition(v)).getNombre(),
                    Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        int a = alimentos.size() > 0 ? alimentos.get(alimentos.size() -
                1).getId() : 0 + 1;
        String nombre = editText.getText().toString();
        int imagen = R.drawable.restaurant;
        SQLiteDatabase db;
        BDLite conexion = new BDLite(this, "aliments", null, 1);
        db = conexion.getWritableDatabase();
        if(db != null && editText.getText().length() > 0){
            ContentValues registro = new ContentValues();
            registro.put("nombre", nombre);
            registro.put("imagen", imagen);
            db.insert("aliments", null, registro);
//db.execSQL("DELETE FROM alimentos");
            db.close();
            selectAll();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Registro exitoso!",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Por favor, escriba el nombre del alimento",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void selectAll(){
        alimentos.clear();
        BDLite conexion = new BDLite(this, "aliments", null, 1);
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from aliments", null);
        while(cursor.moveToNext()){
            alimentos.add(new Alimento(cursor.getInt(0),
                    cursor.getString(1), cursor.getInt(2)));
        }
    }
}
