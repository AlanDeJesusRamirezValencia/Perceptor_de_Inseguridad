package com.example.perceptordeinseguridad;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

public abstract class LocationFragment extends Fragment {
    private final int granted = PackageManager.PERMISSION_GRANTED;
    private FusedLocationProviderClient provider;
    private LocationRequest request;
    private final String permission = Manifest.permission.ACCESS_FINE_LOCATION;
    private final LocationCallback callback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult == null) return;
            double lat = 0, lon = 0;
            for (Location location : locationResult.getLocations()) {
                if (location.getLatitude() != 0 || location.getLongitude() != 0) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                    provider.removeLocationUpdates(callback);
                    break;
                }
            }
            onLocationObtained(lat, lon);
        }
    };


    protected void initLocationComponents() {
        provider = LocationServices.getFusedLocationProviderClient(requireActivity());
        request = LocationRequest.create();
        request.setInterval(4000);
        request.setFastestInterval(2000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void checkPermissionsAndGetLastLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), permission) == granted){
            LocationSettingsRequest req;
            req = new LocationSettingsRequest.Builder().addLocationRequest(request).build();
            SettingsClient settingsClient = LocationServices.getSettingsClient(requireActivity());
            Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(req);
            task.addOnSuccessListener(locationSettingsResponse -> {
                //Settings of device are satisfied and we can start location updates
                if(ContextCompat.checkSelfPermission(requireContext(), permission) == granted)
                    provider.requestLocationUpdates(request, callback , Looper.getMainLooper());
            });
            //Error: Surely GPS location is disabled
            task.addOnFailureListener(e ->onGPSDisabled());
        }
        else
            ActivityCompat.requestPermissions(requireActivity(),new String[]{permission},1);
    }

    protected abstract void onLocationObtained(double lat, double lon);

    protected abstract void onGPSDisabled();

    @Override
    public void onStop() {
        super.onStop();
        provider.removeLocationUpdates(callback);
    }
}