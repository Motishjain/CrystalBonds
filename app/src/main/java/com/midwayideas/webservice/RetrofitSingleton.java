package com.midwayideas.webservice;

import com.midwayideas.constants.AppConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 3/23/2016.
 */
public class RetrofitSingleton {

    static RestEndpointInterface restEndpointInterface;

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restEndpointInterface = retrofit.create(RestEndpointInterface.class);
    }

    public static RestEndpointInterface newInstance(){
        return restEndpointInterface;
    }
}
