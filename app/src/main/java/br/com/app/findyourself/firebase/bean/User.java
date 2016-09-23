package br.com.app.findyourself.firebase.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import br.com.app.findyourself.utils.AppUtils;
import br.com.app.findyourself.database.DBUtils;

import static br.com.app.findyourself.interfaces.FireBase.FB_NODO_USER;

/**
 * Created by Victor on 6/18/2016.
 */

public class User implements Serializable {

    public static final long serialVersionUID = 100L;

    private static User instance;

    private int method;

    private String uid;
    private String name;
    private String email;
    private String password;
    private String newPassword;
    private String uriPhoto;
    private String profilePhoto;

    private long dtEvaluation = 0;

    private boolean evaluation = false;
    private boolean appIntro = false;
    private boolean appQuiz = false;



    /**********************************************************************
     Author: Victor Bitencourt
     Method: User()
     Date: 06/18/2016
     About: The default constructor of class to integrate with FireBase.
     Parameters: null
     return: void
    ************************************************************************/
    public User(){}

    /**********************************************************************
     Author: Victor Bitencourt
     Method: destroyInstance()
     Date: 07/2/2016
     About: This function destroy the reference of object in the memory
     Parameters: null
     return: void
     ************************************************************************/
    public void destroyInstance(){
        instance = null;
    }
    /**********************************************************************
     Author: Victor Bitencourt
     Method: getInstance()
     Date: 06/18/2016
     About: This function works with Singleton to get only one instance of
     this calss.
     Parameters: null
     return: Objetc - User
    ************************************************************************/
    @Exclude
    public static User getInstance() {

        if (instance == null)
            instance = new User();

        return instance;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: getEmail()
     Date: 06/18/2016
     About: to get the user's email.
     return: String - email
    ************************************************************************/
    public String getEmail() {
        return email;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: setEmail()
     Date: 06/18/2016
     About: to set the user's email.
     Parameters: String email
     return: void
    *************************************************************************/
    public void setEmail(String email) {
        this.email = email;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: getUid()
     Date: 06/18/2016
     About: to get the user's uid.
     return: String - uid
    *************************************************************************/
    @Exclude
    public String getUid() {
        return uid;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: setUid()
     Date: 06/18/2016
     About: to set the user's uid.
     Parameters: String uid
     return: void
    *************************************************************************/
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: getPassword()
     Date: 06/18/2016
     About: to get the user's password.
     Parameters: null
     return: String - password
     *************************************************************************/
    @Exclude
    public String getPassword() {
        return password;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: setPassword()
     Date: 06/18/2016
     About: to set the user's password.
     Parameters: String password
     return: void
     *************************************************************************/
    public void setPassword(String password) {
        this.password = password;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: encriptyPassword()
     Date: 06/18/2016
     About: This function makes the encryption of password to send to the FireBase.
     Parameters: null
     return: void
     *************************************************************************/
    public void encriptyPassword() {
        if (password != null)
            this.password = AppUtils.cryptWithMD5(password);
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: getNewPassword()
     Date: 06/18/2016
     About: to get the user's  new password.
     Parameters: null
     return: String - newPassword
     *************************************************************************/
    @Exclude
    public String getNewPassword() {
        return newPassword;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: setNewPassword()
     Date: 06/18/2016
     About: to get the user's  new password.
     Parameters: String - newPassword
     return: void
     *************************************************************************/
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: encriptyNewPasswor()
     Date: 06/18/2016
     About: This function makes the encryption of  newpassword to send to the FireBase.
     Parameters: null
     return: void
     *************************************************************************/
    public void encriptyNewPasswor() {
        if (newPassword != null)
            newPassword =  AppUtils.cryptWithMD5(newPassword);
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: getName()
     Date: 06/18/2016
     About: to get the user's  name.
     Parameters: null
     return: String - name
     *************************************************************************/
    public String getName() {
        return name;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: setName()
     Date: 06/18/2016
     About: to set the user's  name.
     Parameters: String - name
     return: null
     *************************************************************************/
    public void setName(String name) {
        this.name = name;
    }


    /**********************************************************************
     Author: Victor Bitencourt
     Method: saveUser()
     Date: 06/18/2016

     About: This function first saves the User`s data in the SqlLite database.
     * after that, it sends the User object to the FireBase and saves there the
     * data.
     Parameters: Context context
     return: null
     *************************************************************************/
    public void saveUser(Context context) {

        DBUtils.getInstance(context).updateUser(this);
        FirebaseDatabase.getInstance().getReference().child(FB_NODO_USER).child(this.uid).setValue(this);
    }


    /**********************************************************************
     Author: Victor Bitencourt
     Method: getMethod()
     Date: 06/18/2016
     About: to get the user's method, method means which kind of sing in the user
     used to enter in the app. like facebook or Google +

     Parameters: null
     return: int - method
     *************************************************************************/
    public int getMethod() {
        return method;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: setMethod()
     Date: 06/18/2016
     About: to set the user's method, method means which kind of sing in the user
     used to enter in the app. like facebook or Google +

     Parameters: int - method
     return: null
     *************************************************************************/
    public void setMethod(int method) {
        this.method = method;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: getUriPhoto()
     Date: 06/18/2016
     About: to set the user's uri photo, for exemple when a user sing in to the
     app by facebook (or can be other integrations), the facebook provides a uri that contain the user's photo
     to access.

     Parameters: null
     return: String - uriPhotoProfile
     *************************************************************************/
    public String getUriPhoto() {
        return uriPhoto;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: setUriPhoto()
     Date: 06/18/2016
     About: to set the user's uri photo, for exemple when a user sing in to the
     app by facebook (or can be other integrations), the facebook provides a uri that contain the user's photo
     to access.

     Parameters: String - uriPhotoProfile
     return: null
     *************************************************************************/
    public void setUriPhoto(String uriPhoto) {
        this.uriPhoto = uriPhoto;
    }


    /**********************************************************************
     Author: Victor Bitencourt
     Method: getEvaluation()
     Date: 06/18/2016
     About: to get if the user already made the evaluation about
     the app.

     Parameters: null
     return: boolean - evaluation
     *************************************************************************/
    public boolean getEvaluation() {
        return evaluation;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: setEvaluation()
     Date: 06/18/2016
     About: to set (true or false) if the user already made the evaluation about
     the app.

     Parameters: boolean - evaluation
     return: void
     *************************************************************************/
    public void setEvaluation(boolean evaluation) {
        this.evaluation = evaluation;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: isAppIntro()
     Date: 06/18/2016
     About: to get (true or false) if the user already saw the app's introduction.

     Parameters: boolean - appIntro
     return: void
     *************************************************************************/
    public boolean isAppIntro() {
        return appIntro;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: setAppIntro()
     Date: 06/18/2016
     About: to set (true or false) if the user already saw the app's introduction.

     Parameters: null
     return: boolean - appIntro
     *************************************************************************/
    public void setAppIntro(boolean appIntro) {
        this.appIntro = appIntro;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: isAppIntro()
     Date: 06/18/2016
     About: to get (true or false) if the user already did the app's QUIZ.

     Parameters: boolean - appIntro
     return: void
     *************************************************************************/
    public boolean isAppQuiz() {
        return appQuiz;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: setAppQuiz()
     Date: 06/18/2016
     About: to set (true or false) if the user already did the app's QUIZ.

     Parameters: null
     return: boolean - appIntro
     *************************************************************************/
    public void setAppQuiz(boolean appQuiz) {
        this.appQuiz = appQuiz;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: getDtEvaluation()
     Date: 06/18/2016
     About: to get the date of user's evaluation.
     the date is given by number of miliseconds since january 1970.

     Parameters: null
     return: long - dtEvaluation
     *************************************************************************/
    public long getDtEvaluation() {
        return dtEvaluation;
    }

    /**********************************************************************
     Author: Victor Bitencourt
     Method: setDtEvaluation()
     Date: 06/18/2016
     About: to get the date of user's evaluation.
     the date is given by number of miliseconds since january 1970.

     Parameters: long - dtEvaluation (miliseconds)
     return: null
     *************************************************************************/
    public void setDtEvaluation(long dtEvaluation) {
        this.dtEvaluation = dtEvaluation;
    }

    @Exclude
    public Bitmap getProfilePhoto() {

        Bitmap bitmap = null;
        if( this.profilePhoto != null) {
            byte[] decodedByte = Base64.decode(this.profilePhoto, 0);
            bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        }
        return bitmap;
    }
    @Exclude
    public void setProfilePhoto(Bitmap profilePhoto) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        profilePhoto.compress(Bitmap.CompressFormat.PNG, 100, out);
        byte[] bytes = out.toByteArray();
        String imageEncoded = Base64.encodeToString(bytes, Base64.DEFAULT);
        this.profilePhoto = imageEncoded;
    }
}
