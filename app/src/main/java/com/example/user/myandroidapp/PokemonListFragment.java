package com.example.user.myandroidapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.myandroidapp.adapter.PokemonListViewAdapter;
import com.example.user.myandroidapp.model.OwnedPokemonDataManager;
import com.example.user.myandroidapp.model.OwnedPokemonInfo;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/8/31.
 */
public class PokemonListFragment extends Fragment implements OnPokemonSelectedChangeListener, AdapterView.OnItemClickListener, DialogInterface.OnClickListener, FindCallback<OwnedPokemonInfo> {

    public final static int detailActivityRequestCode = 1;
    public final static String ownedPokemonInfoKey = "ownedPokemonInfoKey";

    PokemonListViewAdapter arrayAdapter;
    ArrayList<OwnedPokemonInfo> ownedPokemonInfos;
    AlertDialog alertDialog;

    View fragmentView;

    public static PokemonListFragment newInstance() {

        Bundle args = new Bundle();

        PokemonListFragment fragment = new PokemonListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ownedPokemonInfos = new ArrayList<>();

        setHasOptionsMenu(true);
        setMenuVisibility(true);
    }

    private final static String recordIsInDBKey = "recordIsInDB";

    void prepareListViewData() {
        SharedPreferences preferences =
                getActivity().getSharedPreferences(PokemonListFragment.class.getSimpleName(),
                        Context.MODE_PRIVATE);

        boolean recordIsInDB = preferences.getBoolean(recordIsInDBKey, false);

        if(!recordIsInDB) { //load data from csv and save it into DB
            loadFromCSV();

            OwnedPokemonInfo.initDB(ownedPokemonInfos);

            preferences.edit().putBoolean(recordIsInDBKey, true).commit();
        }
        else {
            ParseQuery<OwnedPokemonInfo> query = OwnedPokemonInfo.getQuery();
            query.fromPin(OwnedPokemonInfo.localDBTableName).findInBackground(this);
            query = OwnedPokemonInfo.getQuery();
            query.findInBackground(this);
        }

    }

    void loadFromCSV() {

        OwnedPokemonDataManager dataManager = new OwnedPokemonDataManager(getActivity());
        dataManager.loadListViewData();
        dataManager.loadPokemonTypes();

        ownedPokemonInfos.addAll(dataManager.getOwnedPokemonInfos());

        OwnedPokemonInfo[] initPokemonInfos = dataManager.getInitPokemonInfos();
        Intent srcIntent = getActivity().getIntent();
        int selectedIndex = srcIntent.getIntExtra(MainActivity.selectedPokemonIndexKey, 0);
        ownedPokemonInfos.add(0, initPokemonInfos[selectedIndex]);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.activity_pokemon_list, null);
            ListView listView = (ListView) fragmentView.findViewById(R.id.listView);
            arrayAdapter = new PokemonListViewAdapter(getActivity(),
                    R.layout.row_view_of_pokemon_list,
                    ownedPokemonInfos);
            arrayAdapter.pokemonSelectedChangeListener = this;

            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(this);

            alertDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("警告")
                    .setMessage("你確定要丟棄這些神奇寶貝嗎")
                    .setNegativeButton("取消", this)
                    .setPositiveButton("確定", this)
                    .create();

        }

        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(!arrayAdapter.selectedPokemons.isEmpty()) {
            inflater.inflate(R.menu.selected_pokemon_action_bar, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_delete) {
            alertDialog.show();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == AlertDialog.BUTTON_NEGATIVE) {
            Toast.makeText(getActivity(), "取消丟棄", Toast.LENGTH_SHORT).show();
        }
        else if(which == AlertDialog.BUTTON_POSITIVE) {
            deleteOwnedPokemons();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OwnedPokemonInfo ownedPokemonInfo = arrayAdapter.getItem(position);

        Intent intent = new Intent();
        intent.setClass(getActivity(), DetailActivity.class);
        intent.putExtra(ownedPokemonInfoKey, ownedPokemonInfo);
        startActivityForResult(intent, detailActivityRequestCode);
    }

    @Override
    public void onSelectedChange(OwnedPokemonInfo ownedPokemonInfo) {
        getActivity().invalidateOptionsMenu(); //make system call onCreateOptionsMenu
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == detailActivityRequestCode) { //this result came from detail activity
            if(resultCode == DetailActivity.savePokemonIntoComputer) {
                String pokemonName = data.getStringExtra(OwnedPokemonInfo.nameKey);
                if(arrayAdapter != null) {
                    OwnedPokemonInfo ownedPokemonInfo = arrayAdapter.getItemWithName(pokemonName);
                    if(ownedPokemonInfo != null)
                        removePokemonInfo(ownedPokemonInfo);

                    Toast.makeText(getActivity(), pokemonName + "已經被存到電腦裡了", Toast.LENGTH_SHORT).show();
                }
            }
            // add a new result code in DetailActivity and write a corresponding conditional statement here to do the update effect


        }

    }

    void deleteOwnedPokemons() {
        for(OwnedPokemonInfo ownedPokemonInfo : arrayAdapter.selectedPokemons) {
            removePokemonInfo(ownedPokemonInfo);
        }
        arrayAdapter.selectedPokemons.clear();
        arrayAdapter.notifyDataSetChanged();

        getActivity().invalidateOptionsMenu();
    }

    //when data has been read from DB
    @Override
    public void done(List<OwnedPokemonInfo> objects, ParseException e) {
        if(e == null) { //no error
            ownedPokemonInfos.clear();
            ownedPokemonInfos.addAll(objects);

            if(arrayAdapter != null)
                arrayAdapter.notifyDataSetChanged();
        }

    }

    public void removePokemonInfo(OwnedPokemonInfo pokemonInfo) {
        if(arrayAdapter != null)
            arrayAdapter.remove(pokemonInfo);

        //remove from DB
        pokemonInfo.unpinInBackground(OwnedPokemonInfo.localDBTableName);
        pokemonInfo.deleteEventually();

    }

    @Override
    public void onPause() {
        super.onPause();
        OwnedPokemonInfo.saveToDB(ownedPokemonInfos);
    }
}
