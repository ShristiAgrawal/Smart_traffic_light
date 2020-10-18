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
import com.shristi.smart_traffic_light.Models.Hospital;
import com.shristi.smart_traffic_light.Models.RouteResponse;
import com.shristi.smart_traffic_light.Models.location;
import com.shristi.smart_traffic_light.Models.route_detail;
import com.shristi.smart_traffic_light.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    private RetrofitService service;
    final private String AUTH_TOKEN = "Token dbef64a307efc2df5a8cab4827a8a65833f1b5e6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_login);
        service = RetrofitClient.getinstance().create(RetrofitService.class);

        service.getlocation(11.215659, 77.357261, "puluapatti, Tiruppur, Tamil Nadu", AUTH_TOKEN).enqueue(new Callback<RouteResponse>() {
            @Override
            public void onResponse(Call<RouteResponse> call, Response<RouteResponse> response) {
                Logger.getAnonymousLogger().info(response.body().toString());
            }

            @Override
            public void onFailure(Call<RouteResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


        service.getAllHospitals(AUTH_TOKEN).enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse(Call<List<Hospital>> call, Response<List<Hospital>> response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<List<Hospital>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng Raipur = new LatLng(21.253632, 81.635994);
        map.addMarker(new MarkerOptions().position(Raipur).title("Raipur"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Raipur));
    }
}
