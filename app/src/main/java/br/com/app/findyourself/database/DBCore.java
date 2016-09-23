package br.com.app.findyourself.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static br.com.app.findyourself.interfaces.DataBase.AFTER_DROP_DATABASE;
import static br.com.app.findyourself.interfaces.DataBase.BEFORE_DROP_DATABASE;
import static br.com.app.findyourself.interfaces.DataBase.CREATING_DATABASE;
import static br.com.app.findyourself.interfaces.DataBase.DB_CREATE_FOOD_PREFERENCE;
import static br.com.app.findyourself.interfaces.DataBase.DB_CREATE_MUSIC_PREFERENCE;
import static br.com.app.findyourself.interfaces.DataBase.DB_CREATE_PLACE_PREFERENCE;
import static br.com.app.findyourself.interfaces.DataBase.DB_CREATE_USER;
import static br.com.app.findyourself.interfaces.DataBase.DB_CREATE_USER_FAVORITE;
import static br.com.app.findyourself.interfaces.DataBase.DB_CREATE_USER_PREFERENCE;
import static br.com.app.findyourself.interfaces.DataBase.DB_CREATE_USER_SEE_LATER;
import static br.com.app.findyourself.interfaces.DataBase.DB_NAME;
import static br.com.app.findyourself.interfaces.DataBase.DB_VERSION;
import static br.com.app.findyourself.interfaces.DataBase.NM_TABLE_USER;
import static br.com.app.findyourself.interfaces.DataBase.NM_TABLE_USER_FAVORITE;
import static br.com.app.findyourself.interfaces.DataBase.NM_TABLE_USER_FOOD_PREFERENCE;
import static br.com.app.findyourself.interfaces.DataBase.NM_TABLE_USER_MUSIC_PREFERENCE;
import static br.com.app.findyourself.interfaces.DataBase.NM_TABLE_USER_PLACE_PREFERENCE;
import static br.com.app.findyourself.interfaces.DataBase.NM_TABLE_USER_PREFERENCE;
import static br.com.app.findyourself.interfaces.DataBase.NM_TABLE_USER_SEE_LATER;

/**
 * Created by Victor on 6/18/2016.
 */
public class DBCore extends SQLiteOpenHelper {

    private static String TAG = "DBCore";


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: DBCore()
     * Date: 06/18/2016
     * About: This function is the constructor to check if exist the database or no,
     * if so check the database's version, in case of difference , it will execute the
     * method onUpgrade().
     * Parameters: Context context
     * return: void
     *************************************************************************/
    public DBCore(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: onCreate()
     * Date: 06/18/2016
     * About: This function creates the Database into SQLITE
     * Parameters: Context context
     * return: void
     *************************************************************************/
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DB_CREATE_USER);
        Log.i(TAG,"onCreate: "+DB_CREATE_USER);

        db.execSQL(DB_CREATE_USER_FAVORITE);
        Log.i(TAG,"onCreate: "+DB_CREATE_USER_FAVORITE);

        db.execSQL(DB_CREATE_USER_SEE_LATER);
        Log.i(TAG,"onCreate: "+DB_CREATE_USER_SEE_LATER);

        db.execSQL(DB_CREATE_USER_PREFERENCE);
        Log.i(TAG,"onCreate: "+DB_CREATE_USER_PREFERENCE);

        db.execSQL(DB_CREATE_MUSIC_PREFERENCE);
        Log.i(TAG,"onCreate: "+DB_CREATE_MUSIC_PREFERENCE);

        db.execSQL(DB_CREATE_PLACE_PREFERENCE);
        Log.i(TAG,"onCreate: "+DB_CREATE_PLACE_PREFERENCE);

        db.execSQL(DB_CREATE_FOOD_PREFERENCE);
        Log.i(TAG,"onCreate: "+DB_CREATE_FOOD_PREFERENCE);

        /* this code helps to execute some action on the creation of database, like
            create a new table, or fields. */
        if (!CREATING_DATABASE.isEmpty()) {
            db.execSQL(CREATING_DATABASE);
            Log.i(TAG,"onCreate:CREATING_DATABASE: " +CREATING_DATABASE);
        }

    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: onUpgrade()
     * Date: 06/18/2016
     * About: This function wiil upgrade the database when the structure has diferences.
     * <p/>
     * Parameters: SQLiteDatabase - db
     * int - oldVersion
     * int - newVersion
     * <p/>
     * return: void
     *************************************************************************/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {

            /* this code helps to execute some action before drop tables */
            if (!BEFORE_DROP_DATABASE.isEmpty()) {
                db.execSQL(BEFORE_DROP_DATABASE);
                Log.i(TAG,"onUpgrade:BEFORE_DROP_DATABASE: " + BEFORE_DROP_DATABASE);
            }

            db.execSQL("DROP TABLE IF EXISTS " + NM_TABLE_USER + ";");
            Log.i(TAG,"onUpgrade:DROP TABLE - " + NM_TABLE_USER + ";");

            db.execSQL("DROP TABLE IF EXISTS " + NM_TABLE_USER_PREFERENCE + ";");
            Log.i(TAG,"onUpgrade:DROP TABLE - " + NM_TABLE_USER_PREFERENCE + ";");

            db.execSQL("DROP TABLE IF EXISTS " + NM_TABLE_USER_FAVORITE + ";");
            Log.i(TAG,"onUpgrade:DROP TABLE - " + NM_TABLE_USER_FAVORITE + ";");

            db.execSQL("DROP TABLE IF EXISTS " + NM_TABLE_USER_SEE_LATER + ";");
            Log.i(TAG,"onUpgrade:DROP TABLE - " + NM_TABLE_USER_SEE_LATER + ";");

            db.execSQL("DROP TABLE IF EXISTS " + NM_TABLE_USER_PLACE_PREFERENCE + ";");
            Log.i(TAG,"onUpgrade:DROP TABLE - " + NM_TABLE_USER_PLACE_PREFERENCE + ";");

            db.execSQL("DROP TABLE IF EXISTS " + NM_TABLE_USER_FOOD_PREFERENCE + ";");
            Log.i(TAG,"onUpgrade:DROP TABLE - " + NM_TABLE_USER_FOOD_PREFERENCE + ";");

            db.execSQL("DROP TABLE IF EXISTS " + NM_TABLE_USER_MUSIC_PREFERENCE + ";");
            Log.i(TAG,"onUpgrade:DROP TABLE - " + NM_TABLE_USER_MUSIC_PREFERENCE + ";");

            onCreate(db);

            /* this code helps to execute some action after drop tables and create a new database */
            if (!AFTER_DROP_DATABASE.isEmpty()) {
                db.execSQL(AFTER_DROP_DATABASE);
                Log.i(TAG, "onUpgrade:AFTER_DROP_DATABASE: " + AFTER_DROP_DATABASE);
            }

        }


    }
}

