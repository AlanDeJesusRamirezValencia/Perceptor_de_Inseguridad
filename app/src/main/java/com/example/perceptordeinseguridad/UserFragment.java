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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private ImageButton goToMap;
    private Button logOut;
    private Button goToEditUser;

    //Navigation bar animation components

    private View focus;
    private View floatView;
    private ImageButton floatImage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        goToStartLocation = view.findViewById(R.id.btn_goToStartLocFragFromUserFrag);
        goToMap = view.findViewById(R.id.btn_goToMapFromUser);
        logOut = view.findViewById(R.id.btn_logout);
        goToEditUser = view.findViewById(R.id.btn_goToEditUser);
        user = requireActivity().getSharedPreferences("current_user", Context.MODE_PRIVATE);
        userID = view.findViewById(R.id.user_id_user);
        name = view.findViewById(R.id.user_username);
        birthDate = view.findViewById(R.id.user_birthday);
        age = view.findViewById(R.id.user_age);
        gender = view.findViewById(R.id.user_gender);
        nationality = view.findViewById(R.id.user_nationality);
        socioeconomicLevel = view.findViewById(R.id.user_socioeconomicLevel);
        occupation = view.findViewById(R.id.user_occupation);
        focus = view.findViewById(R.id.user_focus);
        floatView = view.findViewById(R.id.user_float_button);
        floatImage = view.findViewById(R.id.user_float_image);

        //Animations
        floatButtonAnimation();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showDataOnScreen();
        goToStartLocation.setOnClickListener(
                v -> Navigation.findNavController(view).navigate(R.id.userFrag_to_startLocFrag));
        goToMap.setOnClickListener(
                view1 -> Navigation.findNavController(view).navigate(R.id.userFrag_to_mapFrag));
        goToEditUser.setOnClickListener(
                view1 -> Navigation.findNavController(view).navigate(R.id.userFrag_to_editUserFrag));
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


    private void floatButtonAnimation(){
        Animation animFloatButton;
        animFloatButton = AnimationUtils.loadAnimation(requireContext(), R.anim.show_float_view);
        Animation animFloatImage;
        animFloatImage = AnimationUtils.loadAnimation(requireContext(), R.anim.show_float_image);
        Animation focusAnimation;
        focusAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.focus_initial);
        floatView.startAnimation(animFloatButton);
        floatImage.startAnimation(animFloatImage);
        floatImage.setVisibility(View.VISIBLE);
        focus.startAnimation(focusAnimation);
    }
}