package br.com.app.findyourself.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import br.com.app.findyourself.R;
import br.com.app.findyourself.activities.MainActivity;
import br.com.app.findyourself.firebase.bean.User;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Victor on 7/2/2016.
 */
public class LoadProfilePhoto extends AsyncTask<Object, Integer, Bitmap> {

    private static String TAG = "LoadProfilePhoto";
    private MainActivity mMainActivity;

    @Override
    protected Bitmap doInBackground(Object... params) {

        mMainActivity = (MainActivity) params[0];
        String uri = (String) params[1];
        Bitmap bitmap = null;

        try {

            bitmap = BitmapFactory.decodeStream((InputStream) new URL(uri).getContent());

        } catch (IOException e) {
            Log.e(TAG,"doInBackground: "+ e.getMessage());
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

       if (bitmap != null) {
           User.getInstance().setProfilePhoto(bitmap);
           NavigationView navigationView = (NavigationView) mMainActivity.findViewById(R.id.nav_view);
           CircleImageView header_Main_IMAGEM = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.header_main_imgv_IMAGEM);
           header_Main_IMAGEM.setImageBitmap(User.getInstance().getProfilePhoto());
       }
        //Codigo
    }

}
