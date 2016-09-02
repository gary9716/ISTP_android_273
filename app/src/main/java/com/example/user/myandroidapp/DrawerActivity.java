package com.example.user.myandroidapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DrawerActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener, FragmentManager.OnBackStackChangedListener {

    Toolbar toolbar;
    AccountHeader headerResult;
    IProfile profile;
    Drawer naviDrawer;
    Fragment[] fragments;
    FragmentManager fragmentManager;

    final int defaultSelectedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragments = new Fragment[3];
        fragments[0] = PokemonListFragment.newInstance();
        fragments[1] = TestFragment.newInstance("fake 2");
        fragments[2] = PokemonMapFragment.newInstance();

        SharedPreferences preferences = getSharedPreferences(Application.class.getSimpleName(), MODE_PRIVATE);

        String profileName = preferences.getString(
                MainActivity.trainerNameKey,
                "batman");

        String profileEmail = preferences.getString(
                MainActivity.trainerEmailKey,
                "batman@gmail.com");

        String profileImgUrl = preferences.getString(
                MainActivity.trainerProfileImgKey,
                null);

        if(profileImgUrl == null) {
            Drawable profileIcon = null;
            profileIcon = Utils.getDrawable(this, R.drawable.profile3);
            profile = new ProfileDrawerItem()
                    .withName(profileName)
                    .withEmail(profileEmail)
                    .withIcon(profileIcon);
        }
        else {
            profile = new ProfileDrawerItem()
                    .withName(profileName)
                    .withEmail(profileEmail);

            ImageLoader.getInstance().loadImage(profileImgUrl,
                    new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    profile.withIcon(loadedImage);
                    headerResult.clear();
                    headerResult.addProfile(profile, 0);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }

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
                .withSavedInstance(savedInstanceState) //recover
                .build();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //save drawer and headers' UI state
        outState = naviDrawer.saveInstanceState(outState);
        outState = headerResult.saveInstanceState(outState);

        super.onSaveInstanceState(outState);
    }

    void displayFragment(Fragment fragment, boolean canBeReversed) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragmentContainer, fragment);
        if(canBeReversed) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        if(naviDrawer != null && naviDrawer.isDrawerOpen()) {
            naviDrawer.closeDrawer();
        }
        else if(fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        displayFragment(fragments[position - 1], true);
        return false; //return false to bound back the drawer after clicking one of items
    }

    @Override
    public void onBackStackChanged() {
        for(int i = 0;i < fragments.length;i++) {
            if(fragments[i].isVisible()) {
                naviDrawer.setSelectionAtPosition(i + 1, false);
                break;
            }
        }
    }
}
