package vaystudios.com.memory.Object;

import android.graphics.Bitmap;

import java.lang.reflect.Array;
import java.util.ArrayList;

import vaystudios.com.memory.View.CustomBitmap;
import vaystudios.com.memory.View.CustomText;

/**
 * Created by Devan on 2/4/2017.
 */

public class MemoryObject
{
    public int id;
    public String title;
    public Bitmap myBitmap;
    public ArrayList<CustomBitmap> bitmaps;
    public ArrayList<CustomText> texts;

    public MemoryObject()
    {

    }

    public MemoryObject(int id, String title)
    {
        this.id = id;
        this.title = title;
    }


    public MemoryObject(int id, String title, ArrayList<CustomBitmap> bitmaps)
    {
        this.id = id;
        this.title = title;
        this.bitmaps = bitmaps;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getMyBitmap() {
        return myBitmap;
    }

    public void setMyBitmap(Bitmap myBitmap) {
        this.myBitmap = myBitmap;
    }

    public ArrayList<CustomBitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(ArrayList<CustomBitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    public ArrayList<CustomText> getTexts() {
        return texts;
    }

    public void setTexts(ArrayList<CustomText> texts) {
        this.texts = texts;
    }




}
