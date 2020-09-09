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
 * A {@link Fragment} subclass.
 *
 * In this fragment, the user chooses an option to describe
 * the context of his perception of insecurity.
 *
 * @author Alan De Jesús Ramírez Valencia.
 * @since 1.0
 */
public class ContextFragment extends Fragment implements View.OnClickListener {

    /** Button to close the current fragment and back to {@link StartLocationFragment}*/
    private ImageButton close;

    /**
     * button where the user describes being in a "private transport"
     * as a context of their perception of insecurity
     */
    private Button privateTransport;

    /**
     * button where the user describes being in a "public transport"
     * as a context of their perception of insecurity
     */
    private Button publicTransport;

    /**
     * button where the user describes being "at work"
     * as a context of their perception of insecurity
     */
    private Button atLocalWork;

    /**
     * button where the user describes going "on foot"
     * as a context of their perception of insecurity
     */
    private Button onFoot;

    /**
     * button where the user describes being "at home"
     * as a context of their perception of insecurity
     */
    private Button atHome;

    /**
     * button where the user doesn't describes a context of their perception of insecurity
     * the context is described as "unknown"
     */
    private Button skip;

    /**
     * Bundle sent from {@link StartLocationFragment}.
     *
     * This Bundle must contain the following data.
     * - Latitude abbreviated as "lat2"
     * - Longitude abbreviated as "lon2"
     * - Level of insecurity abbreviated as "level"
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
        View view = inflater.inflate(R.layout.fragment_context, container, false);
        privateTransport = view.findViewById(R.id.button_privateTransport);
        publicTransport = view.findViewById(R.id.button_publicTransport);
        atLocalWork = view.findViewById(R.id.button_atLocalWork);
        close = view.findViewById(R.id.button_closeContext);
        onFoot = view.findViewById(R.id.button_onFoot);
        atHome = view.findViewById(R.id.button_atHome);
        skip = view.findViewById(R.id.button_skip);
        args = new Bundle();
        args.putString("longitude", requireArguments().getString("lat2"));
        args.putString("latitude", requireArguments().getString("lon2"));
        args.putString("level2", requireArguments().getString("level"));
        return view;
    }

    /**
     * The {@link DialogFragment#onViewCreated(View, Bundle)} overwritten method.
     *
     * It set the OnClickListener methods of the local buttons.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        close.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.contextFrag_to_startLocationFrag));
        privateTransport.setOnClickListener(this);
        publicTransport.setOnClickListener(this);
        atLocalWork.setOnClickListener(this);
        onFoot.setOnClickListener(this);
        atHome.setOnClickListener(this);
        skip.setOnClickListener(this);
    }

    /**
     * This method is obtained thanks to the onClickListener interface of the View class.
     *
     * Compares the id of de clicked View with the local id buttons and
     * adds the level of perception of insecurity according to the id.
     */
    @Override
    public void onClick(@NonNull View v) {
        if(v.getId() == R.id.button_privateTransport) args.putString("context", "1");
        if(v.getId() == R.id.button_publicTransport) args.putString("context", "2");
        if(v.getId() == R.id.button_atLocalWork) args.putString("context", "3");
        if(v.getId() == R.id.button_onFoot) args.putString("context", "4");
        if(v.getId() == R.id.button_atHome) args.putString("context", "5");
        if(v.getId() == R.id.button_skip) args.putString("context", "6");
        Navigation.findNavController(v).navigate(R.id.contextFrag_to_typeFrag, args);
    }
}