package com.shristi.smart_traffic_light.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shristi.smart_traffic_light.Api.RetrofitClient;
import com.shristi.smart_traffic_light.Api.RetrofitService;
import com.shristi.smart_traffic_light.Models.Api_response;
import com.shristi.smart_traffic_light.Models.location;
import com.shristi.smart_traffic_light.Models.route_detail;
import com.shristi.smart_traffic_light.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    private RetrofitService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_login);
        service = RetrofitClient.getinstance().create(RetrofitService.class);

        Call<route_detail> call = service.getlocation(11.215,77.357,
                "puluapatti,Tiruppur,Tamil Nadu","Token dbef64a307efc2df5a8cab4827a8a65833f1b5e6");

        call.enqueue(new Callback<route_detail>() {
            @Override
            public void onResponse(Call<route_detail> call, Response<route_detail> response) {
                Toast.makeText(MapActivity.this,"Response code is "+response.code(),Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<route_detail> call, Throwable t) {
                Toast.makeText(MapActivity.this,"Error occured",Toast.LENGTH_SHORT).show();
            }
        });
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        LatLng Raipur=new LatLng(21.253632, 81.635994);
        map.addMarker(new MarkerOptions().position(Raipur).title("Raipur"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Raipur));
    }
}
