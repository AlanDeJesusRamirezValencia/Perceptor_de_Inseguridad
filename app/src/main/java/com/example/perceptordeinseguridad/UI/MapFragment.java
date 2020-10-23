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

    private FusedLocationProviderClient provider;
    private MapView mapView;
    private ImageButton goToStartLocation;
    private ImageButton goToUser;

    //Animated components
    private View floatView;
    private ImageButton floatImage;

    //request components
    final int duration = Toast.LENGTH_SHORT;
    final int method = Request.Method.POST;


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = v.findViewById(R.id.mapView);
        goToStartLocation = v.findViewById(R.id.btn_goToStartLocFragFromMapFrag);
        goToUser = v.findViewById(R.id.btn_goToUserFromMapFrag);
        mapView.onCreate(savedInstance);
        mapView.getMapAsync(this);
        provider = LocationServices.getFusedLocationProviderClient(requireActivity());

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

    /** Failed connection */
    private void errorResponse(VolleyError error){
        Toast.makeText(requireActivity(), "Disconnected", duration).show();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        //map.setMinZoomPreference(13);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        int granted = PackageManager.PERMISSION_GRANTED;
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ContextCompat.checkSelfPermission(requireContext(), permission) == granted) {
            map.setMyLocationEnabled(true);
            Task<Location> locationTask = provider.getLastLocation();
            locationTask.addOnSuccessListener(loc -> map.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 12)));
        } else {
            ActivityCompat.requestPermissions(requireActivity(),new String[]{permission},1);
        }
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        Map<String, String> parameters = new HashMap<>();
        SharedPreferences userData = requireActivity().getSharedPreferences("current_user", Context.MODE_PRIVATE);
        parameters.put("id", userData.getString("user_id", ""));
        String url = "https://www.edacarquitectos.com/appBotonDePanico/PerceptionsHistory.php?id_usuario=3";
        CustomRequest request = new CustomRequest(method, url, parameters, response -> {
            try {
                JSONArray dataMarkers = response.getJSONArray("datos");
                for (int i=0; i<dataMarkers.length(); i++) {
                    JSONObject data = dataMarkers.getJSONObject(i);
                    map.addMarker(new MarkerOptions().position(new LatLng(Integer.parseInt(data.getString("latitud")), Integer.parseInt(data.getString("longitud")))).title(data.getString("tipo_peligro")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(requireActivity(), "Incompatible values", duration).show();
            }
        }, this::errorResponse);
        queue.add(request);
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