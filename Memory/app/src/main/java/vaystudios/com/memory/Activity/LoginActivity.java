package vaystudios.com.memory.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import vaystudios.com.memory.MainActivity;
import vaystudios.com.memory.Object.User;
import vaystudios.com.memory.R;
import vaystudios.com.memory.Server.*;

public class LoginActivity extends AppCompatActivity {


    private EditText login_password;
    private EditText login_userName;
    private Button login_button;
    private TextView login_registerLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_userName = (EditText)findViewById(R.id.et_loginUserName);
        login_password = (EditText)findViewById(R.id.et_loginPassword);
        login_button = (Button) findViewById(R.id.bt_loginButton);
        login_registerLink = (TextView)findViewById(R.id.tv_loginRegisterHere);

        login_userName.setText("dev_06");
        login_password.setText("123");

        login_registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vaystudios.com.memory.Activity.LoginActivity.this, vaystudios.com.memory.Activity.RegisterActivity.class);
                startActivity(intent);
            }
        });



      login_button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              final String username = login_userName.getText().toString();
              final String password = login_password.getText().toString();

              Response.Listener<String> listener = new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response)
                  {

                      try {
                          JSONObject jsonResponse = new JSONObject(response);
                          boolean success = jsonResponse.getBoolean("success");
                          if(success)
                          {
                              Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                              intent.putExtra("name", jsonResponse.getString("name"));
                              intent.putExtra("user_id", jsonResponse.getString("user_id"));
                              intent.putExtra("username", username);

                              LoginActivity.this.startActivity(intent);
                          }else
                          {
                              Toast.makeText(getApplicationContext(), "Invalid Username or Password. Try Again!", Toast.LENGTH_SHORT).show();
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              };

              LoginRequest request = new LoginRequest(username, password, listener);
              RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
              queue.add(request);

          }
      });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if(hasFocus)
        {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }
}
