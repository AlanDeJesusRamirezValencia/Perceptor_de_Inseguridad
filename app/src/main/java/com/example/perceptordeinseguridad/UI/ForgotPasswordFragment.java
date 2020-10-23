package com.example.perceptordeinseguridad.UI;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perceptordeinseguridad.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {

    private EditText emailAddress;
    private Button sendEmail;

    /** request parameters */
    private Map<String, String> parameters;

    /** Request method; */
    final int method = Request.Method.POST;

    /** Used to navigate to other fragments */
    private View view;

    /** Will send our HTTP requests */
    private RequestQueue queue;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        emailAddress = view.findViewById(R.id.editText_emailAddress);
        sendEmail = view.findViewById(R.id.button_sendEmail);
        parameters = new HashMap<>();
        queue = Volley.newRequestQueue(requireContext());
        this.view = view;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendEmail.setOnClickListener(view1 -> sendRequest());
    }

    private void sendRequest(){
        StringRequest request;
        parameters.put("password", emailAddress.getText().toString());
        String url = "https://www.edacarquitectos.com/perceptorDeInseguridad/login.php";
        request = new StringRequest(method, url, this::response, this::errorResponse){
            @Override
            protected Map<String, String> getParams() {
                return parameters;
            }
        };
        queue.add(request);
    }

    /** Successful connection and request submission. */
    private void response(@NonNull String response){
        Toast.makeText(requireContext(), "Your password has been sent", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(this.view).navigate(R.id.forgotPwFrag_to_logInFrag);
    }

    /** Failed connection */
    private void errorResponse(VolleyError error){
        Toast.makeText(requireActivity(), "Disconnected", Toast.LENGTH_LONG).show();
    }
}