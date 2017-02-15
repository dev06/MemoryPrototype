package vaystudios.com.memory.Fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vaystudios.com.memory.MainActivity;
import vaystudios.com.memory.Object.MemoryObject;
import vaystudios.com.memory.R;
import vaystudios.com.memory.View.CustomBitmap;
import vaystudios.com.memory.View.CustomText;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Devan on 2/4/2017.
 */

public class CanvasEditFragment extends Fragment
{
    private ArrayList<CustomText> customTexts = new ArrayList<CustomText>();
    private ArrayList<CustomBitmap> customBitmaps = new ArrayList<CustomBitmap>();


    RelativeLayout relativeLayout;
    View view;
    String path;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.canvas_edit_fragment_layout, container, false);
        ImageView canvasOptionButton = (ImageView)view.findViewById(R.id.btn_canvasOption);
        ImageView canvasCompleteButton = (ImageView)view.findViewById(R.id.btn_canvasComplete);

        // user finishes designing the canvas and wants to finish it up.
        canvasCompleteButton.setOnClickListener(new View.OnClickListener() {
            MemoryObject memoryObject = new MemoryObject("New Memory!");

            @Override
            public void onClick(View v) {
                // generates the alert builder dialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Memory Name");
                builder.setIcon(R.mipmap.ic_launcher);
                final EditText editText = new EditText(getContext());

                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.requestFocus();
                builder.setView(editText);
                // positive button for button press.
                builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(editText.getText().length() > 0)
                        {
                            memoryObject.title = editText.getText().toString();
                            memoryObject.texts = customTexts;
                            memoryObject.bitmaps = customBitmaps;
                            MainActivity.memoryObjects.add(0, memoryObject);
                            relativeLayout.removeAllViews();
                            MemoryListFragment memoryListFragment = new MemoryListFragment();
                            getFragmentManager().beginTransaction().replace(R.id.activity_main, memoryListFragment).commit();
                        }else
                        {
                            Toast.makeText(getContext(), "Enter a Memory Title!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });


        relativeLayout = (RelativeLayout)view.findViewById(R.id.canvas_layout);
        this.view = view;


        canvasOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.showContextMenu();
            }
        });

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
                startCameraForCapture();
                break;
            }

            case R.id.menu_uploadPhoto:
            {
                openGalleryForCrop();
                break;
            }

            case R.id.menu_text:
            {
                CustomText customText = new CustomText(getContext(), null, true);



                relativeLayout.addView(customText,relativeLayout.getChildCount());
                customText.bringToFront();
                customText.invalidate();
                relativeLayout.requestLayout();

                for(int i = 0;i  <relativeLayout.getChildCount(); i++)
                {
                    Log.d("Test", relativeLayout.getChildAt(i).toString() + "");
                }

                customTexts.add(customText);
                break;
            }

        }

        return super.onContextItemSelected(item);
    }

    private void startCameraForCapture()
    {
        try {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photo = createImageFile();

            if(photo != null)
            {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "vaystudios.com.memory", photo);
                i.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(i, MainActivity.REQUEST_IMAGE_CAPTURE);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void openGalleryForCrop()
    {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, MainActivity.REQUET_GALLERY_IMAGE_CROP);
    }

    private void selectImageFromGallery(Intent data)
    {
        Uri uri = data.getData();
        String[] path = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(uri, path, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(path[0]));



        cropImage(Uri.fromFile( new File(imagePath)));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MainActivity.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            cropImage(Uri.fromFile(new File(path)));
        }

        if(requestCode == MainActivity.REQUET_IMAGE_CROP && resultCode == RESULT_OK)
        {

            if(getActivity().checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED)
            {
                if(getActivity().shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Permission Needed to Read the Storage", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MainActivity.REQUET_IMAGE_CROP);
            }


            if(getActivity().checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED)
            {
                Uri newUri = data.getData();

                try {
                    Bitmap b = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), newUri);

                    Bitmap bitmap = scaleBitmapToScreenSize(RotateBitmap(b, 90));

                    if(bitmap != null)
                    {
                        CustomBitmap customBitmap = new CustomBitmap(getActivity(), null, bitmap, view, true);
                        customBitmaps.add(customBitmap);
                        relativeLayout.addView(customBitmap, relativeLayout.getChildCount());
                        customBitmap.bringToFront();
                        customBitmap.invalidate();
                        relativeLayout.requestLayout();


                        for(int i = 0;i  <relativeLayout.getChildCount(); i++)
                        {
                            Log.d("Test", relativeLayout.getChildAt(i).toString() + "");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }



        if(requestCode == MainActivity.REQUET_GALLERY_IMAGE_CROP && resultCode == RESULT_OK)
        {
            selectImageFromGallery(data);
        }

    }

    private void cropImage( Uri uri)
    {
        Intent i = new Intent("com.android.camera.action.CROP");
        i.setDataAndType(uri, "image/*");
        i.putExtra("crop", "true");
        i.putExtra("aspectX", 1);
        i.putExtra("aspectY", 1);
        i.putExtra("scale", true);
        i.putExtra("outputX", 500);
        i.putExtra("outputY", 500);



        startActivityForResult(i, MainActivity.REQUET_IMAGE_CROP);
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
            result = Bitmap.createScaledBitmap(source, (int)(source.getWidth()), (int)(source.getHeight() ), true);
        }catch(Exception e)
        {

        }

        return result;
    }

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
