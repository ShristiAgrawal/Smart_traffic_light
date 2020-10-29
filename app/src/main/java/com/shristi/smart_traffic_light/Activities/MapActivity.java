package com.shristi.smart_traffic_light.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
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
    private ArrayList<ArrayList<Double>> locations;
    private ArrayList<LatLng> list=new ArrayList<LatLng>();
    private RetrofitService service;
    PolylineOptions options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_login);
        service = RetrofitClient.getinstance().create(RetrofitService.class);

        Call<route_detail> call = service.getlocation(11.215,77.357,
                "puluapatti,Tiruppur,Tamil Nadu", "Token dbef64a307efc2df5a8cab4827a8a65833f1b5e6");

        call.enqueue(new Callback<route_detail>() {
            @Override
            public void onResponse(Call<route_detail> call, Response<route_detail> response) {
            locations=response.body().getLocations();
                LatLng latLng = new LatLng(locations.get(0).get(0),locations.get(0).get(1));
                float zoom = 17;
                // whatever
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
                for(int i=0;i<locations.size();i++){
            LatLng l=new LatLng(locations.get(i).get(0),locations.get(i).get(1));
            list.add(l);
                    map.addMarker(new MarkerOptions().position(l).title(""));

        }
                for (int i = 0; i < list.size() - 1; i++) {
                    LatLng src = list.get(i);
                    LatLng dest = list.get(i + 1);

                    Polyline line = map.addPolyline(
                            new PolylineOptions().add(
                                    new LatLng(src.latitude, src.longitude),
                                    new LatLng(dest.latitude,dest.longitude)
                            ).width(10).color(Color.BLUE).geodesic(true)
                    );
                }



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
        
    }
}
