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
import android.widget.Button;
import android.widget.EditText;
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
 * Used so that the user can log in (with their username and password),
 * and navigate to the {@link SingUpOneFragment}
 */
public class LogInFragment extends Fragment {

    /** Toasts duration */
    final int duration = Toast.LENGTH_SHORT;

    /** Request method; */
    final int method = Request.Method.POST;

    /** Used to navigate to other fragments */
    private View view;

    /** Will send our HTTP requests */
    private RequestQueue queue;

    /** Username field */
    private EditText username;

    /** Password field */
    private EditText password;

    /** On clicked, will start the {@link LogInFragment#logIn} method */
    private Button logger;

    /** On clicked, the app navigates to {@link SingUpOneFragment}*/
    private Button singUp;

    /** request parameters */
    private Map<String, String> parameters;

    /**
     * Contains the processed response of the request.
     *
     * With it we can save the data of the current new user
     */
    private JSONObject userData;

    /**
     * The {@link DialogFragment#onCreateView} overwritten method.
     *
     * Used to instantiate the local fields.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        username = view.findViewById(R.id.editTextUsuarioLogIn);
        password = view.findViewById(R.id.editTextContrasenalogin);
        logger = view.findViewById(R.id.buttonIniciarSesion);
        singUp = view.findViewById(R.id.buttonIrARegistrar);
        parameters = new HashMap<>();
        queue = Volley.newRequestQueue(requireContext());
        this.view = view;
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
        logger.setOnClickListener(v -> logIn());
        singUp.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.logInFrag_to_singUpFrag));
    }

    /**
     * Creates a {@link StringRequest} object and add it to {@link LogInFragment#queue}
     * If the request is successful, calls the {@link #response(JSONObject)} method.
     * else, calls the {@link LogInFragment#errorResponse(VolleyError)} method.
     * and app navigates to {@link StartLocationFragment}.
     *
     * @see StringRequest
     */
    private void logIn(){
        CustomRequest request;
        parameters.put("nombre", username.getText().toString().trim());
        parameters.put("contrasena", password.getText().toString());
        String url = "https://www.edacarquitectos.com/perceptorDeInseguridad/login.php";
        request = new CustomRequest(method, url, parameters, this::response, this::errorResponse);
        queue.add(request);
    }

    /** Successful connection and request submission. */
    private void response(@NonNull JSONObject response){
        try {
            JSONArray arreglo = response.getJSONArray("datos");
            userData = arreglo.getJSONObject(0);
            if(userData.getString("id_usuario").equals("")) {
                storeUserData();
                Toast.makeText(requireActivity(), "datos incorrectos", duration).show();
            }
            else{
                storeUserData();
                Navigation.findNavController(view).navigate(R.id.logInFrag_to_startLocFrag);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(requireActivity(), "Valores incompatibles", duration).show();
        }
    }

    /** Failed connection */
    private void errorResponse(VolleyError error){
        Toast.makeText(requireActivity(), "Sin conexión", duration).show();
    }

    /**
     * Stores the user data in the SharedPreferences
     *
     * @see SharedPreferences
     * @see android.content.SharedPreferences.Editor
     */
    private void storeUserData(){
        try {
            SharedPreferences user;
            user = requireActivity().getSharedPreferences("current_user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = user.edit();
            editor.putString("user_id", userData.getString("id_usuario"));
            editor.putString("name", userData.getString("nombre"));
            editor.putString("birth_date", userData.getString("fecha_nacimiento"));
            editor.putString("age", userData.getString("edad"));
            editor.putString("gender", userData.getString("genero"));
            editor.putString("nationality", userData.getString("nacionalidad"));
            // Socioeconomic Level is abbreviated as se_level
            editor.putString("se_level", userData.getString("nivel_socioeconomico"));
            editor.putString("occupation", userData.getString("ocupacion"));
            editor.apply();
        }  catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(requireActivity(), "Valores incompatibles", duration).show();
        }
    }

}