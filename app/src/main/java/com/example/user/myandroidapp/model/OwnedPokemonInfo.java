package com.example.user.myandroidapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2016/8/24.
 */
public class OwnedPokemonInfo implements Parcelable {

    public final static String nameKey = "name";


    public static final int maxNumSkills = 4;
    public static String[] typeNames;

    public int pokemonId;
    public String name;
    public int level;
    public int currentHP;
    public int maxHP;

    public int type_1;
    public int type_2;
    public String[] skills = new String[maxNumSkills];

    public boolean isSelected = false;

    public OwnedPokemonInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pokemonId);
        dest.writeString(this.name);
        dest.writeInt(this.level);
        dest.writeInt(this.currentHP);
        dest.writeInt(this.maxHP);
        dest.writeInt(this.type_1);
        dest.writeInt(this.type_2);
        dest.writeStringArray(this.skills);
    }

    protected OwnedPokemonInfo(Parcel in) {
        this.pokemonId = in.readInt();
        this.name = in.readString();
        this.level = in.readInt();
        this.currentHP = in.readInt();
        this.maxHP = in.readInt();
        this.type_1 = in.readInt();
        this.type_2 = in.readInt();
        this.skills = in.createStringArray();
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
}
