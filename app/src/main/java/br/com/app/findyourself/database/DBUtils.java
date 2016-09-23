package br.com.app.findyourself.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.app.findyourself.firebase.bean.User;

import static br.com.app.findyourself.interfaces.DataBase.FIELD_UID;
import static br.com.app.findyourself.interfaces.DataBase.FIELD_USER_APP_INTRO;
import static br.com.app.findyourself.interfaces.DataBase.FIELD_USER_APP_QUIZ;
import static br.com.app.findyourself.interfaces.DataBase.FIELD_USER_DT_EVALUATION;
import static br.com.app.findyourself.interfaces.DataBase.FIELD_USER_EMAIL;
import static br.com.app.findyourself.interfaces.DataBase.FIELD_USER_EVALUATION;
import static br.com.app.findyourself.interfaces.DataBase.FIELD_USER_METHOD;
import static br.com.app.findyourself.interfaces.DataBase.FIELD_USER_NAME;
import static br.com.app.findyourself.interfaces.DataBase.FIELD_USER_URI_PHOTO;
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
public class DBUtils {

    private static String TAG = "DBUtils";

    private static DBUtils instance;
    private SQLiteDatabase db;
    private Context context;


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: DBUtils()
     * Date: 06/18/2016
     * About: The constructor of class to create an instance of database.
     * Parameters: Context context
     * return: void
     ************************************************************************/
    protected DBUtils(Context context) {

        this.context = context;
        DBCore core = new DBCore(context);
        db = core.getWritableDatabase();

    }

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: getInstance()
     * Date: 06/18/2016
     * About: This function works with Singleton to get only one instance of
     * this calss.
     * Parameters: null
     * return: Objetc - DBUtils
     ************************************************************************/
    public static DBUtils getInstance(Context context) {

        if (instance == null)
            instance = new DBUtils(context);

        return instance;
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: insertUser()
     * Date: 06/18/2016
     * About: This function inserts a new user into the SQLITE database.
     * Parameters: Object - User
     * return: void
     *************************************************************************/
    private void insertUser(User user) throws Exception {

        ContentValues values = getContentValuesFromUser(user);
        db.insert(NM_TABLE_USER, null, values);

    }

    public boolean deleteUser(String uid) throws Exception {

        try {

            db.delete(NM_TABLE_USER, FIELD_UID + " = ?", new String[]{uid});
            db.delete(NM_TABLE_USER_PREFERENCE, FIELD_UID + " = ?", new String[]{uid});
            db.delete(NM_TABLE_USER_FAVORITE, FIELD_UID + " = ?", new String[]{uid});
            db.delete(NM_TABLE_USER_SEE_LATER, FIELD_UID + " = ?", new String[]{uid});
            db.delete(NM_TABLE_USER_PLACE_PREFERENCE, FIELD_UID + " = ?", new String[]{uid});
            db.delete(NM_TABLE_USER_FOOD_PREFERENCE, FIELD_UID + " = ?", new String[]{uid});
            db.delete(NM_TABLE_USER_MUSIC_PREFERENCE, FIELD_UID + " = ?", new String[]{uid});

            return true;

        } catch (Exception e) {
            e.getStackTrace();
            Log.e(TAG, "deleteUser: " + e.getMessage());
            return false;
        }


    }

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: getContentValuesFromUser()
     * Date: 06/18/2016
     * About: This function helps to build an specific object to save in the database.
     * <p/>
     * Parameters: Object - User
     * return: ContentValues - values
     *************************************************************************/
    private ContentValues getContentValuesFromUser(User user) {

        ContentValues values = new ContentValues();

        values.put(FIELD_UID, user.getUid());
        values.put(FIELD_USER_NAME, user.getName());
        values.put(FIELD_USER_EMAIL, user.getEmail());
        values.put(FIELD_USER_METHOD, user.getMethod());
        values.put(FIELD_USER_URI_PHOTO, user.getUriPhoto());
        values.put(FIELD_USER_APP_INTRO, user.isAppIntro() ? 1 : 0);
        values.put(FIELD_USER_APP_QUIZ, user.isAppQuiz() ? 1 : 0);
        values.put(FIELD_USER_EVALUATION, user.getEvaluation() ? 1 : 0);
        values.put(FIELD_USER_DT_EVALUATION, String.valueOf(user.getDtEvaluation()));

        return values;

    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: updateUser()
     * Date: 06/18/2016
     * About: This function helps to update an user's data in case of exists,
     * however if user doesn't exist it will insert into the database,
     * Parameters: Object - User
     * return: boolean - successful
     *************************************************************************/
    public boolean updateUser(User user) {

        try {

            if (userExists(user.getUid())) {

                ContentValues values = getContentValuesFromUser(user);
                db.update(NM_TABLE_USER, values, FIELD_UID + " = ?", new String[]{user.getUid()});

            } else {

                insertUser(user);
            }

            return true;

        } catch (Exception e) {
            e.getStackTrace();
            Log.e(TAG, "updateUser: " + e.getMessage());
            return false;
        }


    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: userExists()
     * Date: 06/18/2016
     * About: This function check in the database if the given user's uid already exists into
     * the database SQLITE.
     * Parameters: String - uid
     * return: boolean - exist
     *************************************************************************/
    public boolean userExists(String uid) {

        Cursor dataset = db.query(NM_TABLE_USER, new String[]{FIELD_UID}, FIELD_UID + " = ?", new String[]{uid}, null, null, null);
        return dataset.moveToFirst();
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: loadConnectedUser()
     * Date: 06/18/2016
     * About: This function load the connected user's data to the app.
     * the database SQLITE.
     * Parameters: String - uid
     * return: boolean - load successful
     *************************************************************************/
    public boolean loadConnectedUser(String uid) {

        try {
            Cursor dataset = db.query(NM_TABLE_USER, null, FIELD_UID + " = ?", new String[]{uid}, null, null, null);

            if (dataset.moveToFirst()) {

                User.getInstance().setUid(dataset.getString(dataset.getColumnIndex(FIELD_UID)));
                User.getInstance().setName(dataset.getString(dataset.getColumnIndex(FIELD_USER_NAME)));
                User.getInstance().setEmail(dataset.getString(dataset.getColumnIndex(FIELD_USER_EMAIL)));
                User.getInstance().setMethod(dataset.getInt(dataset.getColumnIndex(FIELD_USER_METHOD)));
                User.getInstance().setUriPhoto(dataset.getString(dataset.getColumnIndex(FIELD_USER_URI_PHOTO)));
                User.getInstance().setAppIntro(dataset.getInt(dataset.getColumnIndex(FIELD_USER_APP_INTRO)) == 1 ? true : false);
                User.getInstance().setAppQuiz(dataset.getInt(dataset.getColumnIndex(FIELD_USER_APP_QUIZ)) == 1 ? true : false);
                User.getInstance().setEvaluation(dataset.getInt(dataset.getColumnIndex(FIELD_USER_EVALUATION)) == 1 ? true : false);
                User.getInstance().setDtEvaluation(Long.parseLong(dataset.getString(dataset.getColumnIndex(FIELD_USER_DT_EVALUATION))));

                return true;
            } else
                return false;
        } catch (Exception e) {

            e.getStackTrace();
            return false;

        }
    }


}

