package com.example.foyjoo.appgeo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommuneActivity extends AppCompatActivity {
    CommuneApi communeApi;
    private TextView infosCommune;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commune);

        Intent intent = getIntent();
        if (intent != null) {
            String str = "";
            if (intent.hasExtra("editText")) {
                str = intent.getStringExtra("editText");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://geo.api.gouv.fr")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                communeApi = retrofit.create(CommuneApi.class);
                getCommunes(str);
            }
        }
    }

    private void getCommunes(String str) {
        infosCommune = (TextView) findViewById(R.id.infosCommune);
        final ListView communeList = (ListView) findViewById(R.id.communeList);


        Call<List<Commune>> call = communeApi.getCommunes(str);

        call.enqueue(new Callback<List<Commune>>() {
            @Override
            public void onResponse(Call<List<Commune>> call, Response<List<Commune>> response) {
                if (!response.isSuccessful()) {
                    infosCommune.setText("code : " + response.code());
                    return;
                }

                List<Commune> communes = response.body();

                ArrayList<String> list = new ArrayList<>();
                HashMap<String, String> map;
                ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();


                for (Commune commune : communes) {
                    map = new HashMap<String, String>();
                    map.put("nom", commune.getNom());
                    map.put("code", commune.getCode());
                    listItem.add(map);

                }
                SimpleAdapter mSchedule = new SimpleAdapter(CommuneActivity.this, listItem, R.layout.activity_commune, new String[]{"nom"}, new int[]{R.id.infosCommune});
                communeList.setAdapter(mSchedule);
                communeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, String> map = (HashMap<String, String>) communeList.getItemAtPosition(position);
                        String communeSelectedCode = map.get("code");
                        Log.v(map.get("nom"), "Item selected");
                        Log.v(map.get("code"), "Item selected code");
                        Intent communeInfosActivity = new Intent(getApplicationContext(), CommuneInfos.class);
                        communeInfosActivity.putExtra("communeSelectedCode", communeSelectedCode);
                        startActivity(communeInfosActivity);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Commune>> call, Throwable t) {
                TextView infosCommune = (TextView) findViewById(R.id.infosCommune);
                infosCommune.setText(t.getMessage());
                infosCommune.append(" ERROR ");

            }
        });
    }

}
