package com.riz.admin.week5day1geocoder.rettrofilhelper;

import com.riz.admin.week5day1geocoder.rettrofilhelper.mapdata.MapData;


import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Query;

/**
 * Created by  Admin on 12/4/2017.
 */
//key=AIzaSyBmY_rRKvH1KcH1_gQ-uKxI4iEkUUZYbCo
public interface GoogleMapServiceRet {
    @GET("json?")
    Call<MapData>getAddressData(@Query("address") String address, @Query("key") String key);

    @GET("json?")
    Call<MapData>getAddresUsingLang(@Query("latlng") String langitude, @Query("key") String key);
}
