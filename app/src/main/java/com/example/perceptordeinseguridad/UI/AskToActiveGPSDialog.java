package com.example.perceptordeinseguridad.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.perceptordeinseguridad.R;

/**
 * A {@link DialogFragment} subclass.
 *
 * It notify the user must to active the GPS to continue with the add perception.
 *
 * @author Alan de Jesús Ramirez Valencia.
 * @since 1.0
 */
public class AskToActiveGPSDialog extends DialogFragment {

    /** The button to close the current DialogFragment */
    private Button accept;

    /**
     * The {@link DialogFragment#onCreateView} overwritten method.
     *
     * Is used to instantiate the {@link AskToActiveGPSDialog#accept} button.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View v = inflater.inflate(R.layout.dialog_ask_to_active_gps, container, false);
        accept = v.findViewById(R.id.button_acceptGPS);
        return v;
    }

    /**
     * The {@link DialogFragment#onViewCreated} overwritten method.
     *
     * Here, we create the functionality of the {@link AskToActiveGPSDialog#accept}
     * button, that is, close the current DialogFragment
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accept.setOnClickListener(v -> dismiss());
    }
}