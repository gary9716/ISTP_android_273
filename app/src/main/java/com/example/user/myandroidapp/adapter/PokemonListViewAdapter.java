package com.example.user.myandroidapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myandroidapp.R;
import com.example.user.myandroidapp.model.OwnedPokemonInfo;

import java.util.List;

/**
 * Created by user on 2016/8/25.
 */
public class PokemonListViewAdapter extends ArrayAdapter<OwnedPokemonInfo> {

    LayoutInflater mInflater;
    int mRowLayoutId;

    public PokemonListViewAdapter(Context context,
                                  int resource,
                                  List<OwnedPokemonInfo> objects) {
        super(context, resource, objects);

        mRowLayoutId = resource;
        mInflater = LayoutInflater.from(context);

    }


    public class ViewHolder {

        View mRowView;
        ImageView mAppearanceImg;
        TextView mNameText;
        TextView mLevelText;
        TextView mCurrentHP;
        TextView mMaxHP;
        ProgressBar mHPBar;

        public ViewHolder(View rowView) {
            mRowView = rowView;
            mAppearanceImg = (ImageView)rowView.findViewById(R.id.appearanceImg);
            mNameText = (TextView)rowView.findViewById(R.id.nameText);
            mLevelText = (TextView)rowView.findViewById(R.id.levelText);
            mCurrentHP = (TextView)rowView.findViewById(R.id.currentHP);
            mMaxHP = (TextView)rowView.findViewById(R.id.maxHP);
            mHPBar = (ProgressBar)rowView.findViewById(R.id.hpBar);
        }

        


    }

}
