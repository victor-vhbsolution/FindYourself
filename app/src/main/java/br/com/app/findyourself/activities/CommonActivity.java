package br.com.app.findyourself.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by Victor on 6/18/2016.
 */
public abstract class CommonActivity extends AppCompatActivity {

    protected AutoCompleteTextView mEmail;
    protected EditText mPassword;
    protected ProgressBar mProgressBar;
    protected View mLayoutView;
    protected View mContainer;


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: showSnackbar()
     * Date: 06/17/2016
     * About: This function helps to make esier to use a Snackbar object in the
     * child classes (activity)
     * Parameters: boolean show
     * return: void
     *************************************************************************/
    protected void showSnackbar(String message) {
        Snackbar.make(mContainer,
                message,
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: showToast()
     * Date: 06/17/2016
     * About: This function helps to make esier to use a Toast object in the
     * child classes (activity)
     * Parameters: boolean show
     * return: void
     *************************************************************************/
    protected void showToast(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG)
                .show();
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: showProgress()
     * Date: 06/17/2016
     * About: This function helps to control the behavior of progress bar and
     * layout of main view given. it makes esier to use the progress bar in the
     * child classes
     * Parameters: boolean show
     * return: void
     *************************************************************************/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLayoutView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            mLayoutView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLayoutView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
                }
            });

            mProgressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            mProgressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            mLayoutView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        }
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: beforeViews()
     * Date: 06/17/2016
     * About: This function have to be overwrite in the child classes.
     * it helps to configure things before of the creation of the view
     * Parameters: null
     * return: void
     *************************************************************************/
    abstract protected void beforeViews();


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: initViews()
     * Date: 06/17/2016
     * About: This function have to be overwrite in the child classes.
     * it helps initialize the components/fields that will be used in the class
     * Parameters: null
     * return: void
     *************************************************************************/
    abstract protected void initViews();

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: initUser()
     * Date: 06/17/2016
     * About: This function have to be overwrite in the child classes.
     * it helps initialize an user  or process about user
     * Parameters: null
     * return: void
     *************************************************************************/
    abstract protected void initUser();

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: createTransiction()
     * Date: 06/17/2016
     * About: This function helps to create transiction beetween activities, it
     * needs to be overwrite in the child classes
     * Parameters: null
     * return: void
     *************************************************************************/
    abstract protected void createTransition();


}
