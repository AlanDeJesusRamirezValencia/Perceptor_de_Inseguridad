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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends LocationFragment implements OnMapReadyCallback, MenuAnimations {

    private MapView mapView;
    private GoogleMap map;
    private ImageButton goToStartLocation;
    private ImageButton goToHistory;
    private ImageButton goToUser;

    //Animated components
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        initLocationComponents();
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = v.findViewById(R.id.mapView);
        goToStartLocation = v.findViewById(R.id.btn_goToAddPerFragFromMapFrag);
        goToHistory = v.findViewById(R.id.btn_goToHistoryFragFromMapFrag);
        goToUser = v.findViewById(R.id.btn_goToUserFromMapFrag);
        mapView.onCreate(savedInstance);
        mapView.getMapAsync(this);
        checkPermissionsAndGetLastLocation();
        //animations
        floatButtonAnimations(
                v.findViewById(R.id.map_focus),
                v.findViewById(R.id.map_float_view),
                v.findViewById(R.id.map_float_image),
                requireContext()
        );
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goToStartLocation.setOnClickListener( v -> Navigation.findNavController(v).navigate(R.id.mapFrag_to_startLocFrag));
        goToHistory.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.mapFrag_to_historyFrag));
        goToUser.setOnClickListener( v -> Navigation.findNavController(v).navigate(R.id.mapFrag_to_userFrag));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        this.map = map;
        this.map.setMinZoomPreference(16);
        this.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng ll1 = new LatLng(18.142708, -94.476217);
        this.map.addMarker(new MarkerOptions().position(ll1)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_one)));
        LatLng ll2 = new LatLng(18.145145, -94.472462);
        this.map.addMarker(new MarkerOptions().position(ll2)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_one)));
        LatLng ll3 = new LatLng(18.141230, -94.474876);
        this.map.addMarker(new MarkerOptions().position(ll3)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_four)));
        LatLng ll4 = new LatLng(18.140985, -94.476807);
        this.map.addMarker(new MarkerOptions().position(ll4)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_one)));
        LatLng ll5 = new LatLng(18.143146, -94.477934);
        this.map.addMarker(new MarkerOptions().position(ll5)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_four)));
        LatLng ll6 = new LatLng(18.142636, -94.473728);
        this.map.addMarker(new MarkerOptions().position(ll6)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_one)));
        LatLng ll7 = new LatLng(18.146875, -94.474540);
        this.map.addMarker(new MarkerOptions().position(ll7)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_one)));
        LatLng ll8 = new LatLng(18.144642, -94.478724);
        this.map.addMarker(new MarkerOptions().position(ll8)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_one)));
        LatLng ll9 = new LatLng(18.141007, -94.478445);
        this.map.addMarker( new MarkerOptions().position(ll9)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_four)));
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
    protected void onLocationObtained(double lat, double lon) {
        LatLng myPosition = new LatLng(18.143023, -94.475691);
        map.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
    }

    @Override
    protected void onGPSDisabled() {

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