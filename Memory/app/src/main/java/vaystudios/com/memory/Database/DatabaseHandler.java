package vaystudios.com.memory.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Devan on 2/14/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "memory.db";
    public  static final String TABLE_MEMORY = "memory";
    public  static final String COLUMN_ID = "memory";



    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
