package vaystudios.com.memory.Server;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import vaystudios.com.memory.R;

public class UserActivity extends AppCompatActivity {

    private String userName;
    private int userId;
    private TextView userArea_welcome;
    private TextView userArea_name;
    private TextView userArea_age;
    private TextView userArea_receiveText;
    private EditText userArea_textToUpload;
    private Button   userArea_uploadButton;
    private Button   userArea_downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user);
//
//        userArea_welcome = (TextView)findViewById(R.id.tv_ua_welcome);
//        userArea_name = (TextView)findViewById(R.id.tv_ua_name);
//        userArea_age = (TextView)findViewById(R.id.tv_ua_age);
//        userArea_textToUpload = (EditText)findViewById(R.id.et_ua_textToUpload);
//        userArea_uploadButton = (Button)findViewById(R.id.bt_ua_upload);
//        userArea_downloadButton= (Button)findViewById(R.id.bt_ua_download);
//        userArea_receiveText = (TextView)findViewById(R.id.tv_ua_receivedText);
//
//        Intent intent = getIntent();
//        if(intent != null)
//        {
//            userArea_welcome.setText("Welcome, " + intent.getStringExtra("name"));
//            userArea_name.setText(intent.getStringExtra("name"));
//            userArea_age.setText(intent.getStringExtra("age"));
//            userName = intent.getStringExtra("username");
//            userId = Integer.parseInt(intent.getStringExtra("userId"));
//        }
//
//        final JSONObject object = new JSONObject();
//        userArea_uploadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String text = userArea_textToUpload.getText().toString();
//                try {
//                    object.put("title", text);
//                    object.put("length", text.length() + "");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                Log.d("Vay", "Object -> " + object.toString());
//
//                Response.Listener<String> listener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("Vay", response);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            boolean success = jsonObject.getBoolean("success");
//                            if(success)
//                            {
//                                Toast.makeText(getApplicationContext(), "Upload Successful", Toast.LENGTH_SHORT).show();
//                            }else
//                            {
//                                Toast.makeText(getApplicationContext(), "Upload Unsuccessful", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//                        }
//                    }
//                };
//                DataUploadRequest dataUploadRequest = new DataUploadRequest(userId, userName, object.toString(), listener);
//                RequestQueue queue = Volley.newRequestQueue(UserActivity.this);
//                queue.add(dataUploadRequest);
//            }
//        });
//
//
//
//        userArea_downloadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Response.Listener<String> listener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("Vay", "Download -> " + response);
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            String memory = jsonResponse.getString("memory");
//                            JSONObject parseObject = new JSONObject(memory);
//
//                            String title = parseObject.getString("title");
//                            String length = parseObject.getString("length");
//
//                            userArea_receiveText.setText("Title: " + title + "\n" + " Length " + length);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//
//
//                DataDownloadRequest dataDownloadRequest = new DataDownloadRequest(userId, userName, listener);
//                RequestQueue queue = Volley.newRequestQueue(UserActivity.this);
//                queue.add(dataDownloadRequest);
//
//            }
//
//        });
    }
}
