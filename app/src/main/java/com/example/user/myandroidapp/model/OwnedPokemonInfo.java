package com.example.user.myandroidapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

/**
 * Created by user on 2016/8/24.
 */

@ParseClassName("OwnedPokemonInfo")
public class OwnedPokemonInfo extends ParseObject implements Parcelable {

    public final static String pokemonIdKey = "pokeId";
    public final static String nameKey = "name";
    public final static String levelKey = "level";
    public final static String currentHPKey = "currentHP";
    public final static String maxHPKey = "maxHP";
    public final static String type1Key = "type_1";
    public final static String type2Key = "type_2";
    public final static String skillsKey = "skills";

    public static final int maxNumSkills = 4;
    public static String[] typeNames;

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

    //getter and setter

    public int getPokemonId() {
        return getInt(pokemonIdKey);
    }

    public void setPokemonId(int pokemonId) {
        put(pokemonIdKey, pokemonId);
    }


    public String getName() {
        return getString(nameKey);
    }

    public void setName(String name) {
        put(nameKey, name);
    }

    public int getLevel() {
        return getInt(levelKey);
    }

    public void setLevel(int level) {
        put(levelKey, level);
    }

    public int getCurrentHP() {
        return getInt(currentHPKey);
    }

    public void setCurrentHP(int currentHP) {
        put(currentHPKey, currentHP);
    }

    public int getMaxHP() {
        return getInt(maxHPKey);
    }

    public void setMaxHP(int maxHP) {
        put(maxHPKey, maxHP);
    }

    public int getType_1() {
        return getInt(type1Key);
    }

    public void setType_1(int type_1) {
        put(type1Key, type_1);
    }

    public int getType_2() {
        return getInt(type2Key);
    }

    public void setType_2(int type_2) {
        put(type2Key, type_2);
    }

    boolean skillHaveBeenInited = false;
    boolean skillHaveBeenModified = false;

    public String[] getSkills() {
        if(!skillHaveBeenInited) {
            skillHaveBeenInited = true;
            this.skills = readSkillFromParseObjStorage();
        }
        else if(skillHaveBeenModified) {
            skillHaveBeenModified = false;
            this.skills = readSkillFromParseObjStorage();
        }

        return this.skills;
    }

    private String[] readSkillFromParseObjStorage() {
        ArrayList<String> skillList = (ArrayList)get(skillsKey);
        String[] skillArray = new String[maxNumSkills];
        if(skillList != null) {
            for(int i = 0;i < skillList.size();i++) {
                skillArray[i] = skillList.get(i);
            }
        }

        return skillArray;
    }

    public void setSkills(String[] skills) {
        ArrayList<String> skillList = new ArrayList<>(skills.length);
        for(String skillName : skills) {
            if(skillName != null) {
                skillList.add(skillName);
            }
        }
        put(skillsKey, skillList);

        skillHaveBeenModified = true;
    }

    public static ParseQuery<OwnedPokemonInfo> getQuery() {
        return ParseQuery.getQuery(OwnedPokemonInfo.class);
    }

    public static final String localDBTableName = OwnedPokemonInfo.class.getSimpleName();

}
