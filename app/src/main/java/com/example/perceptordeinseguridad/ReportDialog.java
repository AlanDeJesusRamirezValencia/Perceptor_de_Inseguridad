package com.example.perceptordeinseguridad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportDialog extends DialogFragment {

    /** Toasts duration */
    final int SHORT = Toast.LENGTH_SHORT;

    /** Request method; */
    final int method = Request.Method.POST;

    /** The button to close the current DialogFragment */
    private Button close;

    /** Show the level of insecurity */
    private TextView level;

    /** Show the latitude of the perception */
    private TextView latitude;

    /** Show the longitude of the perception */
    private TextView longitude;

    /** Show the x UTM value of the perception */
    private TextView x;

    /** Show the y UTM value of the perception */
    private TextView y;

    /** Show the UTM zone of the perception */
    private TextView zone;

    /** Show the UTM hemisphere of the perception */
    private TextView hemisphere;

    /** Show the context of the perception */
    private TextView context;

    /** Show the type of danger of the perception */
    private TextView type;

    /** Will send our HTTP requests */
    private RequestQueue queue;

    /** Request parameters */
    private Map<String, String> parameters;

    /**
     * The {@link DialogFragment#onCreateView} overwritten method.
     *
     * Used to instantiate the local fields.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.dialog_report, container, false);
        level = view.findViewById(R.id.tv_level);
        latitude = view.findViewById(R.id.tv_latitude);
        longitude = view.findViewById(R.id.tv_longitude);
        x = view.findViewById(R.id.tv_x);
        y = view.findViewById(R.id.tv_y);
        zone = view.findViewById(R.id.tv_zone);
        hemisphere = view.findViewById(R.id.tv_hemisphere);
        context = view.findViewById(R.id.tv_context);
        type = view.findViewById(R.id.tv_type);
        queue = Volley.newRequestQueue(requireContext());
        parameters = new HashMap<>();
        parameters.put("id_percepcion", requireArguments().getString("perception_id"));
        close = view.findViewById(R.id.button_closeReport);
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
        close.setOnClickListener( v ->  dismiss());
        sendRequest();
    }

    /**
     * Creates a {@link StringRequest} object and add it to {@link ReportDialog#queue}
     * If the request is successful, calls the {@link ReportDialog#response(JSONObject)} method.
     * else, calls the {@link ReportDialog#errorResponse(VolleyError)} method.
     *
     * @see StringRequest
     */
    private void sendRequest(){
        CustomRequest request;
        String url = "https://www.edacarquitectos.com/perceptorDeInseguridad/searchperception.php";
        request = new CustomRequest(method, url, parameters, this::response, this::errorResponse);
        queue.add(request);
    }

    /**
     * Successful connection and request submission.
     *
     * The JSONObject response is used to set local TextView fields text.
     */
    private void response(JSONObject response){
        try {
            JSONArray array = response.getJSONArray("datos");
            JSONObject JSONResponse = array.getJSONObject(0);
            if (!(JSONResponse.getString("id_percepcion").equals(""))){
                level.setText(JSONResponse.getString("valor_inseguridad"));
                hemisphere.setText(JSONResponse.getString("hemisferio"));
                context.setText(JSONResponse.getString("contexto"));
                type.setText(JSONResponse.getString("tipo_peligro"));
                longitude.setText(JSONResponse.getString("longitud"));
                latitude.setText(JSONResponse.getString("latitud"));
                zone.setText(JSONResponse.getString("zona"));
                x.setText(JSONResponse.getString("x"));
                y.setText(JSONResponse.getString("y"));
            }
            else
                Toast.makeText(requireActivity(), "Wrong report", SHORT).show();
        } catch (JSONException e) {
            Toast.makeText(requireActivity(), "Invalid array", SHORT).show();
        }
    }

    /** Failed connection */
    private void errorResponse(VolleyError error){
        Toast.makeText(requireActivity(), "Disconnected", SHORT).show();
    }
}