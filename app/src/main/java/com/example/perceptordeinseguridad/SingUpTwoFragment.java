package com.example.perceptordeinseguridad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingUpTwoFragment extends Fragment {

    private RadioButton hombre;
    private RadioButton mujer;
    private String genero;
    private Calendar calendario;
    private DatePicker datePicker;
    private Button continuar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_sing_up_two, container, false);
        datePicker = view.findViewById(R.id.date_picker);
        hombre = view.findViewById(R.id.radioButtonHombre);
        mujer = view.findViewById(R.id.radioButtonMujer);
        calendario = Calendar.getInstance();
        continuar = view.findViewById(R.id.button_continuar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        continuar.setOnClickListener(v -> {
            if(validarDatos()){
                String fecha = datePicker.getYear()+"-"
                        +(datePicker.getMonth()+1)+"-"
                        +datePicker.getDayOfMonth();
                Bundle args = new Bundle();
                args.putString("user", requireArguments().getString("usuario"));
                args.putString("password", requireArguments().getString("contrasena"));
                args.putString("mail", requireArguments().getString("correo"));
                args.putString("genero", genero);
                args.putString("fecha", fecha);
                Navigation.findNavController(v).navigate(R.id.singUpTwoFrag_to_singUpThreeFrag, args);
            }
        });
    }

    private boolean validarDatos(){
        //Toast's duration
        int SHORT = 0;
        if (hombre.isChecked())
            genero = "masculino";
        else if (mujer.isChecked())
            genero = "femenino";
        else{
            Toast.makeText(requireActivity(), "Genero no asignado", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (calendario.get(Calendar.YEAR)-(datePicker.getYear()) < 18){
            Toast.makeText(requireActivity(), "Debes ser mayor de edad", SHORT).show();
            return false;
        }
        return true;
    }
}