package com.example.user.myandroidapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public class ViewHolder {

        View mRowView;
        ImageView mAppearanceImg;
        TextView mNameText;
        TextView mLevelText;
        TextView mCurrentHP;
        TextView mMaxHP;
        ProgressBar mHPBar;

        OwnedPokemonInfo mData;

        public ViewHolder(View rowView) {
            mRowView = rowView;
            mAppearanceImg = (ImageView)rowView.findViewById(R.id.appearanceImg);
            mNameText = (TextView)rowView.findViewById(R.id.nameText);
            mLevelText = (TextView)rowView.findViewById(R.id.levelText);
            mCurrentHP = (TextView)rowView.findViewById(R.id.currentHP);
            mMaxHP = (TextView)rowView.findViewById(R.id.maxHP);
            mHPBar = (ProgressBar)rowView.findViewById(R.id.hpBar);
        }

        //bind mRowView with data
        public void setView(OwnedPokemonInfo data) {
            mData = data;

            mNameText.setText(data.name);
            mLevelText.setText(String.valueOf(data.level));
            mCurrentHP.setText(String.valueOf(data.currentHP));
            mMaxHP.setText(String.valueOf(data.maxHP));
            int progress = (int)((((float)data.currentHP) / data.maxHP) * 100);
            mHPBar.setProgress(progress);

            //TODO: load image through library from Internet

        }


    }

}
