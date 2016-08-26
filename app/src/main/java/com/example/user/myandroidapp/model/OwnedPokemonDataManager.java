package com.example.user.myandroidapp.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by user on 2016/8/24.
 */
public class OwnedPokemonDataManager {

    Context mContext;
    ArrayList<OwnedPokemonInfo> ownedPokemonInfos = null;
    static final int numInitPokemons = 3;
    OwnedPokemonInfo[] initPokemonInfos = new OwnedPokemonInfo[numInitPokemons];

    public OwnedPokemonDataManager(Context context) {
        mContext = context;
    }

    public void loadPokemonTypes() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("pokemon_types.csv"))
            );
            OwnedPokemonInfo.typeNames = reader.readLine().split(",");
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadListViewData() {
        ownedPokemonInfos = new ArrayList<>();

        BufferedReader reader;
        String line = null;
        String[] dataFields = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("init_pokemon_data.csv"))
            );

            for(int i = 0;i< numInitPokemons;i++) {
                dataFields = reader.readLine().split(",");
                initPokemonInfos[i] = constructPokemonInfo(dataFields);
            }

            reader.close();

            reader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("pokemon_data.csv"))
            );

            while((line = reader.readLine()) != null) {
                dataFields = line.split(",");
                ownedPokemonInfos.add(constructPokemonInfo(dataFields));
            }
            reader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    static final int skillStartIndex = 8;

    private OwnedPokemonInfo constructPokemonInfo(String[] dataFields) {
        OwnedPokemonInfo ownedPokemonInfo = new OwnedPokemonInfo();
        ownedPokemonInfo.pokemonId = Integer.valueOf(dataFields[0]);
        ownedPokemonInfo.name = dataFields[2];
        ownedPokemonInfo.level = Integer.valueOf(dataFields[3]);
        ownedPokemonInfo.currentHP = Integer.valueOf(dataFields[4]);
        ownedPokemonInfo.maxHP = Integer.valueOf(dataFields[5]);
        ownedPokemonInfo.type_1 = Integer.valueOf(dataFields[6]);
        ownedPokemonInfo.type_2 = Integer.valueOf(dataFields[7]);

        for(int i = skillStartIndex;i < dataFields.length;i++) {
            ownedPokemonInfo.skills[i - skillStartIndex] = dataFields[i];
        }

        Log.d("testDM", ownedPokemonInfo.pokemonId + "," +
                ownedPokemonInfo.name + "," +
                ownedPokemonInfo.level + "," +
                ownedPokemonInfo.type_1 + "," +
                ownedPokemonInfo.type_2 + "," +
                ownedPokemonInfo.currentHP + "," +
                ownedPokemonInfo.maxHP + "," +
                Arrays.toString(ownedPokemonInfo.skills));

        return ownedPokemonInfo;
    }

    public ArrayList<OwnedPokemonInfo> getOwnedPokemonInfos() {
        return ownedPokemonInfos;
    }
    public OwnedPokemonInfo[] getInitPokemonInfos() {
        return initPokemonInfos;
    }
}
