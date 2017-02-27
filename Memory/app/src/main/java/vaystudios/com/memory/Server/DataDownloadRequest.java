package vaystudios.com.memory.Server;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Devan on 2/25/2017.
 */

public class DataDownloadRequest extends StringRequest {

    private static final String DOWNLOAD_REQUEST_URL = "https://artificial-cares.000webhostapp.com/DataDownload.php";

    private Map<String, String> params;

    public DataDownloadRequest(int userId, String username, Response.Listener<String> listener)
    {
        super(Request.Method.POST, DOWNLOAD_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", userId + "");
        params.put("username", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
