package br.com.app.findyourself.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.app.findyourself.R;
import br.com.app.findyourself.firebase.bean.User;
import br.com.app.findyourself.utils.AppUtils;

import static br.com.app.findyourself.interfaces.AppSettings.LG_METHOD_FIND_YOURSELF;
import static br.com.app.findyourself.interfaces.AppSettings.NM_OBJ_REGISTER_LOGIN;

public class LoginRegisterActivity extends CommonActivity {

    private static String TAG = "LoginRegisterActivity";

    private FirebaseAuth mAuth;
    private EditText mEdiTextName;

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
        setContentView(R.layout.activity_login_register);

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

        mAuth = FirebaseAuth.getInstance();
        mEdiTextName = (EditText) findViewById(R.id.layout_register_NAME);
        mEmail = (AutoCompleteTextView) findViewById(R.id.layout_register_EMAIL);
        mPassword = (EditText) findViewById(R.id.layout_register_PASSWORD);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_login_register_form_progressbar);
        mLayoutView = findViewById(R.id.activity_login_register_form_data);
        mContainer = findViewById(R.id.activity_login_register_container);

        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                btnUserRegister(v);
                return false;
            }
        });

    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: initUser()
     * Date: 06/18/2016
     * About: This function comes from the super class and have to overwrite.
     * it helps initialize an user sometime can be on app or
     * on FireBase like below.
     * Parameters: null
     * return: void
     *************************************************************************/
    @Override
    protected void initUser() {

        if (AppUtils.isNetworkConnected(getBaseContext())){

            showProgress(true);
            final User user = new User();

            user.setName(mEdiTextName.getText().toString().trim());
            user.setEmail(mEmail.getText().toString());
            user.setPassword(mPassword.getText().toString());
            user.encriptyPassword();

            mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            showProgress(false);
                            Log.i(TAG, "registerUser:onComplete: " + task.isSuccessful());
                            if (task.isSuccessful()) {

                                user.setUid(task.getResult().getUser().getUid());
                                user.setMethod(LG_METHOD_FIND_YOURSELF);
                                user.saveUser(getBaseContext());

                                onAuthSuccess(user);
                            } else {
                                showSnackbar(task.getException().getMessage());
                            }
                        }
                    });

        }else
            AppUtils.internetActionSnackBar(this, mContainer, Snackbar.LENGTH_LONG);

    }

    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: createTransition()
     * Date: 06/18/2016
     * About: This function comes from the super class and have to overwrite.
     * it helps to create  transiction for the classes
     * Parameters: null
     * return: void
     *************************************************************************/
    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createTransition() {
    }



    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: btnUserRegister()
     * Date: 06/18/2016
     * About: This function helps valid the fields of register and then send
     * the user object to the FireBase to save.
     * Parameters: View view
     * return: void
     *************************************************************************/
    public void btnUserRegister(View view) {

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(mEdiTextName.getText().toString())) {
            mEdiTextName.setError(getString(R.string.error_field_required));
            focusView = mEdiTextName;
            cancel = true;
        }

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

            initUser();
        }
    }


    /**********************************************************************
     * Author: Victor Bitencourt
     * Method: onAuthSuccess()
     * Date: 06/18/2016
     * About: This function goes to login activity and pass the new user's data
     * by intent. just in case of the user's register was successful.
     * Parameters: Object User
     * return: void
     *************************************************************************/
    private void onAuthSuccess(User user) {

        Intent it = new Intent();
        it.putExtra(NM_OBJ_REGISTER_LOGIN, user.getEmail());
        mAuth.signOut();
        setResult(Activity.RESULT_OK, it);
        finish();
    }


}
