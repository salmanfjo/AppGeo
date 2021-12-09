package com.example.foyjoo.appgeo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by foyjoo on 16/07/2019.
 */

public class CommuneInfos extends AppCompatActivity {
    CommuneApi communeApi;
    TextView infosCommune;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos_commune);


        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("communeSelectedCode")) {
                String communeSelected = intent.getStringExtra("communeSelectedCode");
                Log.v(communeSelected, "nom de commune recu");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://geo.api.gouv.fr")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                communeApi = retrofit.create(CommuneApi.class);
                getCommune(communeSelected);
            }
        }
    }

    private void getCommune(String communeSelected) {
        infosCommune = (TextView) findViewById(R.id.infos_commune);

        Call<Commune> call = communeApi.getCommune(communeSelected);

        call.enqueue(new Callback <Commune>() {
            @Override
            public void onResponse(Call<Commune> call, Response<Commune> response) {
                if (!response.isSuccessful()) {
                    infosCommune.setText("code : " + response.code());
                    return;
                }

                Commune commune = response.body();


                String nom = commune.getNom();
                String code = commune.getCode();
                String codeDepartement = commune.getCodeDepartement();
                int population = commune.getPopulation();
                String codeRegion = commune.getCodeRegion();

                infosCommune.setText("Nom : " + nom + "\n"
                        + "Code : " + code + "\n"
                        + "Code Département : " + codeDepartement + '\n'
                        + "Code Région : " + codeRegion + "\n"
                        + "Population : " + population);
            }


            @Override
            public void onFailure(Call <Commune> call, Throwable t) {
                TextView infosCommune = (TextView) findViewById(R.id.infos_commune);
                infosCommune.setText(t.getMessage());
                infosCommune.append(" ERROR ");

            }
        });
    }
}
