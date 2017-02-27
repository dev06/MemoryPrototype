package vaystudios.com.memory;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vaystudios.com.memory.Fragment.CanvasViewFragment;
import vaystudios.com.memory.Fragment.MemoryListFragment;
import vaystudios.com.memory.Object.MemoryObject;
import vaystudios.com.memory.Object.User;
import vaystudios.com.memory.Util.Coder.JSONDecoder;
import vaystudios.com.memory.View.CustomBitmap;

public class MainActivity extends AppCompatActivity {

    public static int REQUEST_IMAGE_CAPTURE = 0;
    public static int REQUET_IMAGE_CROP = 1;
    public static int REQUET_GALLERY_IMAGE_CROP = 2;

    public static User loggedUser;
    public static ArrayList<MemoryObject> memoryObjects = new ArrayList<MemoryObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED)
        {
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                Toast.makeText(this, "Permission Needed to Read the Storage", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        CreateLoggedUser(getIntent());

        MemoryListFragment memoryListFragment = new MemoryListFragment();
        getFragmentManager().beginTransaction().add(R.id.activity_main,memoryListFragment).commit();

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


    private void CreateLoggedUser(Intent intent)
    {
        loggedUser = new User();
        loggedUser.setName(intent.getStringExtra("name"));
        loggedUser.setUser_id(intent.getStringExtra("user_id"));
        loggedUser.setUsername(intent.getStringExtra("username"));
  }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed() {

        CanvasViewFragment myFragment = (CanvasViewFragment)getFragmentManager().findFragmentByTag("CANVAS_VIEW_FRAGMENT");
        if (myFragment != null && myFragment.isVisible()) {
            super.onBackPressed();
        }
    }
}
