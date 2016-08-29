package com.example.user.myandroidapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myandroidapp.OnPokemonSelectedChangeListener;
import com.example.user.myandroidapp.R;
import com.example.user.myandroidapp.model.OwnedPokemonInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by user on 2016/8/25.
 */
public class PokemonListViewAdapter extends ArrayAdapter<OwnedPokemonInfo> implements OnPokemonSelectedChangeListener {

    public ArrayList<OwnedPokemonInfo> selectedPokemons = new ArrayList<>();
    public OnPokemonSelectedChangeListener pokemonSelectedChangeListener;
    LayoutInflater mInflater;
    int mRowLayoutId;

    public PokemonListViewAdapter(Context context,
                                  int resource,
                                  List<OwnedPokemonInfo> objects) {
        super(context, resource, objects);

        mRowLayoutId = resource;
        mInflater = LayoutInflater.from(context);
        ViewHolder.mAdapter = this;

    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {

        OwnedPokemonInfo data = getItem(position);
        ViewHolder viewHolder = null;

        if(rowView == null) {
            rowView = mInflater.inflate(mRowLayoutId, null); //generate view object based on mRowLayoutId
            viewHolder = new ViewHolder(rowView); //construct a viewHolder object and find sub UI component in rowView
            rowView.setTag(viewHolder); //cache viewHolder
        }
        else {
            viewHolder = (ViewHolder)rowView.getTag(); //retrieve previous cached viewHolder object
        }

        viewHolder.setView(data); //set data onto this rowView through viewHolder object

        return rowView;
    }

    @Override
    public void onSelectedChange(OwnedPokemonInfo ownedPokemonInfo) {
        if(ownedPokemonInfo.isSelected) {
            selectedPokemons.add(ownedPokemonInfo);
        }
        else {
            selectedPokemons.remove(ownedPokemonInfo);
        }

        if(pokemonSelectedChangeListener != null) {
            pokemonSelectedChangeListener.onSelectedChange(ownedPokemonInfo);
        }
    }

    public static class ViewHolder implements View.OnClickListener {

        View mRowView;
        ImageView mAppearanceImg;
        TextView mNameText;
        TextView mLevelText;
        TextView mCurrentHP;
        TextView mMaxHP;
        ProgressBar mHPBar;

        OwnedPokemonInfo mData;

        public static PokemonListViewAdapter mAdapter;

        public ViewHolder(View rowView) {
            mRowView = rowView;
            mAppearanceImg = (ImageView)rowView.findViewById(R.id.appearanceImg);
            mAppearanceImg.setOnClickListener(this);

            mNameText = (TextView)rowView.findViewById(R.id.nameText);
            mLevelText = (TextView)rowView.findViewById(R.id.levelText);
            mCurrentHP = (TextView)rowView.findViewById(R.id.currentHP);
            mMaxHP = (TextView)rowView.findViewById(R.id.maxHP);
            mHPBar = (ProgressBar)rowView.findViewById(R.id.hpBar);
        }

        //bind mRowView with data
        public void setView(OwnedPokemonInfo data) {
            mData = data;

            mRowView.setActivated(data.isSelected);

            mNameText.setText(data.name);
            mLevelText.setText(String.valueOf(data.level));
            mCurrentHP.setText(String.valueOf(data.currentHP));
            mMaxHP.setText(String.valueOf(data.maxHP));
            int progress = (int)((((float)data.currentHP) / data.maxHP) * 100);
            mHPBar.setProgress(progress);

            int pokemonId = data.pokemonId;
            String imgUrl = String.format(
                    "http://www.csie.ntu.edu.tw/~r03944003/listImg/%d.png",
                    pokemonId
            );

            ImageLoader.getInstance().displayImage(imgUrl, mAppearanceImg);

        }

        public void setSelected() {
            mData.isSelected = !mData.isSelected;
            mRowView.setActivated(mData.isSelected);
            mAdapter.onSelectedChange(mData);
        }

        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if(viewId == R.id.appearanceImg) {
                setSelected();
            }

        }
    }

    public OwnedPokemonInfo getItemWithName(String pokemonName) {
        for(int i = 0;i < getCount();i++) {
            OwnedPokemonInfo ownedPokemonInfo = getItem(i);
            if(ownedPokemonInfo.name.equals(pokemonName)) {
                return ownedPokemonInfo;
            }
        }

        return null;
    }

    public void update(OwnedPokemonInfo newData) {
        OwnedPokemonInfo oldData = getItemWithName(newData.name);
        oldData.skills = newData.skills;
        oldData.currentHP = newData.currentHP;
        oldData.maxHP = newData.maxHP;
        oldData.level = newData.level;
        oldData.type_1 = newData.type_1;
        oldData.type_2 = newData.type_2;

        notifyDataSetChanged(); //reflect changes on ListView
    }

}
