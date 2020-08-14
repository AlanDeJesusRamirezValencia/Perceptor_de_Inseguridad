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
        email = view.findViewById(R.id.correoElectronico);
        name = view.findViewById(R.id.editTextUsuarioRegistro);
        password = view.findViewById(R.id.editTextContrasenaRegistro);
        passwordConfirmation = view.findViewById(R.id.confirmarContraseña);
        BtnContinue = view.findViewById(R.id.button_continuar1);
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
            this.email.setError("Campo vacío");
            return false;
        }

        if (email.getText().toString().contains("@")){
            String[] parts = email.getText().toString().split("@");
            if (!parts[1].contains(".")){
                this.email.setError("Formato de correo electrónico inválido");
                return false;
            }
        }
        else {
            this.email.setError("Formato de correo electrónico inválido");
        }

        if (name.getText().toString().equals("")) {
            this.name.setError("Campo vacío");
            return false;
        }
        if (password.getText().toString().equals("")) {
            this.password.setError("Campo vacío");
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
            passwordConfirmation.setError("Contraseñas no coinciden");
            password.setText("");
            passwordConfirmation.setText("");
            return false;
        }
        return true;
    }

    /**
     * Sends an HTTP request to check that name or email aren't already occupied.
     */
    private void checkUsername(){
        parameters.put("nombre", name.getText().toString().trim());
        parameters.put("correo", email.getText().toString().trim());
        Toast.makeText(requireActivity(), parameters.toString(), SHORT).show();
        String url = "https://www.edacarquitectos.com/perceptorDeInseguridad/checkusername.php";
        StringRequest request = new StringRequest(method, url, this::response, this::errorResponse){
            @Override
            protected Map<String, String> getParams() {return parameters;}
        };
        queue.add(request);
    }

    /** Successful connection and request submission. */
    private void response(@NonNull String response){
        if (response.trim().equals("libre")){
            Bundle args = new Bundle();
            args.putString("correo",email.getText().toString().trim());
            args.putString("usuario", name.getText().toString().trim());
            args.putString("contrasena",password.getText().toString());
            Navigation.findNavController(view).navigate(R.id.singUpFrag_to_singUpTwoFrag, args);
        }
        else {
            Toast.makeText(requireActivity(), response, Toast.LENGTH_SHORT).show();
        }
    }

    /** Failed connection */
    private void errorResponse(VolleyError error){
        Toast.makeText(requireActivity(), "Sin conexión", SHORT).show();
    }
}