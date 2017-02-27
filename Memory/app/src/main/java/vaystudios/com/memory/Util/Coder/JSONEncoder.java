package vaystudios.com.memory.Util.Coder;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vaystudios.com.memory.Fragment.CanvasEditFragment;
import vaystudios.com.memory.Fragment.MemoryListFragment;
import vaystudios.com.memory.MainActivity;
import vaystudios.com.memory.R;
import vaystudios.com.memory.Server.DataUploadRequest;
import vaystudios.com.memory.Util.MiscIO.Transform;
import vaystudios.com.memory.View.CustomBitmap;
import vaystudios.com.memory.View.CustomText;

/**
 * Created by Devan on 2/25/2017.
 */

public class JSONEncoder
{

    private Activity activity;
    private Context context;
    private CanvasEditFragment.MemoryToSave memoryToSave;
    private ArrayList<CustomText> texts;
    private ArrayList<CustomBitmap> bitmaps;



    public JSONEncoder(Activity activity, Context context, CanvasEditFragment.MemoryToSave memoryToSave)
    {
        this.activity= activity;
        this.context = context;
        this.memoryToSave = memoryToSave;
        this.texts = memoryToSave.texts;
        this.bitmaps = memoryToSave.bitmaps;
        try {
            Log.d("JSON", encodeMemoryToJson(memoryToSave));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public JSONEncoder(Activity activity, Context context)
    {
        this.activity = activity;
        this.context = context;
    }


    private String encodeMemoryToJson(CanvasEditFragment.MemoryToSave memoryToSave) throws JSONException {
        JSONObject decodeString = new JSONObject();
        long suffix = 0;
        if(JSONDecoder.JSON_DECODE_STRING.length() > 0)
        {
            decodeString = new JSONObject(JSONDecoder.JSON_DECODE_STRING);
            suffix = decodeString.length() + 1;
            Log.d("JSON", "LENGTH -> " + decodeString.length());

        }


        JSONObject root = new JSONObject();
        JSONObject sub = new JSONObject();
        sub.put("title", memoryToSave.title);
        sub.put("texts", memoryToSave.texts);
        sub.put("bitmaps", memoryToSave.bitmaps);
        root.put("memory_" + suffix, sub);
        String cut = root.toString().substring(1, root.toString().length() - 1);

        return cut;
    }




    public void encode() throws JSONException {

        JSONObject root = new JSONObject();


        JSONObject jsonObject = new JSONObject();
        jsonObject.putOpt("memory", memoryToSave);
        String ret = "";


        JSONObject _returnObject = new JSONObject();
        _returnObject.put("title", memoryToSave.title);

        JSONObject _innerText = new JSONObject();
        JSONObject _innerBitmap = new JSONObject();

        CanvasEditFragment.MemoryToSave test = (CanvasEditFragment.MemoryToSave) jsonObject.get("memory");
        for(int i =0;i < test.texts.size(); i++)
        {
            CustomText customText = test.texts.get(i);

            if(customText != null)
            {
                if(customText.transform == null)
                {
                    customText.SetTransform();
                }

                _innerText.put("customText_" + i, customText.text +
                        "," + customText.transform.getX() + "," + customText.transform.getY() +
                        "," + customText.transform.getSx() + "," + customText.transform.getSy() +
                        "," + customText.transform.getRot());
            }

        }

        for(int i =0;i < test.bitmaps.size(); i++)
        {

            CustomBitmap customBitmap = test.bitmaps.get(i);
            if(customBitmap != null)
            {
                if(customBitmap.transform == null)
                {
                    customBitmap.SetTransform();
                }
                _innerBitmap.put("customBitmap_" + i , test.bitmaps.get(i).ToBitmapString() +
                    "," + customBitmap.transform.getX() + "," + customBitmap.transform.getY() +
                    "," + customBitmap.transform.getSx() + "," + customBitmap.transform.getSy() +
                    "," + customBitmap.transform.getRot());

            }
        }

        _returnObject.put("texts", _innerText);
        _returnObject.put("bitmaps", _innerBitmap);
        int decodeLength = 0;

        if(JSONDecoder.JSON_DECODE_STRING.length() > 0)
        {
            root = new JSONObject(JSONDecoder.JSON_DECODE_STRING);
            decodeLength = root.length();
        }

        root.put("memory_" + (decodeLength + 1), _returnObject);
        ret = root.toString();
        pushToServer(ret);
    }


    public void deleteAll()
    {
        JSONObject empty = new JSONObject();
        pushToServer(empty.toString());
    }




    private void pushToServer(String jsonObject)
    {

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Toast.makeText(context, success ? "Uploaded!" : "Unable to Upload. Try Again!", Toast.LENGTH_SHORT).show();

                    if(success)
                    {
                        JSONDecoder decoder = new JSONDecoder(activity, context);
                        decoder.FetchData();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        DataUploadRequest request = new DataUploadRequest(Integer.parseInt(MainActivity.loggedUser.getUser_id())
                , MainActivity.loggedUser.getUsername(),
                jsonObject, listener);


        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }


}
