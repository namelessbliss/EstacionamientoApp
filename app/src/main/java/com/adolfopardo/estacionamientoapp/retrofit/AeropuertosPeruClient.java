package com.adolfopardo.estacionamientoapp.retrofit;


import com.adolfopardo.estacionamientoapp.Constants;
import com.adolfopardo.estacionamientoapp.NullOrEmptyConverterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AeropuertosPeruClient {

    // Instancia propia estatica
    private static AeropuertosPeruClient INSTANCE = null;

    private AeropuertosPeruService aeropuertosPeruService;
    private Retrofit retrofit;

    public AeropuertosPeruClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.APP_BASE_URL)
                .addConverterFactory(new NullOrEmptyConverterFactory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aeropuertosPeruService = retrofit.create(AeropuertosPeruService.class);
    }

    /**
     * Patron Singleton
     *
     * @return
     */
    public static AeropuertosPeruClient getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AeropuertosPeruClient();
        }
        return INSTANCE;
    }

    public AeropuertosPeruService getAeropuertosPeruService() {
        return aeropuertosPeruService;
    }
}
