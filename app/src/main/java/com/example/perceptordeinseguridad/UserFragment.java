package com.example.perceptordeinseguridad;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    private SharedPreferences user;
    private TextView userID;
    private TextView name;
    private TextView birthDate;
    private TextView age;
    private TextView gender;
    private TextView nationality;
    private TextView socioeconomicLevel;
    private TextView occupation;
    private ImageButton goToStartLocation;
    private Button logOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        goToStartLocation = view.findViewById(R.id.btn_goToStartLocFragFromUserFrag);
        logOut = view.findViewById(R.id.btn_logout);
        user = requireActivity().getSharedPreferences("current_user", Context.MODE_PRIVATE);
        userID = view.findViewById(R.id.user_id_user);
        name = view.findViewById(R.id.user_username);
        birthDate = view.findViewById(R.id.user_fecha_nacimiento);
        age = view.findViewById(R.id.user_edad);
        gender = view.findViewById(R.id.user_genero);
        nationality = view.findViewById(R.id.user_nacionalidad);
        socioeconomicLevel = view.findViewById(R.id.user_nivel_socioeconomico);
        occupation = view.findViewById(R.id.user_ocupacion);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showDataOnScreen();
        goToStartLocation.setOnClickListener(
                v -> Navigation.findNavController(view).navigate(R.id.userFrag_to_startLocFrag));
        logOut.setOnClickListener(this::logOut);
    }

    public void logOut(View view){
        SharedPreferences.Editor editor = user.edit();
        editor.clear().apply();
        Navigation.findNavController(view).navigate(R.id.userFrag_to_choiserFrag);
    }

    private void showDataOnScreen(){
        userID.setText(user.getString("user_id",""));
        name.setText(user.getString("name",""));
        birthDate.setText(user.getString("birth_date",""));
        age.setText(user.getString("age",""));
        gender.setText(user.getString("gender",""));
        nationality.setText(user.getString("nationality",""));
        socioeconomicLevel.setText(user.getString("se_level",""));
        occupation.setText(user.getString("occupation", ""));
    }
}