package com.example.user.myandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.myandroidapp.adapter.PokemonListViewAdapter;
import com.example.user.myandroidapp.model.OwnedPokemonDataManager;
import com.example.user.myandroidapp.model.OwnedPokemonInfo;

import java.util.ArrayList;

public class PokemonListActivity extends AppCompatActivity implements OnPokemonSelectedChangeListener {

    PokemonListViewAdapter arrayAdapter;
    ArrayList<OwnedPokemonInfo> ownedPokemonInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        OwnedPokemonDataManager dataManager = new OwnedPokemonDataManager(this);
        dataManager.loadListViewData();

        ownedPokemonInfos = dataManager.getOwnedPokemonInfos();

        ListView listView = (ListView)findViewById(R.id.listView);
        arrayAdapter = new PokemonListViewAdapter(this,
                R.layout.row_view_of_pokemon_list,
                ownedPokemonInfos);
        arrayAdapter.pokemonSelectedChangeListener = this;

        listView.setAdapter(arrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(arrayAdapter.selectedPokemons.isEmpty()) {
            return false; //not showing anything on action bar
        }
        else {
            getMenuInflater().inflate(R.menu.selected_pokemon_action_bar, menu);
            return true; //show the menu items on action bar
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_delete) {
            for(OwnedPokemonInfo ownedPokemonInfo : arrayAdapter.selectedPokemons) {
                ownedPokemonInfos.remove(ownedPokemonInfo);
            }
            arrayAdapter.selectedPokemons.clear();
            arrayAdapter.notifyDataSetChanged();
            invalidateOptionsMenu();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSelectedChange(OwnedPokemonInfo ownedPokemonInfo) {
        invalidateOptionsMenu(); //make system call onCreateOptionsMenu
    }
}
