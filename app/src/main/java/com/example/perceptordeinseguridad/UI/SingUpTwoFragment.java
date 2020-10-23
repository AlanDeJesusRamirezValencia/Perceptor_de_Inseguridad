package com.example.perceptordeinseguridad.UI;

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
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.perceptordeinseguridad.CustomRequest;
import com.example.perceptordeinseguridad.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingUpTwoFragment extends Fragment {

    /** Toasts duration */
    final int duration = Toast.LENGTH_SHORT;

    /** Request method */
    final int method = Request.Method.POST;

    /**
     * Contains the processed response of the request.
     *
     * With it we can save the data of the current new user
     */
    private JSONObject userData;

    private RequestQueue queue;
    private Map<String, String> parameters;

    private RadioButton man;
    private RadioButton woman;
    private String gender;
    private Calendar calendar;
    private DatePicker datePicker;
    private Button btnContinue;

    /** Used to navigate to other fragments */
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_sing_up_two, container, false);
        datePicker = view.findViewById(R.id.date_picker);
        man = view.findViewById(R.id.radioButtonMan);
        woman = view.findViewById(R.id.radioButtonWoman);
        calendar = Calendar.getInstance();
        btnContinue = view.findViewById(R.id.button_continue2);
        queue = Volley.newRequestQueue(requireContext());
        parameters = new HashMap<>();
        this.view = view;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnContinue.setOnClickListener(v -> {
            if(checkData()){
                sendRequest();
            }
        });
    }

    private void sendRequest(){
        setParameters();
        CustomRequest request;
        String url = "https://www.edacarquitectos.com/perceptorDeInseguridad/singup.php";
        request = new CustomRequest(method, url, parameters, this::response, this::errorResponse);
        queue.add(request);
    }

    /** Successful connection and request submission. */
    private void response(@NonNull JSONObject response){
        try {
            JSONArray array = response.getJSONArray("data");
            userData = array.getJSONObject(0);
            if(!userData.getString("pk_user").equals("")){
                storeUserData();
                Navigation.findNavController(view).navigate(R.id.singUpTwoFrag_to_startLocFrag);
            }
            else Toast.makeText(requireContext(), "Error. Try later.", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(requireActivity(), "Incompatible data", duration).show();
        }
    }

    /** Failed connection */
    private void errorResponse(VolleyError error){
        Toast.makeText(requireActivity(), "disconnected", duration).show();
    }

    private boolean checkData(){
        //Toast's duration
        int SHORT = 0;
        if (man.isChecked())
            gender = "1";
        else if (woman.isChecked())
            gender = "2";
        else{
            Toast.makeText(requireActivity(), "Gender not assigned", SHORT).show();
            return false;
        }
        if (calendar.get(Calendar.YEAR)-(datePicker.getYear()) < 18){
            Toast.makeText(requireActivity(), "You must be of legal age", SHORT).show();
            return false;
        }
        return true;
    }

    private void storeUserData(){
        try {
            SharedPreferences user;
            user = requireActivity().getSharedPreferences("current_user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = user.edit();
            editor.putString("pk_user", userData.getString("pk_user"));
            editor.putString("name", userData.getString("name"));
            editor.putString("date_birth", userData.getString("date_birth"));
            editor.putString("age", userData.getString("age"));
            editor.putString("email", userData.getString("email"));
            editor.putString("gender", userData.getString("gender"));
            editor.apply();
        }  catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(requireActivity(), "Incompatible data", duration).show();
        }
    }

    private void setParameters(){
        String date = datePicker.getYear()+"-"
                +(datePicker.getMonth()+1)+"-"
                +datePicker.getDayOfMonth();
        parameters.put("name", requireArguments().getString("user"));
        parameters.put("password", requireArguments().getString("password"));
        parameters.put("email", requireArguments().getString("mail"));
        parameters.put("gender", gender);
        parameters.put("date_birth", date);
    }
}