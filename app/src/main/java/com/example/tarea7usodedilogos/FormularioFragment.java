package com.example.tarea7usodedilogos;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class FormularioFragment extends DialogFragment {
    private ArrayList<Tarea> tareas = new ArrayList<>();
    private TareaAdaptador adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario, container, false);

        // Inicializa los elementos de la vista
        Spinner asignatura = view.findViewById(R.id.asignaturaSpinner);
        EditText descripcion = view.findViewById(R.id.descripcionEditText);
        TextView fecha = view.findViewById(R.id.fechaTextView);
        TextView hora = view.findViewById(R.id.horaTextView);
        Button aceptar = view.findViewById(R.id.aceptarButton);
        Button cancelar = view.findViewById(R.id.cancelarButton);

        Bundle args = getArguments();
        if (args != null) {
            String asignaturaText = args.getString("asignatura");
            String descripcionText = args.getString("descripcion");
            String fechaText = args.getString("fecha");
            String horaText = args.getString("hora");

            // Rellenar los campos del formulario con los datos de la tarea
            descripcion.setText(descripcionText);
            fecha.setText(fechaText);
            hora.setText(horaText);

            // Configurar el Spinner con la asignatura seleccionada
            String[] opc_asignaturas = new String[] {
                    "PMDM", "AD", "EIE", "PSP", "DI"
            };
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opc_asignaturas);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            asignatura.setAdapter(adapter);

            // Establecer la asignatura seleccionada
            int position = Arrays.asList(opc_asignaturas).indexOf(asignaturaText);
            if (position != -1) {
                asignatura.setSelection(position);
            }
        }










        aceptar.setOnClickListener(v -> {
            String selectedAsignatura = asignatura.getSelectedItem().toString();
            String descripcionText = descripcion.getText().toString();
            String fechaText = fecha.getText().toString();
            String horaText = hora.getText().toString();
            String estado = "Pendiente";
            // Crear el objeto Tarea
            Tarea tarea = new Tarea(selectedAsignatura, descripcionText, fechaText, horaText, estado);

            // Llamar al método de la actividad principal para agregar la tarea
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                activity.agregarTarea(tarea);
            }

            // Cerrar el diálogo o fragmento
            dismiss();
        });
        cancelar.setOnClickListener(v -> {
            // Simplemente cerrar el formulario sin hacer nada
            dismiss();
        });

        // Opciones del Spinner
        String[] opc_asignaturas = new String[] {
                "PMDM", "AD", "EIE", "PSP", "DI"
        };

        // Crear el adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opc_asignaturas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        asignatura.setAdapter(adapter);

        // Configuración del DatePickerDialog para seleccionar la fecha
        fecha.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            // Mostrar el DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    (view1, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                        // Formatear la fecha seleccionada
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = dateFormat.format(selectedDate.getTime());

                        // Actualizar el TextView con la fecha seleccionada
                        fecha.setText(formattedDate);
                    },
                    year, month, dayOfMonth
            );
            datePickerDialog.show();
        });

        // Configuración del TimePickerDialog para seleccionar la hora
        hora.setOnClickListener(v -> {
            // Obtener la hora actual
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            // Mostrar el TimePickerDialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    requireContext(),
                    (view1, selectedHour, selectedMinute) -> {
                        // Formatear la hora seleccionada
                        String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);

                        // Actualizar el TextView con la hora seleccionada
                        hora.setText(formattedTime);
                    },
                    hour, minute, true // Hora actual, minuto actual, formato 24 horas
            );
            timePickerDialog.show();
        });

        return view;
    }

}
