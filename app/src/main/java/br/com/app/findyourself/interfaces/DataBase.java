package br.com.app.findyourself.interfaces;

/**
 * Created by Victor on 6/18/2016.
 */
public interface DataBase {


    // Table names
    String NM_TABLE_USER = "USER";
    String NM_TABLE_USER_PREFERENCE = "USER_PREFERENCE";
    String NM_TABLE_USER_FAVORITE = "USER_FAVORITE";
    String NM_TABLE_USER_SEE_LATER = "USER_SEE_LATER";
    String NM_TABLE_USER_PLACE_PREFERENCE = "PLACE_PREFERENCE";
    String NM_TABLE_USER_FOOD_PREFERENCE = "FOOD_PREFERENCE";
    String NM_TABLE_USER_MUSIC_PREFERENCE = "MUSIC_PREFERENCE";

    // To helps on actions that are not predicted
    String BEFORE_DROP_DATABASE = "";
    String AFTER_DROP_DATABASE = "";
    String CREATING_DATABASE = "";


    String DB_NAME = "FIND_YOURSELF";

    int DB_VERSION = 1;


    // Fields
    String FIELD_UID = "UID";
    String FIELD_PLACE = "PLACE";
    String FIELD_TYPE_PLACE = "TYPE_PLACE";
    String FIELD_TYPE_MUSIC = "TYPE_MUSIC";
    String FIELD_TYPE_FOOD = "TYPE_FOOD";

    String FIELD_USER_NAME = "NAME";
    String FIELD_USER_EMAIL = "EMAIL";
    String FIELD_USER_METHOD = "METHOD";
    String FIELD_USER_URI_PHOTO = "URI_PHOTO";
    String FIELD_USER_APP_INTRO = "APP_INTRO";
    String FIELD_USER_APP_QUIZ = "APP_QUIZ";
    String FIELD_USER_EVALUATION = "EVALUATION";
    String FIELD_USER_DT_EVALUATION = "DT_EVALUATION";

    String FIELD_USER_PREFERENCE_PRICE = "PRICE_PREFERENCE";

    String FIELD_USER_PREFERENCE_INTIME = "INTIME";
    String FIELD_USER_PREFERENCE_OPENNOW = "OPENNOW";
    String FIELD_USER_PREFERENCE_MINSCORE = "MINSCORE";
    String FIELD_USER_PREFERENCE_RADIUS = "RADIUS";



    String DB_CREATE_USER =
            " CREATE TABLE "+NM_TABLE_USER+"( " +
                    FIELD_UID+" TEXT NOT NULL," +
                    FIELD_USER_NAME+" TEXT,"+
                    FIELD_USER_EMAIL+" TEXT,"+
                    FIELD_USER_METHOD+" INTEGER,"+
                    FIELD_USER_URI_PHOTO+" TEXT," +
                    FIELD_USER_APP_INTRO+" INTEGER," +
                    FIELD_USER_APP_QUIZ+" INTEGER," +
                    FIELD_USER_EVALUATION+" INTEGER," +
                    FIELD_USER_DT_EVALUATION+" TEXT);";

    String DB_CREATE_USER_PREFERENCE =
            " CREATE TABLE "+NM_TABLE_USER_PREFERENCE+"(" +
                    FIELD_UID+" TEXT NOT NULL," +
                    FIELD_USER_PREFERENCE_PRICE+" INTEGER," +
                    FIELD_USER_PREFERENCE_INTIME+" INTEGER," +
                    FIELD_USER_PREFERENCE_MINSCORE+" INTEGER,"+
                    FIELD_USER_PREFERENCE_OPENNOW+" INTEGER," +
                    FIELD_USER_PREFERENCE_RADIUS+" INTEGER);";

    String DB_CREATE_USER_FAVORITE =
            " CREATE TABLE IF NOT EXISTS "+NM_TABLE_USER_FAVORITE+"(" +
                    FIELD_UID+" TEXT NOT NULL," +
                    FIELD_PLACE+" TEXT);";

    String DB_CREATE_USER_SEE_LATER =
            " CREATE TABLE IF NOT EXISTS "+NM_TABLE_USER_SEE_LATER+"(" +
                    FIELD_UID+" TEXT NOT NULL," +
                    FIELD_PLACE+" TEXT);";

    String DB_CREATE_PLACE_PREFERENCE =
            " CREATE TABLE IF NOT EXISTS "+NM_TABLE_USER_PLACE_PREFERENCE+"(" +
                    FIELD_UID+" TEXT NOT NULL," +
                    FIELD_TYPE_PLACE+" TEXT);" ;

    String DB_CREATE_FOOD_PREFERENCE =
            " CREATE TABLE IF NOT EXISTS "+NM_TABLE_USER_FOOD_PREFERENCE+"(" +
                    FIELD_UID+" TEXT NOT NULL," +
                    FIELD_TYPE_FOOD+" TEXT);" ;

    String DB_CREATE_MUSIC_PREFERENCE =
            " CREATE TABLE IF NOT EXISTS "+NM_TABLE_USER_MUSIC_PREFERENCE+"(" +
                    FIELD_UID+" TEXT NOT NULL," +
                    FIELD_TYPE_MUSIC+" TEXT);";





}
