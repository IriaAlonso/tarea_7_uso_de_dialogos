package com.example.tarea7usodedilogos;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Tarea> arrayTarea = new ArrayList<>();
    private TareaAdaptador adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        adaptador = new TareaAdaptador(arrayTarea, this);
        RecyclerView rv_tareas = findViewById(R.id.rv_tareas);
        rv_tareas.setLayoutManager(new LinearLayoutManager(this));
        rv_tareas.setAdapter(adaptador);

        Button abrirFormulario = findViewById(R.id.button);
        abrirFormulario.setOnClickListener(v -> {
            FormularioFragment fragment = new FormularioFragment();
            fragment.show(getSupportFragmentManager(),"Tarea");
        });

    }
    public void agregarTarea(Tarea tarea) {
        arrayTarea.add(tarea);  // Agregar la tarea a la lista
        adaptador.notifyDataSetChanged();  // Notificar al adaptador para actualizar el RecyclerView
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // Inflar el menú contextual desde el archivo XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);  // Asegúrate de tener un archivo de menú llamado menu_contextual.xml
    }
}