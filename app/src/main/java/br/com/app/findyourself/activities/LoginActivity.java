package br.com.app.findyourself.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import br.com.app.findyourself.R;
import br.com.app.findyourself.database.DBUtils;
import br.com.app.findyourself.firebase.bean.User;
import br.com.app.findyourself.utils.AppUtils;

import static br.com.app.findyourself.interfaces.AppSettings.LG_METHOD_FACEBOOK;
import static br.com.app.findyourself.interfaces.AppSettings.LG_METHOD_FIND_YOURSELF;
import static br.com.app.findyourself.interfaces.AppSettings.LOGIN_REGISTER;
import static br.com.app.findyourself.interfaces.AppSettings.NM_OBJ_REGISTER_LOGIN;
import static br.com.app.findyourself.interfaces.AppSettings.PLAY_SERVICES_RESOLUTION_REQUEST;
import static br.com.app.findyourself.interfaces.FireBase.FB_NODO_USER;

public class LoginActivity extends CommonActivity {

    private static String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;

    private int mMethod;

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: DBCore()
     * Date: 06/18/2016
     * About: This function is the constructor of the class, it starts the creation of
     * layout and components of screen
     * Parameters: Bundle savedInstanceState
     * return: void
     *************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        beforeViews();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

    }

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: beforeViews()
     * Date: 06/19/2016
     * About: This function comes from the super class and have to overwrite.
     * it helps to configure things before of the creation view
     * Parameters: null
     * return: void
     *************************************************************************/
    @Override
    protected void beforeViews() {
        initServices();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        createTransition();
    }

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: initViews()
     * Date: 06/18/2016
     * About: This function comes from the super class and have to overwrite.
     * it helps initialize the components/fields that will be used in the class
     * Parameters: null
     * return: void
     *************************************************************************/
    @Override
    protected void initViews() {

        mEmail = (AutoCompleteTextView) findViewById(R.id.activity_login_tv_EMAIL);
        mPassword = (EditText) findViewById(R.id.activity_login_tv_PASSWORD);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_login_form_progressbar);
        mLayoutView = findViewById(R.id.activity_login_form_data);
        mContainer = findViewById(R.id.activity_login_container);

        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                btnSingInWithFindYourself(v);
                return true;
            }
        });


        mAuth = FirebaseAuth.getInstance();

        if (FacebookSdk.isInitialized()) {

            mCallbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(mCallbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            handleFacebookAccessToken(loginResult.getAccessToken());
                            Log.d(TAG, "FacebookCallback:onSuccess: " + loginResult.getAccessToken());
                        }

                        @Override
                        public void onCancel() {
                            showProgress(false);
                            Log.d(TAG, "FacebookCallback:onCancel");
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            showProgress(false);
                            Log.d(TAG, "FacebookCallback:onError: " + exception.getMessage());
                            showSnackbar(exception.getMessage());
                        }
                    });

        }else{
            Log.e(TAG, "initViews:FacebookSdk.isInitialized:"+ FacebookSdk.isInitialized());
        }
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: initUser()
     * Date: 06/18/2016
     * About: This function comes from the super class and have to overwrite.
     * it helps initialize an user sometime can be on app or
     * on FireBase.
     * Parameters: null
     * return: void
     *************************************************************************/
    @Override
    protected void initUser() {

        showProgress(true);
        mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), AppUtils.cryptWithMD5(mPassword.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            loadUserInformation(task.getResult().getUser().getUid());
                        } else {
                            showProgress(false);
                            showSnackbar(task.getException().getMessage());
                        }
                    }
                });

    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: createTransition()
     * Date: 06/18/2016
     * About: This function comes from the super class and have to overwrite.
     * it helps to create transition for the classes
     * Parameters: null
     * return: void
     *************************************************************************/
    @Override
    protected void createTransition() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            TransitionInflater inflaterEnter = TransitionInflater.from(this);
            Transition transitionEnter = inflaterEnter.inflateTransition(R.transition.transitions);

            getWindow().setSharedElementEnterTransition(transitionEnter);
        }
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: loginProcess()
     * Date: 06/18/2016
     * About: This function decides which method need to call to start the login
     * process
     * Parameters: int method
     * return: void
     *************************************************************************/
    private void loginProcess(int method) {

        mMethod = method;
        if (AppUtils.isNetworkConnected(getBaseContext())) {

            if (AppUtils.checkPlayServices(this, PLAY_SERVICES_RESOLUTION_REQUEST)) {

                switch (method) {

                    case LG_METHOD_FIND_YOURSELF:
                        initUser();
                        break;

                    case LG_METHOD_FACEBOOK:
                        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile", "user_friends"));
                        break;
                }
            }

        } else
            AppUtils.internetActionSnackBar(this, mContainer, Snackbar.LENGTH_LONG);
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: btnSingInWithFindYourself()
     * Date: 06/18/2016
     * About: This function is used as a listener for the button on layout screen and
     * provides the sing in by Find Yourself method
     * Parameters: View view
     * return: void
     **************************************************************************/
    public void btnSingInWithFindYourself(View view) {

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail.getText().toString())) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!AppUtils.isEmailValid(mEmail.getText().toString())) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            mPassword.setError(getString(R.string.error_field_required));
            focusView = mPassword;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            loginProcess(LG_METHOD_FIND_YOURSELF);
        }

    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: btnSingInWithFacebook()
     * Date: 06/18/2016
     * About: This function is used as a listener for the button on layout screen and
     * provides the sing in by Facebook method
     * Parameters: View view
     * return: void
     **************************************************************************/
    public void btnSingInWithFacebook(View view) {

        loginProcess(LG_METHOD_FACEBOOK);
    }

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: handleFacebookAccessToken()
     * Date: 06/18/2016
     * About: This function helps to connect into the app using facebook access
     * token and create the user in the FireBase
     * Parameters: AccessToken token
     * return: void
     *************************************************************************/
    private void handleFacebookAccessToken(final AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        if (token != null) {
            AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (task.isSuccessful()) {

                                User user = new User();

                                user.setUid(task.getResult().getUser().getUid());
                                user.setName(task.getResult().getUser().getDisplayName());
                                user.setEmail(task.getResult().getUser().getEmail());
                                user.setMethod(LG_METHOD_FACEBOOK);

                                Profile profile = new Profile(token.getUserId(), null, null, null, null, null);
                                user.setUriPhoto(profile.getProfilePictureUri(150, 150).toString());

                                user.saveUser(getBaseContext());

                                loadUserInformation(task.getResult().getUser().getUid());

                            } else {
                                Log.w(TAG, "signInWithCredential", task.getException());
                                showProgress(false);
                                showSnackbar(task.getException().toString());

                            }

                        }
                    });
        } else {
            mAuth.signOut();
            showProgress(false);

        }
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: btnCreateRegister()
     * Date: 06/18/2016
     * About: This function open the activity to create a new user
     * Parameters: View view
     * return: void
     *************************************************************************/
    public void btnCreateRegister(View view) {
        startActivityForResult(new Intent(LoginActivity.this, LoginRegisterActivity.class), LOGIN_REGISTER);
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: accessMainActivity()
     * Date: 06/18/2016
     * About: This function provides the access to the Main activity
     * Parameters: null
     * return: void
     *************************************************************************/
    private void accessMainActivity() {

        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();


    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: loadUserInformation()
     * Date: 06/18/2016
     * About: This function helps to load the user's information from Firebase
     * when it doesn's exist in the SQLITE database.
     * Parameters: String userToken
     * return: void
     *************************************************************************/
    private void loadUserInformation(final String uid) {

        showProgress(true);
        if (!DBUtils.getInstance(getBaseContext()).loadConnectedUser(uid)) {

            FirebaseDatabase.getInstance().getReference().child(FB_NODO_USER).child(uid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(TAG, "loadUserInfo:ValueEventListener:onDataChange");


                            User user = dataSnapshot.getValue(User.class);
                            if (user != null) {

                                User.getInstance().setUid(uid);
                                User.getInstance().setName(user.getName());
                                User.getInstance().setEmail(user.getEmail());
                                User.getInstance().setUriPhoto(user.getUriPhoto());
                                User.getInstance().setMethod(user.getMethod());
                                User.getInstance().setEvaluation(user.getEvaluation());

                                DBUtils.getInstance(getBaseContext()).updateUser(User.getInstance());
                                accessMainActivity();

                            } else {
                                mAuth.signOut();
                                showSnackbar(getResources().getString(R.string.warn_firebase_profile));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG, "loadUserInfo:ValueEventListener:onCancelled");
                            showProgress(false);
                        }

                    });
        } else {

            showProgress(false);
            accessMainActivity();
        }


    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: onActivityResult()
     * Date: 06/18/2016
     * About: This function helps to process the result of other activities opened
     * from this
     * Parameters: int requestCode
     * int resultCode
     * Intent data
     * return: void
     *************************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 64206:
                if (resultCode == RESULT_OK)
                    mCallbackManager.onActivityResult(requestCode, resultCode, data);
                else
                    showSnackbar("It is not possible to log in with facebook!");
                break;

            case LOGIN_REGISTER:
                if (resultCode == RESULT_OK) {

                    // get the extras
                    if (data.hasExtra(NM_OBJ_REGISTER_LOGIN)) {
                        String stringEmail = data.getStringExtra(NM_OBJ_REGISTER_LOGIN);
                        mEmail.setText("");
                        mPassword.setText("");
                        mPassword.requestFocus();
                        mEmail.setText(stringEmail);
                    }

                }
                break;

            case PLAY_SERVICES_RESOLUTION_REQUEST:

                loginProcess(mMethod);
                break;

        }

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
     * Method: onBackPressed()
     * Date: 06/18/2016
     * About: This function helps to get the event of back button when pressed
     * Parameters: null
     * return: void
     *************************************************************************/
    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View imgvLogo = findViewById(R.id.activity_login_imgv_LOGO);
            imgvLogo.setVisibility(View.INVISIBLE);
        }
        super.onBackPressed();
    }
}