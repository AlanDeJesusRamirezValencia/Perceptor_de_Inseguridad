package com.example.perceptordeinseguridad;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A {@link Fragment} subclass.
 */
public class SendingFragment extends Fragment {

    /** Toasts duration */
    final int SHORT = Toast.LENGTH_SHORT;

    /** Request method */
    final int method = Request.Method.POST;

    /** Current user data*/
    private SharedPreferences user;

    /** Request parameters*/
    private Map<String, String> parameters;

    /** Will send our HTTP requests */
    private RequestQueue queue;

    /** Used to navigate to other fragments */
    private View view;

    /**
     * The {@link DialogFragment#onCreateView} overwritten method.
     *
     * Used to instantiate the local fields.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        parameters = new HashMap<>();
        user = requireActivity().getSharedPreferences("current_user", Context.MODE_PRIVATE);
        queue = Volley.newRequestQueue(requireContext());
        View view =  inflater.inflate(R.layout.fragment_sending, container, false);
        this.view = view;
        return view;
    }

    /**
     * The {@link DialogFragment#onViewCreated(View, Bundle)} overwritten method.
     *
     * calls {@link SendingFragment#sendRequest()} method.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendRequest();
    }

    /**
     * Creates a {@link StringRequest} object and add it to {@link SendingFragment#queue}
     * If the request is successful, calls the {@link SendingFragment#response(String)} method.
     * else, calls the {@link SendingFragment#errorResponse(VolleyError)} method.
     *
     * @see StringRequest
     */
    private void sendRequest() {
        setParametros();
        String URL = "https://www.edacarquitectos.com/perceptorDeInseguridad/addperception.php";
        StringRequest request = new StringRequest(method, URL, this::response, this::errorResponse){
            @Override
            protected Map<String, String> getParams() {return parameters;}
        };
        queue.add(request);
    }

    /**
     * Successful connection and request submission.
     *
     * The JSONObject response is used to set local TextView fields text.
     */
    private void response(@NonNull String response){
        if(!(response.equals("invalido") || response.equals(""))){
            Bundle args = new Bundle();
            args.putString("idPerception", response);
            Navigation.findNavController(view)
                    .navigate(R.id.sendingFrag_to_sentFrag, args);
        }
        else Toast.makeText(requireActivity(), "Datos incompletos", SHORT).show();
    }

    /** Failed connection */
    private void errorResponse(VolleyError error){
        Toast.makeText(requireActivity(), "Sin conexión", SHORT).show();
    }

    /** Set the request parameters */
    public void setParametros() {
        double longitude = Double.parseDouble(
                Objects.requireNonNull(requireArguments().getString("longitude2")));
        double latitude = Double.parseDouble(
                Objects.requireNonNull(requireArguments().getString("latitude2")));
        Utm utm = new Utm(new double[]{longitude,latitude});
        parameters.put("longitud", requireArguments().getString("longitude2"));
        parameters.put("latitud", requireArguments().getString("latitude2"));
        parameters.put("valor_inseguridad", requireArguments().getString("level3"));
        parameters.put("y", utm.getUMTY() + "");
        parameters.put("x", utm.getUMTX() + "");
        parameters.put("zona", utm.getZona() + "");
        parameters.put("hemisferio", utm.getHemisferio() + "");
        parameters.put("fk_usuario", user.getString("user_id", "1"));
        parameters.put("contexto", requireArguments().getString("context2"));
        parameters.put("tipo_peligro", requireArguments().getString("type"));
    }
}