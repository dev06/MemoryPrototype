package vaystudios.com.memory.Server;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Devan on 2/23/2017.
 */
public class RegisterRequest extends StringRequest {

    private static  String REGISTER_REQUEST_URL =   "https://artificial-cares.000webhostapp.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, int age, String password, Response.Listener<String> listener)
    {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        Log.d("Vay", username);
        params.put("username", username);
        params.put("age", age + "");
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
