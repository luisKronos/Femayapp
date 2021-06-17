package com.grupofemaya.SupervisionAlumbradoPublicoNew.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Generic.GenericActivity;

import org.grupofemaya.SupervisionAlumbradoPublico.R;

import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.Login.LoginActivity;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.UI.NewAlumbrado.NewHomeFragment;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.AlarmReceiverCustom;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.GPSTracker;
import com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.SharedPreferencesManager;

import java.util.Calendar;

public class MainActivity extends GenericActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gpsTracker = new GPSTracker(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NewHomeFragment homeFragment = new NewHomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, homeFragment,
                homeFragment.getTag()).commit();

        Intent intent = new Intent(this, AlarmReceiverCustom.class);
        AlarmManager alarmManager =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 1);

        // since the app is installed and repeating every 6 hours
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                (1000 * 60 * 20 * 3) * 6, pendingIntent);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(that, ScanActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            NewHomeFragment fragment = new NewHomeFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, fragment, fragment.getTag()).commit();
        } else if (id == R.id.nav_pendings) {
            //PendingChecksFragment fragment = new PendingChecksFragment().newInstance(true);
            //FragmentManager manager = getSupportFragmentManager();
            //manager.beginTransaction().replace(R.id.content_main, fragment, fragment.getTag()).commit();
        }else if (id == R.id.nav_logout) {
            SharedPreferencesManager.getInstance().setIdUser("0");
            SharedPreferencesManager.getInstance().setCheckIn(null);
            SharedPreferencesManager.getInstance().setCheckOut(null);
            Intent intent = new Intent(that, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
