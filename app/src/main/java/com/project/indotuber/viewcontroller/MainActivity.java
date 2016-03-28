package com.project.indotuber.viewcontroller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.project.indotuber.R;
import com.project.indotuber.event.HideSpinningLoadingEvent;
import com.project.indotuber.event.ShowSpinningLoadingEvent;
import com.project.indotuber.fonts.CustomTypefaceSpan;
import com.project.indotuber.singleton.AppController;
import com.project.indotuber.singleton.InterfaceManager;
import com.project.indotuber.viewcontroller.mainView.FAQPageFragment;
import com.project.indotuber.viewcontroller.mainView.MainPageFragment;
import com.project.indotuber.viewcontroller.mainView.TNCPageFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    FragmentManager fm;
    NavigationView navigationView;
    FrameLayout rootFrameLayout;
    Toolbar toolbar;
    Context ctx;
    MainPageFragment mainPageFragment;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri data = getIntent().getData();
        fm = getSupportFragmentManager();
        mainPageFragment = new MainPageFragment();
        setContentView(R.layout.activity_main);
        if(data !=null) {
            String scheme = data.getScheme();
            String host = data.getHost();
            if(host.equalsIgnoreCase("www.idtuber.com") || host.equalsIgnoreCase("idtuber.com") ) {
                List<String> params = data.getPathSegments();
                String first = params.get(0);
                if (first.equalsIgnoreCase("watch")) {
                    String videoId = params.get(1);
                    Bundle bundle = new Bundle();
                    bundle.putString("videoId", videoId);
                    mainPageFragment.setArguments(bundle);
                }
            }
        }

        fm.beginTransaction()
                .replace(R.id.mainContainer,mainPageFragment)
                .commitAllowingStateLoss();

        rootFrameLayout = (FrameLayout)findViewById(R.id.rootMainFrameLayout);
        ctx = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#555555"));
        }
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Menu m = navigationView.getMenu();
        navigationView.getMenu().getItem(0).setChecked(true);
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fm.beginTransaction()
                    .replace(R.id.mainContainer,new MainPageFragment())
                    .commitAllowingStateLoss();

        }else if (id == R.id.nav_faq) {
            fm.beginTransaction()
                    .replace(R.id.mainContainer,new FAQPageFragment())
                    .commitAllowingStateLoss();
        }
        else if (id == R.id.nav_tnc) {
            fm.beginTransaction()
                    .replace(R.id.mainContainer,new TNCPageFragment())
                    .commitAllowingStateLoss();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void generateKeyhash(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.project.indotuber",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    public void onEvent(ShowSpinningLoadingEvent event){
        InterfaceManager.sharedInstance().showLoading(rootFrameLayout,ctx);
    }

    public void onEvent(HideSpinningLoadingEvent event){
        InterfaceManager.sharedInstance().hideLoading();
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
            finish();
        }
    }
}
