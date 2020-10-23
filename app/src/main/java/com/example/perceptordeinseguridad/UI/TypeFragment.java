package com.example.perceptordeinseguridad.UI;

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

import com.example.perceptordeinseguridad.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends Fragment implements View.OnClickListener {

    private ImageButton close;
    private Button druggedDrunkPeople;
    private Button harassment;
    private Button vandalism;
    private Button assault;
    private Button skip;
    private Bundle args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        druggedDrunkPeople = view.findViewById(R.id.btn_druggedDrunkPeople);
        close = view.findViewById(R.id.btn_closeTypeOfDanger);
        harassment = view.findViewById(R.id.btn_harassment);
        vandalism = view.findViewById(R.id.btn_vandalism);
        assault = view.findViewById(R.id.btn_assault);
        skip = view.findViewById(R.id.btn_skip2);
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

        druggedDrunkPeople.setOnClickListener(this);
        harassment.setOnClickListener(this);
        vandalism.setOnClickListener(this);
        assault.setOnClickListener(this);
        skip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_druggedDrunkPeople)
            args.putString("type","1");
        if(v.getId() == R.id.btn_vandalism)
            args.putString("type","2");
        if(v.getId() == R.id.btn_assault)
            args.putString("type", "3");
        if(v.getId() == R.id.btn_harassment)
            args.putString("type","4");
        if(v.getId() == R.id.btn_skip2)
            args.putString("type", "5");
        Navigation.findNavController(v).navigate(R.id.typeFrag_to_sendingFrag, args);
    }
}