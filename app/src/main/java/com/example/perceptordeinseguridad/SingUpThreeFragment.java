package com.example.perceptordeinseguridad;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingUpThreeFragment extends Fragment {

    /** Toasts duration */
    final int duration = Toast.LENGTH_SHORT;

    /** Request method */
    final int method = Request.Method.POST;

    /** Used to navigate to other fragments */
    private View view;

    private EditText nationality;
    private EditText occupation;
    private RadioButton middle;
    private RadioButton low;
    private RadioButton high;
    private Button skipAndRecord;
    private Button registrar;
    private RequestQueue queue;
    private Map<String, String> parameters;
    private String socioeconomicLevel;

    /**
     * Contains the processed response of the request.
     *
     * With it we can save the data of the current new user
     */
    private JSONObject userData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_sing_up_three, container, false);
        nationality = view.findViewById(R.id.nationality);
        occupation = view.findViewById(R.id.occupation);
        low = view.findViewById(R.id.radioButtonLow);
        middle = view.findViewById(R.id.radioButtonMiddle);
        high = view.findViewById(R.id.radioButtonHigh);
        registrar = view.findViewById(R.id.buttonRegistrar1);
        skipAndRecord = view.findViewById(R.id.buttonSkipAndRecord);
        queue = Volley.newRequestQueue(requireContext());
        parameters = new HashMap<>();
        this.view = view;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registrar.setOnClickListener(v -> {
            if (checkFilledFields()){
                setOptionalParameters();
                sendRequest();
            }
        });
        skipAndRecord.setOnClickListener(v -> sendRequest());
    }

    private boolean checkFilledFields(){
        if (nationality.getText().toString().isEmpty()){
            nationality.setError("required field");
            return false;
        }
        if (occupation.getText().toString().isEmpty()){
            occupation.setError("required field");
            return false;
        }
        if (!low.isChecked() && !middle.isChecked() && !high.isChecked()) {
            Toast.makeText(
                    requireActivity(),
                    "Socioeconomic level to not assigned", duration).show();
            return false;
        }
        else {
            if (low.isChecked())
                socioeconomicLevel = "bajo";
            else if (middle.isChecked())
                socioeconomicLevel = "medio";
            else
                socioeconomicLevel = "alto";
        }
        return true;
    }

    private void sendRequest(){
        setMandatoryParameters();
        CustomRequest request;
        Toast.makeText(requireActivity(), parameters.toString(), duration).show();
        String url = "https://www.edacarquitectos.com/perceptorDeInseguridad/singup.php";
        request = new CustomRequest(method, url, parameters, this::response, this::errorResponse);
        queue.add(request);
    }

    /** Successful connection and request submission. */
    private void response(@NonNull JSONObject response){
        try {
            Toast.makeText(requireActivity(), response.toString(), Toast.LENGTH_SHORT).show();
            JSONArray array = response.getJSONArray("datos");
            userData = array.getJSONObject(0);
            if(userData.getString("id_usuario").equals("")) {
                storeUserData();
                Toast.makeText(
                        requireActivity(),
                        userData.getString("mensaje"),
                        duration
                ).show();
            }
            else{
                storeUserData();
                Toast.makeText(requireActivity(), userData.toString(), duration).show();
                Navigation.findNavController(view).navigate(R.id.singUpThreeFrag_to_startLocFrag);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(requireActivity(), "Incompatible data", duration).show();
        }
    }

    /** Failed connection */
    private void errorResponse(VolleyError error){
        Toast.makeText(requireActivity(), "disconnected", duration).show();
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
            Toast.makeText(requireActivity(), "Incompatible data", duration).show();
        }
    }

    private void setMandatoryParameters(){
        parameters.put("nombre", requireArguments().getString("user"));
        parameters.put("contrasena", requireArguments().getString("password"));
        parameters.put("genero", requireArguments().getString("genero"));
        parameters.put("fecha_nacimiento", requireArguments().getString("fecha"));
        parameters.put("correo", requireArguments().getString("mail"));
    }

    private void setOptionalParameters() {
        parameters.put("nivel_socioeconomico", socioeconomicLevel);
        parameters.put("nacionalidad", nationality.getText().toString());
        parameters.put("ocupacion", occupation.getText().toString());
    }
}