package com.example.user.myandroidapp;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.user.myandroidapp.model.Utils;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class DrawerActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {

    Toolbar toolbar;
    AccountHeader headerResult;
    IProfile profile;
    Drawer naviDrawer;
    Fragment[] fragments;

    final int defaultSelectedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragments = new Fragment[3];
        fragments[0] = TestFragment.newInstance("fake 1");
        fragments[1] = TestFragment.newInstance("fake 2");
        fragments[2] = TestFragment.newInstance("fake 3");

        String profileName = "batman";
        String profileEmail = "batman@gmail.com";

        Drawable profileIcon = null;
        profileIcon = Utils.getDrawable(this, R.drawable.profile3);
        profile = new ProfileDrawerItem()
                .withName(profileName)
                .withEmail(profileEmail)
                .withIcon(profileIcon);

        buildDrawerHeader(false, savedInstanceState);

        naviDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .inflateMenu(R.menu.drawer_item_list)
                .withOnDrawerItemClickListener(this)
                .withSavedInstance(savedInstanceState)
                .build();

        //make naviDrawer by default select first item
        naviDrawer.setSelectionAtPosition(defaultSelectedIndex + 1, false);
        displayFragment(fragments[defaultSelectedIndex], true);
    }

    private void buildDrawerHeader(boolean compact, Bundle savedInstanceState) {

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(compact)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();

    }

    void displayFragment(Fragment fragment, boolean canBeReversed) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragmentContainer, fragment);
        if(canBeReversed) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        //TODO: display corresponding fragment
        return false; //return false to bound back the drawer after clicking one of items
    }
}
