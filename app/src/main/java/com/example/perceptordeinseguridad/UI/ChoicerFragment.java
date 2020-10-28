package com.example.perceptordeinseguridad.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.perceptordeinseguridad.R;

/**
 * A {@link Fragment} subclass.
 *
 * This is the start destination of the navigation XML file.
 * It serves to identify the current user of the application.
 * If there is a current user, the app navigates to {@link AddPerceptionFragment}.
 * If there isn't, the app navigates to {@link LogInFragment}.
 *
 * @author Alan de Jesús Ramirez Valencia.
 * @since 1.0
 */
public class ChoicerFragment extends Fragment {

    /** Stores the user id saved in the local SharedPreferences.*/
    private String userID;

    /**
     * The {@link DialogFragment#onCreateView} overwritten method.
     *
     * Used to get the current user id, and save it in the
     * {@link ChoicerFragment#userID} field. If there's no user id, the
     * {@link ChoicerFragment#userID} field will be left empty.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_choiser, container, false);
        SharedPreferences preferences = requireActivity().
                getSharedPreferences("current_user", Context.MODE_PRIVATE);
        userID = preferences.getString("pk_user", "");
        return view;
    }

    /**
     * The {@link DialogFragment#onViewCreated} overwritten method.
     *
     * If the {@link ChoicerFragment#userID} field is not empty,
     * the app navigates to {@link AddPerceptionFragment}.
     * If is it, the app navigates to {@link LogInFragment}.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (userID.equals(""))
            Navigation.findNavController(view).navigate(R.id.choiserFrag_to_logInFrag);
        else
            Navigation.findNavController(view).navigate(R.id.choiserFrag_to_startLocFrag);
    }
}