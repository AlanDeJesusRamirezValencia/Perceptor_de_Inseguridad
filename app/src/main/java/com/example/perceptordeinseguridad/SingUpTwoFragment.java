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

    private RadioButton man;
    private RadioButton woman;
    private String gender;
    private Calendar calendar;
    private DatePicker datePicker;
    private Button btnContinue;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_sing_up_two, container, false);
        datePicker = view.findViewById(R.id.date_picker);
        man = view.findViewById(R.id.radioButtonMan);
        woman = view.findViewById(R.id.radioButtonWoman);
        calendar = Calendar.getInstance();
        btnContinue = view.findViewById(R.id.button_continue2);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnContinue.setOnClickListener(v -> {
            if(checkData()){
                String date = datePicker.getYear()+"-"
                        +(datePicker.getMonth()+1)+"-"
                        +datePicker.getDayOfMonth();
                Bundle args = new Bundle();
                args.putString("user2", requireArguments().getString("user"));
                args.putString("password2", requireArguments().getString("password"));
                args.putString("mail2", requireArguments().getString("mail"));
                args.putString("gender", gender);
                args.putString("birthday", date);
                Navigation.findNavController(v).navigate(R.id.singUpTwoFrag_to_singUpThreeFrag, args);
            }
        });
    }

    private boolean checkData(){
        //Toast's duration
        int SHORT = 0;
        if (man.isChecked())
            gender = "1";
        else if (woman.isChecked())
            gender = "2";
        else{
            Toast.makeText(requireActivity(), "Gender not assigned", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (calendar.get(Calendar.YEAR)-(datePicker.getYear()) < 18){
            Toast.makeText(requireActivity(), "You must be of legal age", SHORT).show();
            return false;
        }
        return true;
    }
}