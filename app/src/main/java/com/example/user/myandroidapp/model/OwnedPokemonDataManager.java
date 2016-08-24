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

    private OwnedPokemonInfo constructPokemonInfo(String[] dataFields) {
        
        return null;
    }

}
