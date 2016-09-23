package br.com.app.findyourself.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.facebook.FacebookSdk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

import br.com.app.findyourself.R;
import br.com.app.findyourself.activities.MainActivity;

import static br.com.app.findyourself.interfaces.AppSettings.WIFI_SETTINGS_RESOLUTION_REQUEST;
/**
 * Created by Victor on 6/18/2016.
 */
public class AppUtils {


    private static String TAG = "AppUtils";

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: cryptWithMD5()
     * Date: 06/18/2016
     * About: This function makes the encryption of String given with MD5.
     * Parameters: String text
     * return: String encrypted
     *************************************************************************/
    public static String cryptWithMD5(String text) {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = text.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Log.e("AppUtils:cryptWithMD5? ", Level.SEVERE + " - " + ex);
        }
        return null;


    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: isEmailValid()
     * Date: 06/18/2016
     * About: This function check the given email to valid the structure of email
     * Parameters: String email
     * return: boolean - valid
     *************************************************************************/
    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: isNetworkConnected()
     * Date: 06/18/2016
     * About: This function check the connection with network
     * Parameters: Context context
     * return: boolean - exist
     *************************************************************************/
    public static boolean isNetworkConnected(Context context) {

        boolean result = false;

        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Network[] allNetworks = connectivityManager.getAllNetworks();

                for (Network netWork : allNetworks) {

                    if ((connectivityManager.getNetworkInfo(netWork).getType() == ConnectivityManager.TYPE_MOBILE) && connectivityManager.getNetworkInfo(netWork).isConnected()) {
                        result = true;
                        break;
                    }

                    if ((connectivityManager.getNetworkInfo(netWork).getType() == ConnectivityManager.TYPE_WIFI) && connectivityManager.getNetworkInfo(netWork).isConnected()) {
                        result = true;
                        break;
                    }
                    Log.d(TAG, "isNetworkConnected:" + connectivityManager.getNetworkInfo(netWork).getTypeName() + ": " + connectivityManager.getNetworkInfo(netWork).isConnected());
                }


            } else {

                NetworkInfo[] allNetworks = connectivityManager.getAllNetworkInfo();

                for (NetworkInfo networkInfo : allNetworks) {

                    if ((networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) && networkInfo.isConnected()) {
                        result = true;
                        break;
                    }

                    if ((networkInfo.getType() == ConnectivityManager.TYPE_WIFI) && networkInfo.isConnected()) {
                        result = true;
                        break;
                    }
                    Log.d(TAG, "isNetworkConnected:" + networkInfo.getTypeName() + ": " + networkInfo.isConnected());
                }

            }

        } catch (Exception e) {
            Log.e(TAG, "isNetworkConnected:" + e.getMessage());
            result = false;
        }

        return result;
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: checkPlayServices()
     * Date: 06/18/2016
     * About: This function verify google play services on the device
     * Parameters: Context context
     *             int requestCode
     * return: boolean - exist
     *************************************************************************/
    public static boolean checkPlayServices(Context context, int requestCode) {

        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(context);

        if (result != ConnectionResult.SUCCESS) {

            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog((AppCompatActivity) context, result, requestCode).show();
            }

            return false;
        }

        return true;
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: customActionSnackBar()
     * Date: 06/18/2016
     * About: This function helps to build an actionbar with customized action
     * Parameters: View view
     *             String text
     *             String action
     *             int duration
     *             View.OnClickListener onClickListener
     *
     * return: void
     *************************************************************************/
    public static void customActionSnackBar(View view, String text, String action,
                                            int duration, View.OnClickListener onClickListener) {

        Snackbar snackbar = Snackbar
                .make(view, text, duration)
                .setAction(action, onClickListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            snackbar.setActionTextColor(view.getResources().getColor(R.color.colorAccent, null));
        else
            snackbar.setActionTextColor(view.getResources().getColor(R.color.colorAccent));

        snackbar.show();

    }

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: internetActionSnackBar()
     * Date: 06/18/2016
     * About: This function helps to build an actionbar with customized action
     * Parameters: AppCompatActivity activity
     *             View view
     *             int duration
     * return: void
     *************************************************************************/
    public static void internetActionSnackBar(final AppCompatActivity activity, View view, int duration ) {

        customActionSnackBar(view, activity.getResources().getString(R.string.warn_network),
                activity.getResources().getString(R.string.action_connect), duration,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        activity.startActivityForResult(intent, WIFI_SETTINGS_RESOLUTION_REQUEST);
                    }
                });

    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: internetActionSnackBar()
     * Date: 06/22/2016
     * About: This function helps to start the services that the app will use.
     * Parameters: Context context
     * return: void
     *************************************************************************/
    public static void initAppServices(Context context){

            if (!FacebookSdk.isInitialized()) {
                FacebookSdk.sdkInitialize(context);
            }

    }

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: checkLocationService()
     * Date: 06/22/2016
     * About: This function helps to check if the location service is avaible.
     * Parameters: Context context
     * return: void
     *************************************************************************/
    public static boolean checkLocationService(Context context) {

        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }

    }

    public static boolean requestPermissions(Activity activity, String permission, String message, int requestCode){

        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionRationaleDialog(message, permission, activity, requestCode);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        }else {
            return true;
        }
        return false;

    }
    private static void showPermissionRationaleDialog(final String message, final String permission, final Activity activity, final int requestCode) {
        new AlertDialog.Builder( activity  )
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

}
