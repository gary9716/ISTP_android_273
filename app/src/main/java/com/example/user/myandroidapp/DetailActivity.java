package com.example.user.myandroidapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myandroidapp.model.OwnedPokemonInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by user on 2016/8/26.
 */
public class DetailActivity extends CustomizedActivity {

    public final static int savePokemonIntoComputer = 1;
    //add a new result code here and set the result code in onOptionsItemSelected

    OwnedPokemonInfo ownedPokemonInfo;
    TextView levelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent srcIntent = getIntent();
        ownedPokemonInfo = srcIntent.getParcelableExtra(PokemonListActivity.ownedPokemonInfoKey);

        setUI(ownedPokemonInfo);
    }

    void setUI(OwnedPokemonInfo ownedPokemonInfo) {
        setContentView(R.layout.activity_detail);

        ((TextView)findViewById(R.id.nameText)).setText(ownedPokemonInfo.name);
        levelText = (TextView)findViewById(R.id.levelText);
        levelText.setText(String.valueOf(ownedPokemonInfo.level));
        ((TextView)findViewById(R.id.currentHP)).setText(String.valueOf(ownedPokemonInfo.currentHP));
        ((TextView)findViewById(R.id.maxHP)).setText(String.valueOf(ownedPokemonInfo.maxHP));
        int progress = (int)((((float)ownedPokemonInfo.currentHP)/ownedPokemonInfo.maxHP) * 100);
        ((ProgressBar)findViewById(R.id.hpBar)).setProgress(progress);

        TextView type1Text = (TextView)findViewById(R.id.type1Text);
        if(ownedPokemonInfo.type_1 != -1) {
            type1Text.setText(OwnedPokemonInfo.typeNames[ownedPokemonInfo.type_1]);
        }
        else {
            type1Text.setText("");
        }

        TextView type2Text = (TextView)findViewById(R.id.type2Text);
        if(ownedPokemonInfo.type_2 != -1) {
            type2Text.setText(OwnedPokemonInfo.typeNames[ownedPokemonInfo.type_2]);
        }
        else {
            type2Text.setText("");
        }

        Resources resources = this.getResources();

        for(int i = 1;i <= OwnedPokemonInfo.maxNumSkills;i++) {
            int skillResId = resources.getIdentifier(String.format("skill%dText", i), "id", getPackageName());
            TextView skillTextView = ((TextView) findViewById(skillResId));
            if(ownedPokemonInfo.skills[i - 1] != null) {
                skillTextView.setText(ownedPokemonInfo.skills[i - 1]);
            }
            else {
                skillTextView.setText("");
            }
        }

        String url = String.format("http://www.csie.ntu.edu.tw/~r03944003/detailImg/%d.png", ownedPokemonInfo.pokemonId);
        ImageLoader.getInstance().displayImage(url, (ImageView) findViewById(R.id.appearanceImg));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_save) {
            Intent intent = new Intent();
            intent.putExtra(OwnedPokemonInfo.nameKey, ownedPokemonInfo.name);
            setResult(savePokemonIntoComputer, intent);
            finish();

            return true;
        }
        else if(itemId == R.id.action_level_up) {
            //HW2
            //update levelText this TextView and pass ownedPokemonInfo back to PokemonListActivity

            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

}
