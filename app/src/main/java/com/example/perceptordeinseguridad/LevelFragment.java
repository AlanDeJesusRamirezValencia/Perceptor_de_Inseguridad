package com.example.perceptordeinseguridad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 *
 * This is the first step in sending a perception of insecurity.
 * In this fragment, the user selects a level of perception of insecurity.
 * In a range from 1 to 5. Where 1 is the most insecure and 5 is the safest.
 */
public class LevelFragment extends Fragment implements View.OnClickListener {

    /** Button to close the current fragment and back to {@link StartLocationFragment}*/
    private ImageButton close;

    /** Button describing a very low level of insecurity */
    private Button level1;

    /** Button describing a low level of insecurity */
    private Button level2;

    /** Button describing a medium level of insecurity */
    private Button level3;

    /** button describing high level of insecurity*/
    private Button level4;

    /** Button describing a very high level of insecurity */
    private Button level5;

    /**
     * Bundle sent from {@link StartLocationFragment}.
     *
     * This Bundle must contain the following data.
     * - Latitude abbreviated as "lat"
     * - Longitude abbreviated as "lon"
     */
    private Bundle args;

    /**
     * The {@link DialogFragment#onCreateView} overwritten method.
     *
     * Used to instantiate the local buttons and store the fragment arguments
     * in the local Bundle field.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_level, container, false);
        close = view.findViewById(R.id.button_closeLevelPerception);
        level1 = view.findViewById(R.id.button_level1);
        level2 = view.findViewById(R.id.button_level2);
        level3 = view.findViewById(R.id.button_level3);
        level4 = view.findViewById(R.id.button_level4);
        level5 = view.findViewById(R.id.button_level5);
        args = new Bundle();
        args.putString("lat2", requireArguments().getString("lat"));
        args.putString("lon2", requireArguments().getString("lon"));
        return view;
    }

    /**
     * The {@link DialogFragment#onViewCreated(View, Bundle)} overwritten method.
     *
     * It set the OnClickListener methods of the local buttons.
     */
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        close.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.levelFrag_to_startLocFrag));
        level1.setOnClickListener(this);
        level2.setOnClickListener(this);
        level3.setOnClickListener(this);
        level4.setOnClickListener(this);
        level5.setOnClickListener(this);
    }

    /**
     * This method is obtained thanks to the onClickListener interface of the View class.
     *
     * Compares the id of de clicked View with the local id buttons and
     * adds the level of perception of insecurity according to the id.
     */
    @Override
    public void onClick(@NonNull View v) {
        if (v.getId() == level1.getId()) args.putString("level", "1");
        if (v.getId() == level2.getId()) args.putString("level", "2");
        if (v.getId() == level3.getId()) args.putString("level", "3");
        if (v.getId() == level4.getId()) args.putString("level", "4");
        if (v.getId() == level5.getId()) args.putString("level", "5");
        Navigation.findNavController(v).navigate(R.id.levelFrag_to_contextFrag, args);
    }
}