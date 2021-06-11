package com.example.re_z.Fragments.Main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.re_z.Clases.Configuration;
import com.example.re_z.Clases.RequestHandler;
import com.example.re_z.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Fragment_Registro extends Fragment {

    private static final String TAG = Fragment_Registro.class.getSimpleName();
    private EditText registroEmail;
    private EditText registroPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment_registro, container, false);

        registroEmail = root.findViewById(R.id.registroEmail);
        registroPassword = root.findViewById(R.id.registroPassword);
        Button botonRegistrar = root.findViewById(R.id.botonConfirmarRegistro);
        Button botonCancelar = root.findViewById(R.id.botonCancelarRegistro);

        // Login button Click Event
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String Email = registroEmail.getText().toString().trim();
                String Password = registroPassword.getText().toString().trim();

                if (!Email.isEmpty() && !Password.isEmpty()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.URL_REGISTER, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "Register Response: " + response.toString());
                            try {
                                JSONObject jObj = new JSONObject(response);
                                boolean error = jObj.getBoolean("error");
                                System.out.println(error);

                                // Check for error node in json
                                if (!error) {
                                    // user successfully registered
                                    System.out.println("Usuario Registrado Correctamente");
                                    // Launch main activity
                                    //Fragment loginFragment = new Fragment_Login();
                                    //FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    //transaction.replace(R.id.nav, loginFragment );
                                    //transaction.addToBackStack(null);
                                    //transaction.commit();
                                } else {
                                    // Error in login. Get the error message
                                    String errorMsg = jObj.getString("error_msg");
                                    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Failed to Register",Toast.LENGTH_SHORT).show();
                        }
                    }){
                        protected Map<String , String> getParams() throws AuthFailureError {
                            Map<String , String> params = new HashMap<>();
                            params.put("email", Email);
                            params.put("password", Password);
                            return params;
                        }
                    };
                    RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
                } else {
                    Toast.makeText(getActivity(), "Please enter Email and Password!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Link to Register Screen
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Fragment loginFragment = new Fragment_Login();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_nav, loginFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return root;
    }
}