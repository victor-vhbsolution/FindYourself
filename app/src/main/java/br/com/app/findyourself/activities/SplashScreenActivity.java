package br.com.app.findyourself.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import br.com.app.findyourself.R;
import br.com.app.findyourself.database.DBUtils;
import br.com.app.findyourself.utils.AppUtils;

public class SplashScreenActivity extends AppCompatActivity implements Runnable {

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: onCreate()
     * Date: 06/19/2016
     * About: This function is the constructor of the the class that create the
     * views and starts services and etc..
     * Parameters: null
     * return: void
     *************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createTransiction();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initServices();
        verifyUserLogged();

    }

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: loadUserInformation()
     * Date: 06/19/2016
     * About: This function is used to check if exists some user already connected
     * on the app
     * Parameters: null
     * return: void
     *************************************************************************/
    private void verifyUserLogged() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            loadUserInformation(FirebaseAuth.getInstance().getCurrentUser().getUid());

        } else {
            settingSplashScreen();
        }

    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: loadUserInformation()
     * Date: 06/19/2016
     * About: This function is used to load the informations from the connected
     * user in case of has one, if not the method will call the login activity.
     * Parameters: String userToken
     * return: void
     *************************************************************************/
    private void loadUserInformation(String userToken) {

        if (DBUtils.getInstance(getBaseContext()).loadConnectedUser(userToken)) {
            accessNextActivity(MainActivity.class);
        } else {
            settingSplashScreen();
        }
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: accessNextActivity()
     * Date: 06/19/2016
     * About: This function is used to go to the next activity. have to pass the
     * class, which you want to go, as parameter.
     * Parameters: Class<?> clazz
     * return: void
     *************************************************************************/
    private void accessNextActivity(Class<?> clazz) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && clazz.getName().equals(LoginActivity.class.getName())) {

            View imgvLogo = findViewById(R.id.activity_splash_screen_imgv_SPLASHSCREEN);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, Pair.create(imgvLogo, imgvLogo.getTransitionName()));
            startActivity(new Intent(SplashScreenActivity.this, clazz), optionsCompat.toBundle());

        } else {
            startActivity(new Intent(SplashScreenActivity.this, clazz));
        }

        new Handler().postDelayed(this, 1000);
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: initServices()
     * Date: 06/19/2016
     * About: This function is used to starts any service tha you need on the app.
     * such as facebook sdk and etc..
     * Parameters: null
     * return: void
     *************************************************************************/
    public void initServices() {
        AppUtils.initAppServices(getApplicationContext());
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: settingSplashScreen()
     * Date: 06/19/2016
     * About: This function is called when you need to start the login activity, it makes
     * some configurations before call the login.
     * Parameters: null
     * return: void
     *************************************************************************/
    private void settingSplashScreen() {

        FirebaseAuth.getInstance().signOut();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                accessNextActivity(LoginActivity.class);
            }
        }, 2000);

    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: run()
     * Date: 06/19/2016
     * About: This function comes from the Runnable interface, it provides that
     * you can execute this method anytime if you create a Handle and call it inside.
     * Parameters: null
     * return: void
     *************************************************************************/
    @Override
    public void run() {
        finish();
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: createTransiction()
     * Date: 06/18/2016
     * About: This function helps to create transiction beetween activities
     * Parameters: null
     * return: void
     *************************************************************************/
    private void createTransiction() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.transitions);

            getWindow().setSharedElementExitTransition(transition);
        }

    }

}



