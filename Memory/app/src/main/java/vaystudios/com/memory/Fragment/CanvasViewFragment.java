package vaystudios.com.memory.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import vaystudios.com.memory.MainActivity;
import vaystudios.com.memory.R;
import vaystudios.com.memory.View.CanvasView;
import vaystudios.com.memory.View.CustomBitmap;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Devan on 2/4/2017.
 */

public class CanvasViewFragment extends Fragment
{
    RelativeLayout relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.canvas_view_fragment_layout, container, false);
        ImageView canvasOptionButton = (ImageView)view.findViewById(R.id.btn_canvasOptions);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.canvas_layout);
//        View v = new CanvasView(getActivity().getApplicationContext(), null);
//        View v2 = new CustomBitmap(getActivity().getApplicationContext(), null, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), 300,300);
//        View v3 = new CustomBitmap(getActivity().getApplicationContext(), null, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), 100,100);
////
//        relativeLayout.addView(v2);
//        relativeLayout.addView(v3);


        //CustomBitmap b = new CustomBitmap(getActivity().getApplicationContext(), null, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher) );
       // relativeLayout.addView(b);
        for(int i =0;i < 10;i ++)
        {
            CustomBitmap b = new CustomBitmap(getActivity(),null, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            relativeLayout.addView(b);
        }

//        CustomBitmap b2 = new CustomBitmap(getActivity(),null, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
//


//        relativeLayout.addView(b2);
//
//        MainActivity.bitmaps.add(b);


        registerForContextMenu(canvasOptionButton);



        return view;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.art_menu, menu);


    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.menu_takePhoto:
            {
                try {
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File photo = createImageFile();

                    if(photo != null)
                    {
                        Uri photoURI = FileProvider.getUriForFile(getActivity(), "vaystudios.com.memory", photo);
                        i.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(i, 0);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        return super.onContextItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK)
        {

            Bitmap source = BitmapFactory.decodeFile(path);


            Bitmap bitmap = scaleBitmapToScreenSize(RotateBitmap(source, 90));
            if(bitmap != null)
            {
                relativeLayout.addView(new CustomBitmap(getActivity().getApplicationContext(), null, bitmap));
            }
            Log.d("Vay", BitmapFactory.decodeFile(path) + "");

        }

    }



    private Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix m = new Matrix();
        m.postRotate(angle);
        return Bitmap.createBitmap(source, 0,0, source.getWidth(), source.getHeight(),m, true);
    }

    private Bitmap scaleBitmapToScreenSize(Bitmap source)
    {
        Bitmap result = null;
        try
        {

           DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            float ratio = (float)displayMetrics.widthPixels / (float)displayMetrics.heightPixels;
            Log.d("Vay", ratio + "");

            result = Bitmap.createScaledBitmap(source, (int)(source.getWidth() * ratio), (int)(source.getHeight() * ratio), true);



        }catch(Exception e)
        {

        }

        return result;
    }




    String path;
    private File createImageFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" +  timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        path = image.getAbsolutePath();
        return image;
    }





}
