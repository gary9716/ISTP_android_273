package com.example.user.myandroidapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;

/**
 * Created by user on 2016/8/31.
 */
public class PokemonListFragment extends Fragment implements OnPokemonSelectedChangeListener, AdapterView.OnItemClickListener, DialogInterface.OnClickListener {

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

        OwnedPokemonDataManager dataManager = new OwnedPokemonDataManager(getActivity());
        dataManager.loadListViewData();
        dataManager.loadPokemonTypes();

        ownedPokemonInfos = dataManager.getOwnedPokemonInfos();

        OwnedPokemonInfo[] initPokemonInfos = dataManager.getInitPokemonInfos();
        Intent srcIntent = getActivity().getIntent();
        int selectedIndex = srcIntent.getIntExtra(MainActivity.selectedPokemonIndexKey, 0);
        ownedPokemonInfos.add(0, initPokemonInfos[selectedIndex]);

        setHasOptionsMenu(true);
        setMenuVisibility(true);
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
                    arrayAdapter.remove(ownedPokemonInfo);

                    //alternatives
//                    ownedPokemonInfos.remove(ownedPokemonInfo);
//                    arrayAdapter.notifyDataSetChanged();

                    Toast.makeText(getActivity(), pokemonName + "已經被存到電腦裡了", Toast.LENGTH_SHORT).show();
                }
            }
            // add a new result code in DetailActivity and write a corresponding conditional statement here to do the update effect


        }

    }

    void deleteOwnedPokemons() {
        for(OwnedPokemonInfo ownedPokemonInfo : arrayAdapter.selectedPokemons) {
            ownedPokemonInfos.remove(ownedPokemonInfo);
        }
        arrayAdapter.selectedPokemons.clear();
        arrayAdapter.notifyDataSetChanged();

        // second way to remove from ownedPokemonInfos
//            for(OwnedPokemonInfo ownedPokemonInfo : arrayAdapter.selectedPokemons) {
//                arrayAdapter.remove(ownedPokemonInfo);
//            }
//            arrayAdapter.selectedPokemons.clear();

        getActivity().invalidateOptionsMenu();
    }
}
