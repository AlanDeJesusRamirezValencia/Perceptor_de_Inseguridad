package com.example.perceptordeinseguridad;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartLocationFragment extends Fragment {

    private final int granted = PackageManager.PERMISSION_GRANTED;
    private ImageButton goToUser;
    private View view;
    private FusedLocationProviderClient provider;
    private LocationRequest request;
    private String permission = Manifest.permission.ACCESS_FINE_LOCATION;
    private LocationCallback callback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if(locationResult == null) return;
            Bundle args  = new Bundle();
            for (Location location : locationResult.getLocations()){
                if (location.getLatitude() != 0 || location.getLongitude() != 0) {
                    args.putString("lat", location.getLatitude() + "");
                    args.putString("lon", location.getLongitude() + "");
                    provider.removeLocationUpdates(callback);
                    break;
                }
            }
            Navigation.findNavController(view).navigate(R.id.startLocFrag_to_levelFrag,args);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View v = inflater.inflate(R.layout.fragment_start_location, container, false);
        goToUser = v.findViewById(R.id.btn_goToUserFromStartLoc);
        provider = LocationServices.getFusedLocationProviderClient(requireActivity());
        request = LocationRequest.create();
        request.setInterval(4000);
        request.setFastestInterval(2000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.view = v;
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton start = view.findViewById(R.id.btn_StartLocation);
        goToUser.setOnClickListener(
                v -> Navigation.findNavController(view).navigate(R.id.startLocFrag_to_userFrag));
        start.setOnClickListener(v -> checkPermissionsAndGetLastLocation());
    }

    private void checkPermissionsAndGetLastLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), permission) == granted){
            LocationSettingsRequest req;
            req = new LocationSettingsRequest.Builder().addLocationRequest(request).build();
            SettingsClient settingsClient = LocationServices.getSettingsClient(requireActivity());
            Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(req);
            task.addOnSuccessListener(locationSettingsResponse -> {
                //Settings of device are satisfied and we can start location updates
                if(ContextCompat.checkSelfPermission(requireContext(), permission) == granted)
                    provider.requestLocationUpdates(request, callback, Looper.getMainLooper());
            });
            //Error: Surely GPS location is disabled
            task.addOnFailureListener(e ->
                    Navigation.findNavController(view).navigate(R.id.startLocFrag_to_ActiveGPS));
        }
        else {
            String p = permission;
            ActivityCompat.requestPermissions(requireActivity(),new String[]{p},1);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        provider.removeLocationUpdates(callback);
    }
}