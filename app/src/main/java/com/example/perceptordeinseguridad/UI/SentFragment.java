package com.example.perceptordeinseguridad.UI;

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

import com.example.perceptordeinseguridad.R;

/**
 * A new perception of insecurity has been sent.
 *
 * This fragment show that the new perception of insecurity has been sent.
 * Lets return fragment {@link AddPerceptionFragment}
 * or see the report of the perception.
 */
public class SentFragment extends Fragment {

    /** On clicked, show {@link ReportDialog}*/
    private Button report;

    /** On clicked, app navigates to {@link AddPerceptionFragment}*/
    private ImageButton close;


    /**
     * The {@link DialogFragment#onCreateView} overwritten method.
     *
     * Used to instantiate the local buttons.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_sent, container, false);
        report = view.findViewById(R.id.imageButtonReport);
        close = view.findViewById(R.id.button_closeSent);
        return view;
    }

    /**
     * The {@link DialogFragment#onViewCreated(View, Bundle)} overwritten method.
     *
     * set the onClickListener methods of the buttons
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = new Bundle();
        args.putString("perception_id", requireArguments().getString("idPerception"));
        report.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.sentFrag_to_reportDialog, args));
        close.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.sentFrag_to_addPerFrag));
    }
}