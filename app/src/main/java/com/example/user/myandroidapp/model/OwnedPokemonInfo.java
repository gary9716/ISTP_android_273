package com.example.user.myandroidapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by user on 2016/8/24.
 */

@ParseClassName("OwnedPokemonInfo")
public class OwnedPokemonInfo extends ParseObject implements Parcelable {

    public final static String nameKey = "name";


    public static final int maxNumSkills = 4;
    public static String[] typeNames;

    private int pokemonId;
    private String name;
    private int level;
    private int currentHP;
    private int maxHP;
    private int type_1;
    private int type_2;
    private String[] skills = new String[maxNumSkills];

    public boolean isSelected = false;

    public OwnedPokemonInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getPokemonId());
        dest.writeString(this.getName());
        dest.writeInt(this.getLevel());
        dest.writeInt(this.getCurrentHP());
        dest.writeInt(this.getMaxHP());
        dest.writeInt(this.getType_1());
        dest.writeInt(this.getType_2());
        dest.writeStringArray(this.getSkills());
    }

    protected OwnedPokemonInfo(Parcel in) {
        this.setPokemonId(in.readInt());
        this.setName(in.readString());
        this.setLevel(in.readInt());
        this.setCurrentHP(in.readInt());
        this.setMaxHP(in.readInt());
        this.setType_1(in.readInt());
        this.setType_2(in.readInt());
        this.setSkills(in.createStringArray());
    }

    public static final Parcelable.Creator<OwnedPokemonInfo> CREATOR = new Parcelable.Creator<OwnedPokemonInfo>() {
        @Override
        public OwnedPokemonInfo createFromParcel(Parcel source) {
            return new OwnedPokemonInfo(source);
        }

        @Override
        public OwnedPokemonInfo[] newArray(int size) {
            return new OwnedPokemonInfo[size];
        }
    };

    public int getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(int pokemonId) {
        this.pokemonId = pokemonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getType_1() {
        return type_1;
    }

    public void setType_1(int type_1) {
        this.type_1 = type_1;
    }

    public int getType_2() {
        return type_2;
    }

    public void setType_2(int type_2) {
        this.type_2 = type_2;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }
}
