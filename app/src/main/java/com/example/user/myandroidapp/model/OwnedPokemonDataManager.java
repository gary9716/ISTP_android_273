package com.example.user.myandroidapp.model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by user on 2016/8/24.
 */
public class OwnedPokemonDataManager {

    Context mContext;
    ArrayList<OwnedPokemonInfo> ownedPokemonInfos = null;

    public OwnedPokemonDataManager(Context context) {
        mContext = context;
    }

    public void loadListViewData() {
        ownedPokemonInfos = new ArrayList<>();

        BufferedReader reader;
        String line = null;
        String[] dataFields = null;

        try {
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

        return ownedPokemonInfo;
    }

    public ArrayList<OwnedPokemonInfo> getOwnedPokemonInfos() {
        return ownedPokemonInfos;
    }

}
