package com.riz.admin.week5day1geocoder.rettrofilhelper;

import com.riz.admin.week5day1geocoder.rettrofilhelper.mapdata.AddressComponent;
import com.riz.admin.week5day1geocoder.rettrofilhelper.mapdata.MapData;
import com.riz.admin.week5day1geocoder.rettrofilhelper.mapdata.Result;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by  Admin on 12/4/2017.
 */

public class GoogleApiRetrofitHelper {

    public static final String baseUrl ="https://maps.googleapis.com/maps/api/geocode/";
    private static OkHttpClient httpClientConfig(HttpLoggingInterceptor interceptor){
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();



    }
    private static HttpLoggingInterceptor loggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return  httpLoggingInterceptor;
    }
    public static Retrofit retrofitCong() {
        return new Retrofit.Builder().baseUrl(baseUrl).client(httpClientConfig(loggingInterceptor())).addConverterFactory(GsonConverterFactory.create()).build();

    }

    public static Call<MapData> getMapResult(String address, String key){
        Retrofit retrofit = retrofitCong();
        GoogleMapServiceRet googleMapServiceRet = retrofit.create(GoogleMapServiceRet.class);
        return googleMapServiceRet.getAddressData(address, key);
    }

    public static Call<MapData> getAddressLatLog(String lat, String key){
        Retrofit retrofit = retrofitCong();
        GoogleMapServiceRet googleMapServiceRet= retrofit.create(GoogleMapServiceRet.class);
        return googleMapServiceRet.getAddresUsingLang(lat, key);
    }
}
