package com.example.perceptordeinseguridad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingUpOneFragment extends Fragment {

    //Toasts duration
    int SHORT = Toast.LENGTH_SHORT;

    /** Request method; */
    final int method = Request.Method.POST;

    private EditText passwordConfirmation;
    private EditText password;
    private EditText email;
    private EditText name;
    private Button BtnContinue;
    private RequestQueue queue;
    private Map<String, String> parameters;

    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_sing_up_one, container, false);
        email = view.findViewById(R.id.editTextEmail);
        name = view.findViewById(R.id.editTextUserSingUp);
        password = view.findViewById(R.id.editTextPasswordSingUp);
        passwordConfirmation = view.findViewById(R.id.editTextConfirmPassword);
        BtnContinue = view.findViewById(R.id.button_continue1);
        queue = Volley.newRequestQueue(requireContext());
        parameters = new HashMap<>();
        this.view = view;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BtnContinue.setOnClickListener(v -> {
            if (checkFilledFields())
                checkUsername();
        });
    }

    /** Check all fields are filled. If they're, return true.*/
    public boolean checkFilledFields() {
        //if any data is empty, return false

        if (email.getText().toString().equals("")) {
            this.email.setError("Empty field");
            return false;
        }

        if (email.getText().toString().contains("@")){
            String[] parts = email.getText().toString().split("@");
            if (!parts[1].contains(".")){
                this.email.setError("Invalid email format");
                return false;
            }
        }
        else {
            this.email.setError("Invalid email format");
        }

        if (name.getText().toString().equals("")) {
            this.name.setError("Empty field");
            return false;
        }
        if (password.getText().toString().equals("")) {
            this.password.setError("Empty field");
            return false;
        }

        if (!checkPasswordFormat(this.password.getText().toString())){
            this.password.setError("Password must contain at least a Capital letter, a lowercase letter, a number and a special character");
            return false;
        }

        if (this.password.getText().toString().length() < 8){
            this.password.setError("Password must be at least 8 characters");
            return false;
        }
        return comparePasswords();
    }

    /**
     * Check if passwords are equals.
     *
     * If they're, return false. If aren't, return true.
     */
    private boolean comparePasswords(){
        if (!(password.getText().toString().equals(passwordConfirmation.getText().toString()))){
            passwordConfirmation.setError("Passwords do not match");
            password.setText("");
            passwordConfirmation.setText("");
            return false;
        }
        return true;
    }

    private boolean checkPasswordFormat(@NonNull String password){
        boolean number = false;
        boolean capital = false;
        boolean lowercase = false;
        for (int i = 0; i <= password.length() -1; i ++){
            if (Character.isDigit(password.charAt(i))) {
                number = true;
            } else if (Character.isUpperCase(password.charAt(i))) {
                capital = true;
            } else if (Character.isLowerCase(password.charAt(i))){
                lowercase = true;
            }
        }
        return number && capital && lowercase;
    }

    /**
     * Sends an HTTP request to check that name or email aren't already occupied.
     */
    private void checkUsername(){
        parameters.put("name", name.getText().toString().trim());
        parameters.put("email", email.getText().toString().trim());
        String url = "https://www.edacarquitectos.com/perceptorDeInseguridad/checkusername.php";
        StringRequest request = new StringRequest(method, url, this::response, this::errorResponse){
            @Override
            protected Map<String, String> getParams() {return parameters;}
        };
        queue.add(request);
    }

    /** Successful connection and request submission. */
    private void response(@NonNull String response){
        if (response.trim().equals("free")){
            Bundle args = new Bundle();
            args.putString("mail",email.getText().toString().trim());
            args.putString("user", name.getText().toString().trim());
            args.putString("password",password.getText().toString());
            Navigation.findNavController(view).navigate(R.id.singUpFrag_to_singUpTwoFrag, args);
        }
        else {
            Toast.makeText(requireActivity(), response, Toast.LENGTH_SHORT).show();
        }
    }

    /** Failed connection */
    private void errorResponse(VolleyError error){
        Toast.makeText(requireActivity(), "Disconnected", SHORT).show();
    }
}