package com.example.tarea7usodedilogos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class TareaAdaptador extends RecyclerView.Adapter<TareaAdaptador.TareaViewHolder>{

    private List<Tarea> listaTareas;
    private final Context context;

    public TareaAdaptador(List<Tarea> listaTareas, Context context) {
        this.listaTareas = listaTareas;
        this.context = context;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.objeto_tarea, parent, false);
        return new TareaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = listaTareas.get(position);
        holder.tituloTextView.setText(tarea.getTitulo());
        holder.descripcionTextView.setText(tarea.getDescripcion());
        holder.fechaTextView.setText(tarea.getFecha());
        holder.horaTextView.setText(tarea.getHora());
        holder.itemView.setOnLongClickListener(v -> {
            showBottomSheetDialog(tarea);
            return true; // Indica que el evento fue consumido
        });

        holder.estadoTextVIew.setText("Pendiente");


    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView tituloTextView, descripcionTextView, fechaTextView, horaTextView, estadoTextVIew;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.tituloTextView);
            descripcionTextView = itemView.findViewById(R.id.descripcionTextView);
            fechaTextView = itemView.findViewById(R.id.fechaTextView);
            horaTextView = itemView.findViewById(R.id.horaTextView);
            estadoTextVIew = itemView.findViewById(R.id.textViewDebajoFecha);
        }
    }
    private void showBottomSheetDialog(Tarea tarea) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);

        // Inflar el diseño del diálogo
        View bottomSheetView = LayoutInflater.from(context).inflate(
                R.layout.botton_sheet_dialog,
                null
        );

        TextView editar = bottomSheetView.findViewById(R.id.textEditar);
        TextView eliminar = bottomSheetView.findViewById(R.id.textEliminar);
        TextView completar = bottomSheetView.findViewById(R.id.textCompletado);

        // NO FUNCIONA --> NO RECOGE EL SELECIONABLE DEL SPINNER
        editar.setOnClickListener(v -> {
            // Crear un nuevo fragmento
            FormularioFragment fragment = new FormularioFragment();

            // Crear un Bundle para pasar los datos
            Bundle args = new Bundle();
            args.putString("asignatura", tarea.getTitulo());
            args.putString("descripcion", tarea.getDescripcion());
            args.putString("fecha", tarea.getFecha());
            args.putString("hora", tarea.getHora()); // Asegúrate de que la clase Tarea tenga un método para obtener la asignatura

            fragment.setArguments(args);  // Pasamos los datos al fragmento

            fragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "EditarTarea");

            bottomSheetDialog.dismiss(); // Cerrar el diálogo después de seleccionar una opción
        });
        // PERFE
        eliminar.setOnClickListener(v->{
            listaTareas.remove(tarea); // Eliminar la tarea de la lista
            notifyDataSetChanged(); // Actualizar el RecyclerView
            bottomSheetDialog.dismiss();
        });
        completar.setOnClickListener(v -> {
            tarea.setEstado("Completado"); // Cambiar el estado de la tarea
            // Obtén la posición de la tarea en la lista
            notifyDataSetChanged();// Actualizar el RecyclerView
            bottomSheetDialog.dismiss();
        });

        // Configurar el diálogo con el diseño inflado
        bottomSheetDialog.setContentView(bottomSheetView);

        // Mostrar el diálogo
        bottomSheetDialog.show();
    }




}
