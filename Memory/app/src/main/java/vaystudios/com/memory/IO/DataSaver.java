package vaystudios.com.memory.IO;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import vaystudios.com.memory.Object.MemoryObject;

/**
 * Created by Devan on 2/17/2017.
 */

public class DataSaver {


    public static String FileName = "SaveData.ser";


    public void SaveData(Context context, MemoryObject object)
    {
        try
        {
            FileOutputStream fileOutputStream = context.openFileOutput(FileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static MemoryObject ReadData(Context context)
    {
        MemoryObject memoryObject = null;
        try
        {
            FileInputStream fileInputStream = context.openFileInput(FileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            memoryObject = (MemoryObject)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

        }catch(IOException e)
        {
            e.printStackTrace();
        }catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return  memoryObject;
    }
}
