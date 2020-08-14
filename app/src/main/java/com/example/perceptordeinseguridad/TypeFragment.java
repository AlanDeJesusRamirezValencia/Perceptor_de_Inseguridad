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
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends Fragment implements View.OnClickListener {

    private ImageButton close;
    private Button unintentionalViolence;
    private Button druggedDrunkPeople;
    private Button pedestrianHazard;
    private Button highPollution;
    private Button healtHazard;
    private Button humanDanger;
    private Button localDanger;
    private Button harassment;
    private Button vandalism;
    private Button assault;
    private Button skip;
    private Bundle args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        unintentionalViolence = view.findViewById(R.id.btn_unintentionalViolene);
        druggedDrunkPeople = view.findViewById(R.id.btn_druggedDrunkPeople);
        pedestrianHazard = view.findViewById(R.id.btn_pedestrianHazard);
        highPollution = view.findViewById(R.id.btn_highPollution);
        close = view.findViewById(R.id.btn_closeTypeOfDanger);
        humanDanger = view.findViewById(R.id.btn_humanDanger);
        localDanger = view.findViewById(R.id.btn_localDanger);
        healtHazard = view.findViewById(R.id.btn_healtHazard);
        harassment = view.findViewById(R.id.btn_harassment);
        vandalism = view.findViewById(R.id.btn_vandalism);
        assault = view.findViewById(R.id.btn_asault);
        skip = view.findViewById(R.id.btn_skip2);
        unintentionalViolence.setVisibility(View.GONE);
        druggedDrunkPeople.setVisibility(View.GONE);
        pedestrianHazard.setVisibility(View.GONE);
        highPollution.setVisibility(View.GONE);
        healtHazard.setVisibility(View.GONE);
        harassment.setVisibility(View.GONE);
        vandalism.setVisibility(View.GONE);
        assault.setVisibility(View.GONE);
        args = new Bundle();
        args.putString("latitude2", requireArguments().getString("latitude"));
        args.putString("longitude2", requireArguments().getString("longitude"));
        args.putString("level3", requireArguments().getString("level2"));
        args.putString("context2", requireArguments().getString("context"));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        close.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.typeFrag_to_startLocFrag));
        humanDanger.setOnClickListener(v -> {
            humanDanger.setVisibility(View.GONE);
            localDanger.setVisibility(View.GONE);
            unintentionalViolence.setVisibility(View.VISIBLE);
            druggedDrunkPeople.setVisibility(View.VISIBLE);
            harassment.setVisibility(View.VISIBLE);
            vandalism.setVisibility(View.VISIBLE);
            assault.setVisibility(View.VISIBLE);
        });
        localDanger.setOnClickListener(v -> {
            humanDanger.setVisibility(View.GONE);
            localDanger.setVisibility(View.GONE);
            pedestrianHazard.setVisibility(View.VISIBLE);
            highPollution.setVisibility(View.VISIBLE);
            healtHazard.setVisibility(View.VISIBLE);
        });
        unintentionalViolence.setOnClickListener(this);
        druggedDrunkPeople.setOnClickListener(this);
        pedestrianHazard.setOnClickListener(this);
        highPollution.setOnClickListener(this);
        healtHazard.setOnClickListener(this);
        harassment.setOnClickListener(this);
        vandalism.setOnClickListener(this);
        assault.setOnClickListener(this);
        skip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_unintentionalViolene)
            args.putString("type","violencia no intencional");
        if(v.getId() == R.id.btn_druggedDrunkPeople)
            args.putString("type","alcoholizados/drogados");
        if(v.getId() == R.id.btn_highPollution)
            args.putString("type","alta contaminacion");
        if(v.getId() == R.id.btn_healtHazard)
            args.putString("type","peligro sanitario");
        if(v.getId() == R.id.btn_pedestrianHazard)
            args.putString("type","peligro peatonal");
        if(v.getId() == R.id.btn_vandalism)
            args.putString("type","vandalismo");
        if(v.getId() == R.id.btn_asault)
            args.putString("type", "asalto");
        if(v.getId() == R.id.btn_harassment)
            args.putString("type","acoso");
        if(v.getId() == R.id.btn_skip2)
            args.putString("type", "desconocido");
        Navigation.findNavController(v).navigate(R.id.typeFrag_to_sendingFrag, args);
    }
}