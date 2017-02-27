package vaystudios.com.memory.Server;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Devan on 2/24/2017.
 */

public class LoginRequest extends StringRequest {


    private static  String LOGIN_REQUEST_URL =   "https://artificial-cares.000webhostapp.com/Login.php";
    private Map<String, String> params;

    public LoginRequest(String username,String password, Response.Listener<String> listener)
    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
