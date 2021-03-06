package vaystudios.com.memory.Database;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import vaystudios.com.memory.Object.MemoryObject;
import vaystudios.com.memory.View.CustomBitmap;

/**
 * Created by Devan on 2/14/2017.
 */

public class DatabaseHandler
{
    private static final String DATABASE_NAME = "memory.db";
    private static final int DATA_VERSION = 2;

    public static final String MEMORY_TABLE = "memory";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BITMAP= "bitmap";


    private String[] allColumns = {COLUMN_ID, COLUMN_TITLE, COLUMN_BITMAP};
    private SQLiteDatabase sqlDB;
    private Context context;
    private MemoryDbHelper memoryDbHelper;

    public static final String CREATE_TABLE_MEMORY = "create table " + MEMORY_TABLE + " ( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_BITMAP + " BLOB "
            +
            ");";


    public DatabaseHandler(Context ctx) {
        this.context = ctx;

    }

    public DatabaseHandler open() throws android.database.SQLException {
        memoryDbHelper = new MemoryDbHelper(context);
        sqlDB = memoryDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        memoryDbHelper.close();
    }




    public void createMemory(MemoryObject memoryObject) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, memoryObject.id);
        values.put(COLUMN_TITLE, memoryObject.title);


        sqlDB.insert(MEMORY_TABLE, null, values);

    }


    public long upadateNote(long idToUpdate, String newTitle) {
        /*
         puts the content values to update.
         since the sqlDB.update method requires a "ContentValues" you use the content values to put the new values
         the content values and pass the content values to the update method.
        */

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newTitle);


        // looks in the note table to see if column id == idToUpdate and if so then update the values using "values"
        return sqlDB.update(MEMORY_TABLE, values, COLUMN_ID + " = " + idToUpdate, null);
    }

    public long deleteMemory(long id)
    {
         return sqlDB.delete(MEMORY_TABLE, COLUMN_ID + "=" + id ,null);
    }

    private MemoryObject cursorToNote(Cursor cursor) {
        MemoryObject newNote = new MemoryObject(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        newNote.myBitmap = BitmapFactory.decodeByteArray(cursor.getBlob(2), 0, cursor.getBlob(2).length);


        return newNote;
    }

    public ArrayList<MemoryObject> getAllMemory() {
        ArrayList<MemoryObject> memoryObject = new ArrayList<MemoryObject>();

        Cursor cursor = sqlDB.query(MEMORY_TABLE, allColumns, null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            MemoryObject object = cursorToNote(cursor);
            memoryObject.add(object);
        }

        cursor.close();

        return memoryObject;
    }






    private static class MemoryDbHelper extends SQLiteOpenHelper {
        public MemoryDbHelper(Context c) {
            super(c, DATABASE_NAME, null, DATA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            // create note table
            sqLiteDatabase.execSQL(CREATE_TABLE_MEMORY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.w(MemoryDbHelper.class.getName(), "Upgrade database from " + oldVersion + " to " + newVersion);

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MEMORY_TABLE);
            onCreate(sqLiteDatabase);
        }
    }


}




