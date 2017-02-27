package vaystudios.com.memory.Activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import vaystudios.com.memory.R;
import vaystudios.com.memory.Server.*;

public class RegisterActivity extends AppCompatActivity {

    private EditText register_userName;
    private EditText register_password;
    private EditText register_name;
    private EditText register_age;
    private Button register_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        register_userName = (EditText)findViewById(R.id.et_registerUserName);
        register_password = (EditText)findViewById(R.id.et_registerPassword);
        register_name = (EditText)findViewById(R.id.et_registerName);
        register_age = (EditText)findViewById(R.id.et_registerAge);
        register_button = (Button)findViewById(R.id.et_registerButton);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = register_name.getText().toString();
                final String username = register_userName.getText().toString();
                final String password = register_password.getText().toString();
                final int age = Integer.parseInt(register_age.getText().toString());
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success)
                            {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed").setNegativeButton("Retry",null).create().show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, username, age, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });
    }
}
