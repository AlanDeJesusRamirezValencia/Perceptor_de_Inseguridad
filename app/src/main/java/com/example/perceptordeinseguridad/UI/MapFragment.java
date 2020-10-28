package com.example.perceptordeinseguridad.UI;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.perceptordeinseguridad.CustomRequest;
import com.example.perceptordeinseguridad.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private ImageButton goToStartLocation;
    private ImageButton goToUser;

    //Animated components
    private View floatView;
    private ImageButton floatImage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = v.findViewById(R.id.mapView);
        goToStartLocation = v.findViewById(R.id.btn_goToStartLocFragFromMapFrag);
        goToUser = v.findViewById(R.id.btn_goToUserFromMapFrag);
        mapView.onCreate(savedInstance);
        mapView.getMapAsync(this);
        floatView = v.findViewById(R.id.map_float_view);
        floatImage = v.findViewById(R.id.map_float_image);

        floatButtonAnimation();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goToStartLocation.setOnClickListener( v -> Navigation.findNavController(v).navigate(R.id.mapFrag_to_startLocFrag));
        goToUser.setOnClickListener( v -> Navigation.findNavController(v).navigate(R.id.mapFrag_to_userFrag));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        map.setMinZoomPreference(13);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void floatButtonAnimation(){
        Animation animFloatButton = AnimationUtils.loadAnimation(requireContext(), R.anim.show_float_view);
        Animation animFloatImage = AnimationUtils.loadAnimation(requireContext(), R.anim.show_float_image);
        floatView.startAnimation(animFloatButton);
        floatImage.startAnimation(animFloatImage);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}