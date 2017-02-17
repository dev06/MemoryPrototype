package vaystudios.com.memory.Object;

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
    public ArrayList<CustomBitmap> bitmaps;
    public ArrayList<CustomText> texts;

    public MemoryObject(int id, String title)
    {
        this.id = id;
        this.title = title;
    }



}
