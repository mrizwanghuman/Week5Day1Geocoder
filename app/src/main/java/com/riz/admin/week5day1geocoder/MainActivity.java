package com.riz.admin.week5day1geocoder;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.riz.admin.week5day1geocoder.rettrofilhelper.GoogleApiRetrofitHelper;
import com.riz.admin.week5day1geocoder.rettrofilhelper.mapdata.AddressComponent;
import com.riz.admin.week5day1geocoder.rettrofilhelper.mapdata.MapData;
import com.riz.admin.week5day1geocoder.rettrofilhelper.mapdata.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {
    protected Location mLastLocation;
    private AddressResultReceiver mResultReceiver;
    private static final String TAG = "MainActivity";
    private EditText etlat;
    private EditText etlog;
    List<Address> addressList;
    private TextView tvgetAddress;
    private Geocoder geocoder;
    private EditText etAddress;
    EditText etAddressGoogleGEO;
    TextView tvshowGoogleGeo;
    TextView tvshowGoogleGeoAddress;
private static final String KEY = "AIzaSyBmY_rRKvH1KcH1_gQ-uKxI4iEkUUZYbCo";

 List<Result> resultList;
 String addressComponentList;
    private List<com.riz.admin.week5day1geocoder.rettrofilhelper.mapdata.Location> locationList;
    private String searchAddress;
    private ArrayAdapter<String> googleGeocoderAdapter;
    private EditText etgetLat;
    private EditText etgetLog;
    private String getLat;
    private String getLong;
    private ArrayAdapter<String> addressListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etlat = findViewById(R.id.etlat);
        etlog = findViewById(R.id.etlog);
        tvgetAddress = findViewById(R.id.tvgetAddress);
        etAddress = findViewById(R.id.etAddres);
      //  startIntentService();
        etlat.setText("33.891469");
        etAddressGoogleGEO = findViewById(R.id.etAddressGoogleGEO);
        etlog.setText("-84.476816");
        tvshowGoogleGeo = findViewById(R.id.tvshowGoogleGeo);
        tvshowGoogleGeoAddress = findViewById(R.id.tvshowGoogleGeoAddress);
        etgetLat = findViewById(R.id.etGoogleLati);
        etgetLog = findViewById(R.id.etGoogleLangi);
        etgetLat.setText("33.891469");
        etgetLog.setText("-84.476816");

    }



    public void getAddres(View view) {

        String latString = etlat.getText().toString();
        String logString = etlog.getText().toString();
        double lat = Double.parseDouble(latString);
        double log = Double.parseDouble(logString);
        Log.d(TAG, "getAddres: "+latString + logString);
        geocoder = new Geocoder(this, Locale.getDefault());
        switch (view.getId()){
            case R.id.btnGetaddress:
        try {
            addressList = geocoder.getFromLocation(lat, log,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "getAddres: "+addressList.size());
        for (int i = 0; i < addressList.size(); i++) {
            Log.d(TAG, "getAddres: "+addressList.get(i).getAddressLine(i));
            tvgetAddress.setText(addressList.get(i).getAddressLine(i));
        }
break;
            case R.id.btngetlatlog:

                String addressString = etAddress.getText().toString();
                try {
                    addressList= geocoder.getFromLocationName(addressString, 4);
                    for (int i = 0; i < addressList.size(); i++) {
                        Log.d(TAG, "getAddres: "+addressList.get(i).getLongitude());
                    TextView tvShowLL = findViewById(R.id.tvShowLL);
                       double latkjk= addressList.get(i).getLatitude();
                       double logkjk = addressList.get(i).getLongitude();
                       String stringConvert = Double.toString(latkjk);
                       String stringLog = Double.toString(logkjk);
                    tvShowLL.setText(stringConvert+" "+ stringLog);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }

    public void useGoogleApi(View view) {
        searchAddress = etAddressGoogleGEO.getText().toString();
        googleGeocoderMethod(searchAddress, KEY);
    }
    private void googleGeocoderMethod(String address, String key) {
        GoogleApiRetrofitHelper.getMapResult(address, key).enqueue(new Callback<MapData>() {
            @Override
            public void onResponse(Call<MapData> call, Response<MapData> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body().getResults().size());

                    resultList=response.body().getResults();
List<Location> locationList = new ArrayList<>();
                    for (int i = 0; i < resultList.size(); i++) {
                        addressComponentList=resultList.get(i).getFormattedAddress();
                        Log.d(TAG, "onResponse: "+resultList.get(i).getGeometry().getLocation());
                        tvshowGoogleGeo.setText("Latitude"+resultList.get(i).getGeometry().getLocation().getLat().toString()+" Longitude " + resultList.get(i).getGeometry().getLocation().getLng());
                    }


                }
                else {
                    Log.d(TAG, "dontknow: ");
                }
            }

            @Override
            public void onFailure(Call<MapData> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    public void useGoogleApiAddress(View view) {


        getLat = etgetLat.getText().toString();
        getLong = etgetLog.getText().toString();
        String completeLongLat = getLat+","+getLong;
        GoogleApiRetrofitHelper.getAddressLatLog(completeLongLat, KEY).enqueue(new Callback<MapData>() {
            @Override
            public void onResponse(Call<MapData> call, Response<MapData> response) {
                //Log.d(TAG, "onResponse: "+response.body().getResults());
                resultList= response.body().getResults();
//String addresList = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,)

String addresList= "";
List<String> addresasList = new ArrayList<>();
                if (!resultList.isEmpty()){

                       // Log.d(TAG, "onResponse: "+resultList.get(i).getFormattedAddress());
                        addresList= resultList.get(0).getFormattedAddress();


                }
                //addressListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, addresList);
                tvshowGoogleGeoAddress.setText(addresList);

            }

            @Override
            public void onFailure(Call<MapData> call, Throwable t) {

            }
        });

    }

    public void gotoMapActivity(View view) {
        Intent intent = new Intent(this, MapLocationGoogle.class);
        this.startActivity(intent);
    }
}
