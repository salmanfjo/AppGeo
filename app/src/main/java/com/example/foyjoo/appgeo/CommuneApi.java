package com.example.foyjoo.appgeo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by foyjoo on 22/06/2019.
 */

public interface CommuneApi {

    @GET("communes")
    Call <List<Commune>> getCommunes(@Query("nom") String noms);

    @GET("communes/{code}")
    Call <Commune> getCommune(@Path("code") String code);
}
