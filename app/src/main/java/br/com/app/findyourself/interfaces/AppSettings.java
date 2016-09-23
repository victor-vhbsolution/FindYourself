package br.com.app.findyourself.interfaces;

/**
 * Created by Victor on 6/18/2016.
 */
public interface AppSettings {

    // Methods for Volley Resquest
    int JSON_ARRAY = 0;
    int JSON_OBJECT = 1;
    int STRING_REQUEST = 2;

    int PLAY_SERVICES_RESOLUTION_REQUEST = 1;
    int LOCATION_SERVICES_RESOLUTION_REQUEST = 2;
    int WIFI_SETTINGS_RESOLUTION_REQUEST = 3;
    int LOGIN_REGISTER = 4;

    //between activities
    String NM_OBJ_PLACEDETAILED = "_placeDetailed";
    String NM_OBJ_REGISTER_LOGIN = "_loginMethod";
    String LOGIN_METHOD = "_loginMethod";
    String NM_OBJ_CONNECTEDUSER = "_connectedUser";

    //Login's Methods
    int LG_NON_METHOD = -1;
    int LG_METHOD_FACEBOOK = 0;
    int LG_METHOD_FIND_YOURSELF = 1;
    int LG_METHOD_GOOGLE_PLUS = 2;

    String NM_OBJ_NETWORK = "_wrapObjToNetwork";
    String LATITUDE_NETWORK = "_latitude";
    String LONGITUDE_NETWORK = "_longitude";
    String QUERY_NETWORK = "_query";

    //Firebase nodos
    String FB_NODO_USER = "user";
    String FB_NODO_USER_PREFERENCE = "user_preference";

    String TOKEN = "AIzaSyAkbNbJ3ERzFO9oMyVwJj3FXEhOpyZKbPM";

    String URL_API_GOOGLE_PHOTO = "https://maps.googleapis.com/maps/api/place/photo?" ;
    String MAXWIDTH = "720";


    String API_URL = "http://findyourselfapp.azurewebsites.net/WS_Find_YourSelf/rest/json/";
    //String API_URL = "http://192.168.0.15:8080/WS_Find_YourSelf/rest/json/";
    //String API_URL = "http://172.16.71.132:8080/WS_Find_YourSelf/rest/json/";

    String METHOD_PLACEDETAILS = "placeWebService/getPlaceDetails";

    String METHOD_USER_FOOD_PREFERENCE = "placeWebService/getUserFoodPreference";
    String METHOD_USER_MUSIC_PREFERENCE = "placeWebService/getUserMusicPreference";
    String METHOD_USER_PLACE_PREFERENCE = "placeWebService/getUserPlacePreference";


    String METHOD_CITYPLACES = "placeWebService/getCityPlaces";
    String METHOD_PLACE_LIST_SEARCH_QUERY = "placeWebService/getPlacesByQuery";

    //String METHOD_CITYPLACES = "placeWebService/getUserPreferencePlace";
    String METHOD_PLACE_LIST_REFRESH = "placeWebService/placeListRefresh";

    // tags as log
    String TAG_REGISTER_USER = "_RegisterUserActivity";
    String TAG_LOGIN_USER = "_LogInUserActivity";
    String TAG_MAIN = "_MainActivity";

    // Duaration of the Trasitions between Activities;
    int MS_DURATION_TRANSITION = 1000;


    // Type Actions
    int NON_ACTION = -1;
    int PLACE_DETAILS = 0;
    int PLACE_LIST_REQUEST = 1;
    int PLACE_LIST_REFRESH = 2;
    int PLACE_LIST_REQUEST_USER_PREFERENCE = 3;
    int PLACE_LIST_SEARCH_QUERY = 4;

    String TAG_CONNECTION = "CONNECTION_STATUS" ;

}
