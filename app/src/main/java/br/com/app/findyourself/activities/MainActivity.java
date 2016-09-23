package br.com.app.findyourself.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import br.com.app.findyourself.R;
import br.com.app.findyourself.firebase.bean.User;
import br.com.app.findyourself.utils.AppUtils;
import br.com.app.findyourself.utils.LoadProfilePhoto;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends CommonActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            User user = (User) savedInstanceState.getSerializable("user");

            if ( user != null) {
                User.getInstance().setUid(user.getUid());
                User.getInstance().setName(user.getName());
                User.getInstance().setEmail(user.getEmail());
                User.getInstance().setUriPhoto(user.getUriPhoto());

               if (user.getProfilePhoto() != null)
                    User.getInstance().setProfilePhoto(user.getProfilePhoto());


            }
        }
        beforeViews();
        setContentView(R.layout.activity_main);
        initViews();
    }


    @Override
    protected void beforeViews() {
        AppUtils.initAppServices(getApplicationContext());
        createTransition();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_DRAWER);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void initViews() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_content_TOOLBAR);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_DRAWER);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.inflateMenu(R.menu.main_menu);

        TextView header_Main_Email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_main_tv_LOGIN);
        TextView header_Main_NAME = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_main_tv_NAME);


        /*ViewPager viewPager = (ViewPager) findViewById(R.id.activity_content_VIEWPAGER);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFragment(new MainFragment(this), getResources().getString((R.string.fragment_main)));
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_content_TABS);
        tabLayout.setupWithViewPager(viewPager);*/

        header_Main_NAME.setText(User.getInstance().getName());
        header_Main_Email.setText(User.getInstance().getEmail());

        if (User.getInstance().getProfilePhoto() != null) {
            CircleImageView header_Main_IMAGEM = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.header_main_imgv_IMAGEM);
            header_Main_IMAGEM.setImageBitmap(User.getInstance().getProfilePhoto());
        }else{
            new LoadProfilePhoto().execute(this,User.getInstance().getUriPhoto());
        }

    }

    @Override
    protected void initUser() {

    }

    @Override
    protected void createTransition() {

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        if (User.getInstance() != null){
            savedInstanceState.putSerializable("user",User.getInstance());
        }
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                User.getInstance().destroyInstance();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;


            default:
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_DRAWER);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
