package vaystudios.com.memory.Util.Coder;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import vaystudios.com.memory.Fragment.MemoryListFragment;
import vaystudios.com.memory.MainActivity;
import vaystudios.com.memory.Object.MemoryObject;
import vaystudios.com.memory.Server.DataDownloadRequest;
import vaystudios.com.memory.Util.MiscIO.Transform;
import vaystudios.com.memory.View.CustomBitmap;
import vaystudios.com.memory.View.CustomText;

/**
 * Created by Devan on 2/25/2017.
 */

public class JSONDecoder
{

    public static String JSON_DECODE_STRING = "";
    //Will need to return a list of custom bitmaps and custom texts
    //that will be added to the relative view in "CanvasViewFragment"
    private Activity activity;
    private Context context;
    private ArrayList<CustomBitmap> customBitmaps;
    private ArrayList<CustomText> customTexts;


    public JSONDecoder(Activity activity, Context context)
    {
        this.context = context;
        this.activity = activity;
    }

    public void FetchData()
    {
        recieveDataFromServer();
    }



    public ArrayList<CustomBitmap> decodeCustomBitmap()
    {
        return null;
    }

    public ArrayList<CustomText> decodeCustomText()
    {

        return null;
    }



    private void recieveDataFromServer()
    {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    MainActivity.memoryObjects.clear();
                    JSONObject jsonResponse = new JSONObject(response);

                    String json = jsonResponse.getString("memory");
                    JSON_DECODE_STRING = json;

                    JSONObject object = new JSONObject(json);

                    if(object.names() == null)
                        return;

                    for(int ix = 0;ix < object.names().length(); ix++)
                    {
                        JSONObject memory_object = (JSONObject)object.get("memory_" + (ix + 1));
                        MemoryObject memory = new MemoryObject();

                        memory.setTitle(memory_object.getString(memory_object.names().getString(0)));

                        ArrayList<CustomText> customTexts = new ArrayList<>();
                        JSONObject texts = memory_object.getJSONObject("texts");

                        // Extract the "texts" from the JSON Node
                        if(texts != null && texts.names() != null)
                        {
                            for(int tx = 0; tx < texts.names().length(); tx++)
                            {
                                String extractedText = texts.getString(texts.names().getString(tx));
                                customTexts.add(ParseCustomText(extractedText));
                            }

                        }


                        ArrayList<CustomBitmap> customBitmaps = new ArrayList<>();
                        JSONObject bitmaps = memory_object.getJSONObject("bitmaps");

                        if(bitmaps != null && bitmaps.names() != null)
                        {
                            for(int bx = 0; bx < bitmaps.names().length(); bx++)
                            {
                                String extractedBitmap = bitmaps.getString(bitmaps.names().getString(bx));
                                customBitmaps.add(ParseCustomBitmap(extractedBitmap));
                            }
                        }

                        memory.setTexts(customTexts);
                        memory.setBitmaps(customBitmaps);

                        MainActivity.memoryObjects.add(0,memory);


                    }



                    MemoryListFragment memoryListFragment = (MemoryListFragment)activity.getFragmentManager().findFragmentByTag("MEMORY_LIST_FRAGMENT");
                    if(memoryListFragment != null)
                    {
                        memoryListFragment.UpdateMemoryList();
                        Toast.makeText(context, "Refreshed!", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };


        DataDownloadRequest dataDownloadRequest = new DataDownloadRequest(Integer.parseInt(MainActivity.loggedUser.user_id), MainActivity.loggedUser.getUsername(), listener);
        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(dataDownloadRequest);
    }



    private CustomText ParseCustomText(String text)
    {
        float aspect = (float)MainActivity.DISPLAY_WIDTH/ (float)MainActivity.DISPLAY_HEIGHT;

        float aspect_w = MainActivity.DISPLAY_WIDTH;
        float aspect_h = MainActivity.DISPLAY_HEIGHT;


        CustomText customText = null;
        String[] data = text.split(",");


        Transform transform = new Transform();
        transform.setX(Float.parseFloat(data[1]) * MainActivity.DISPLAY_DENSITY);
        transform.setY(Float.parseFloat(data[2]) * MainActivity.DISPLAY_DENSITY);

        transform.setSx(Float.parseFloat(data[3]));
        transform.setSy(Float.parseFloat(data[4]));




        transform.setRot(Float.parseFloat(data[5]));

        customText = new CustomText(context, null, data[0], transform, false);

        return customText;
    }

    private CustomBitmap ParseCustomBitmap(String text) throws UnsupportedEncodingException {


        float aspect_w = MainActivity.DISPLAY_WIDTH;
        float aspect_h = MainActivity.DISPLAY_HEIGHT;


        CustomBitmap customBitmap = null;
        String[] data = text.split(",");

        Transform transform = new Transform();
        Log.d("DECODE", Float.parseFloat(data[1]) + "*" + aspect_w + " = " +(Float.parseFloat(data[1]) * aspect_w));

        transform.setX(Float.parseFloat(data[1]) * aspect_w);
        transform.setY(Float.parseFloat(data[2]) * aspect_h);
        transform.setSx(Float.parseFloat(data[3]));
        transform.setSy(Float.parseFloat(data[4]));

        transform.setRot(Float.parseFloat(data[5]));

        customBitmap = new CustomBitmap(context, null, data[0] , transform, false);
        return customBitmap;
    }







}
