package com.example.user.myandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.myandroidapp.adapter.PokemonListViewAdapter;
import com.example.user.myandroidapp.model.OwnedPokemonDataManager;
import com.example.user.myandroidapp.model.OwnedPokemonInfo;

import java.util.ArrayList;

public class PokemonListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        OwnedPokemonDataManager dataManager = new OwnedPokemonDataManager(this);
        dataManager.loadListViewData();

        ArrayList<OwnedPokemonInfo> ownedPokemonInfos =
                dataManager.getOwnedPokemonInfos();

        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter arrayAdapter = new PokemonListViewAdapter(this,
                R.layout.row_view_of_pokemon_list,
                ownedPokemonInfos);

        listView.setAdapter(arrayAdapter);

    }

}
