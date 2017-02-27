package vaystudios.com.memory.Server;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Devan on 2/25/2017.
 */

public class DataUploadRequest extends StringRequest {

    private static  String DATEUPLOAD_REQUEST_URL =   "https://artificial-cares.000webhostapp.com/DataUpload.php";
    private Map<String, String> params;

    public DataUploadRequest(int userId, String username, String data, Response.Listener<String> listener)
    {
        super(Request.Method.POST, DATEUPLOAD_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", userId + "");
        params.put("data", data);
        params.put("username", username);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
