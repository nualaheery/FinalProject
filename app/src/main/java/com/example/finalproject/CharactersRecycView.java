package com.example.finalproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class CharactersRecycView extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener {

    public static final String BUNDLE_NAME = "monsterName";
    public static final String BUNDLE_IMAGE = "imageid";
    public static final String BUNDLE_DEBITCARD_NUMBER = "debitcardnumber";
    public static final String BUNDLE_CREDITCARD_NUMBER = "creditcardnumber";



    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<MonsterItem> monsterItems = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_recyc_view);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewCharacters);
        mRecyclerView.setHasFixedSize(true);

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new GridLayoutManager(this, 2);
        } else {
            mLayoutManager = new GridLayoutManager(this, 4);
        }

        mAdapter = new RecyclerViewAdapter(this, monsterItems);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(CharactersRecycView.this);


        loadMonstersFromJson();

    }

    public void loadMonstersFromJson() {
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.monsters);

        Scanner scanner = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }

        displayMonsters(builder.toString());

    }

    public void displayMonsters(String s) {

        StringBuilder builder = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();

        try {
            JSONObject root = new JSONObject(s);
            JSONArray jsonArray = root.getJSONArray("monsters");

            for (int i = 0; i < jsonArray.length(); i++) {
                String monsterName = jsonArray.getJSONObject(i).getString("name");
                String monsterImg = jsonArray.getJSONObject(i).getString("image_name");
                String monsterDebitCardNum = jsonArray.getJSONObject(i).getString("debit_card_number");
                String monsterCreditCardNum = jsonArray.getJSONObject(i).getString("credit_card_number");

                MonsterItem monster = new MonsterItem(monsterImg, monsterName, monsterDebitCardNum, monsterCreditCardNum);

                monsterItems.add(monster);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position) {
       /* MonsterDetailsFragment monsterDetailsFragment = new MonsterDetailsFragment();
        MonsterItem clickedItem = monsterItems.get(position);

        //change imagename to id so can pass through an int ID
        String imageName = clickedItem.getMonsterImageResouce();
        int imageResID = CharactersRecycView.this.getResources().getIdentifier(imageName, "drawable", CharactersRecycView.this.getPackageName());

        Bundle monsterdata = new Bundle();
        /*
        monsterdata.putString(BUNDLE_NAME, clickedItem.getNameText());
        monsterdata.putInt(BUNDLE_IMAGE, imageResID);
        monsterdata.putString(BUNDLE_DEBITCARD_NUMBER,clickedItem.getDebitCardNumber());
        monsterdata.putString(BUNDLE_CREDITCARD_NUMBER, clickedItem.getCreditCardNumber());



        monsterDetailsFragment.setArguments(monsterdata);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_characters, monsterDetailsFragment, null)
                .addToBackStack(null).commit();
    */

    Intent intent = new Intent (CharactersRecycView.this, MonsterDetails.class);
    Bundle bundle = new Bundle();
    bundle.putParcelable("Monster Item", monsterItems.get(position));
    intent.putExtras(bundle);
    startActivity(intent);


    }
}