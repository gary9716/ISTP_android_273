package com.example.user.myandroidapp.model;

/**
 * Created by user on 2016/8/24.
 */
public class OwnedPokemonInfo {

    public static final int maxNumSkills = 4;

    public int pokemonId;
    public String name;
    public int level;
    public int currentHP;
    public int maxHP;

    public int type_1;
    public int type_2;
    public String[] skills = new String[maxNumSkills];

    public OwnedPokemonInfo() {
    }

}
