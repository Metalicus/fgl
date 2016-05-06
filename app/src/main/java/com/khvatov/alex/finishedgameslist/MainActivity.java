package com.khvatov.alex.finishedgameslist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.khvatov.alex.utils.Exporter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String FRAGMENT_PLATFORM_LIST = "FRAGMENT_PLATFORM_LIST";
    public static final String FRAGMENT_GAME_LIST = "FRAGMENT_GAME_LIST";

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //noinspection ConstantConditions
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                final String tag = fragment.getTag();
                final Intent intent;
                switch (tag) {
                    case FRAGMENT_PLATFORM_LIST:
                        intent = new Intent(MainActivity.this, PlatformDetailActivity.class);
                        break;
                    case FRAGMENT_GAME_LIST:
                        intent = new Intent(MainActivity.this, GameDetailActivity.class);
                        break;
                    default:
                        throw new RuntimeException("Unknown activity tag: " + tag);
                }
                startActivity(intent);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //noinspection ConstantConditions
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            final MenuItem item = navigationView.getMenu().getItem(0);
            item.setChecked(true);
            onNavigationItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // settings was selected
        final int id = item.getItemId();
/*        if (id == R.id.settings) {
            // todo: open settings
            export();

            drawer.closeDrawer(GravityCompat.START);
            return true;
        }*/

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (id) {
            case R.id.console_list:
                fragmentTransaction.replace(R.id.fragment_container, new PlatformListFragment(), FRAGMENT_PLATFORM_LIST);
                break;
            case R.id.games_list:
                fragmentTransaction.replace(R.id.fragment_container, new GamesListFragment(), FRAGMENT_GAME_LIST);
                break;
        }
        fragmentTransaction.commit();
        setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void export() {
        //todo: remove before production
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setMessage("Are you sure you want to export data to Android Studio console?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Exporter.exportToConsole(MainActivity.this);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}
