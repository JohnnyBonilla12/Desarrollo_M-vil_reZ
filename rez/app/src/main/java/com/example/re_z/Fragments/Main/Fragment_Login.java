package com.example.re_z.Fragments.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.re_z.Activities.Activity_Access;
import com.example.re_z.Clases.AdapterFavoritos;
import com.example.re_z.Clases.Configuration;
import com.example.re_z.Clases.RequestHandler;
import com.example.re_z.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


public class Fragment_Login extends Fragment {
    private static final String TAG = Fragment_Login.class.getSimpleName();
    private EditText loginEmail;
    private EditText loginPassword;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment_login, container, false);

        loginEmail = root.findViewById(R.id.loginEmail);
        loginPassword = root.findViewById(R.id.loginPassword);
        Button botonIngresar = root.findViewById(R.id.botonIngresar);
        Button botonRegistrar = root.findViewById(R.id.botonRegistrar);

        botonIngresar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String Email = loginEmail.getText().toString().trim();
                String Password = loginPassword.getText().toString().trim();

                if (!Email.isEmpty() && !Password.isEmpty()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.URL_LOGIN, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "Login Response: " + response.toString());
                            try {
                                JSONObject jObj = new JSONObject(response);
                                boolean error = jObj.getBoolean("error");
                                System.out.println(error);

                                // Check for error node in json
                                if (!error) {
                                    // user successfully logged in
                                    Intent intent = new Intent(view.getContext(), Activity_Access.class);
                                    intent.putExtra("email", Email);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    getActivity().finish();
                                    startActivity(intent);
                                } else {
                                    // Error in login. Get the error message
                                    String errorMsg = jObj.getString("error_msg");
                                    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Failed to Login", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    }) {
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
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
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Fragment registerFragment = new Fragment_Registro();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_nav, registerFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }
}