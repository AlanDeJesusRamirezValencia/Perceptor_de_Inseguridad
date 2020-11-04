package com.example.perceptordeinseguridad.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.perceptordeinseguridad.MenuAnimations;
import com.example.perceptordeinseguridad.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements MenuAnimations {

    private ImageButton goToUser;
    private ImageButton goToAddPer;
    private ImageButton goToMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        goToUser = view.findViewById(R.id.btn_goToUserFromHistory);
        goToAddPer = view.findViewById(R.id.btn_goToAddPerFromHistory);
        goToMap = view.findViewById(R.id.btn_goToMapFromHistory);
        //Animations
        floatButtonAnimations(
                view.findViewById(R.id.history_focus),
                view.findViewById(R.id.history_float_view),
                view.findViewById(R.id.historyFloatImage),
                requireContext());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goToUser.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.historyFrag_to_userFrag));
        goToAddPer.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.historyFrag_to_addPerFrag));
        goToMap.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.historyFrag_to_mapFrag));
    }
}