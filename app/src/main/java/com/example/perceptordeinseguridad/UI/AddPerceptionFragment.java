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

import com.example.perceptordeinseguridad.LocationFragment;
import com.example.perceptordeinseguridad.MenuAnimations;
import com.example.perceptordeinseguridad.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPerceptionFragment extends LocationFragment implements MenuAnimations {

    private ImageButton goToUser;
    private ImageButton goToHistory;
    private ImageButton goToMap;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        initLocationComponents();
        View v = inflater.inflate(R.layout.fragment_start_location, container, false);
        goToUser = v.findViewById(R.id.btn_goToUserFromAddPer);
        goToHistory = v.findViewById(R.id.btn_goToHistoryFromAddPer);
        goToMap = v.findViewById(R.id.btn_goToMapFromAddPer);
        //Animations
        floatButtonAnimations(
                v.findViewById(R.id.start_loc_focus),
                v.findViewById(R.id.start_loc_float_view),
                v.findViewById(R.id.add_per_float_image),
                requireContext());
        this.view = v;
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton start = view.findViewById(R.id.btn_StartLocation);
        goToUser.setOnClickListener(
                v -> Navigation.findNavController(view).navigate(R.id.startLocFrag_to_userFrag));
        goToHistory.setOnClickListener(
                v -> Navigation.findNavController(view).navigate(R.id.addPerFrag_to_historyFrag));
        goToMap.setOnClickListener(view1 ->
                Navigation.findNavController(view).navigate(R.id.addPerFrag_to_mapFrag));
        start.setOnClickListener(v -> checkPermissionsAndGetLastLocation());
    }

    @Override
    protected void onLocationObtained(double lat, double lon) {
        Bundle args = new Bundle();
        args.putString("lat", lat + "");
        args.putString("lon", lon + "");
        Navigation.findNavController(view).navigate(R.id.addPerFrag_to_levelFrag, args);
    }

    @Override
    protected void onGPSDisabled() {
        Navigation.findNavController(view).navigate(R.id.addPerFrag_to_ActiveGPS);
    }
}