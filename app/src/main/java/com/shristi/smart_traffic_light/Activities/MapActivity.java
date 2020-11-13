package com.shristi.smart_traffic_light.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    private Button search;
    private EditText address;
    GoogleMap map;
    private ArrayList<ArrayList<Double>> locations,hospitalWayPoints;
    private ArrayList<LatLng> list=new ArrayList<LatLng>();
    private ArrayList<LatLng> hospitalwaylist=new ArrayList<LatLng>();

    private RetrofitService service;
    PolylineOptions options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_login);
        search=findViewById(R.id.search);
      address=findViewById(R.id.emergencyaddress);
        search.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view) {

                service = RetrofitClient.getinstance().create(RetrofitService.class);

                Call<route_detail> call = service.getlocation( 11.215659,77.357261,
                        address.getText().toString(), "Token dbef64a307efc2df5a8cab4827a8a65833f1b5e6");

                call.enqueue(new Callback<route_detail>() {
                    @Override
                    public void onResponse(Call<route_detail> call, Response<route_detail> response) {
                        locations=response.body().getLocations();
                        hospitalWayPoints=response.body()
                                .getHospitalWayPoints();
                        LatLng latLng = new LatLng(locations.get(0).get(0),locations.get(0).get(1));
                        LatLng latLng2 = new LatLng(locations.get(locations.size()-1).get(0),locations.get(locations.size()-1).get(1));
                        LatLng latLng3 = new LatLng(hospitalWayPoints.get(hospitalWayPoints.size()-1).get(0),hospitalWayPoints.get(hospitalWayPoints.size()-1).get(1));


                        float zoom = 17;
                        // whatever
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
                        map.addMarker(new MarkerOptions().position(latLng).title("start").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        map.addMarker(new MarkerOptions().position(latLng2).title("dest").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        map.addMarker(new MarkerOptions().position(latLng3).title("hospilat").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                        for(int i=0;i<locations.size();i++){
                            LatLng l=new LatLng(locations.get(i).get(0),locations.get(i).get(1));
                            list.add(l);
                            //map.addMarker(new MarkerOptions().position(l).title(""));

                        }
                        for(int i=0;i<hospitalWayPoints.size();i++){
                            LatLng l=new LatLng(hospitalWayPoints.get(i).get(0),hospitalWayPoints.get(i).get(1));
                            hospitalwaylist.add(l);
                            //map.addMarker(new MarkerOptions().position(l).title(""));

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
                        for (int i = 0; i < hospitalwaylist.size() - 1; i++) {
                            LatLng src = hospitalwaylist.get(i);
                            LatLng dest = hospitalwaylist.get(i + 1);

                            Polyline line = map.addPolyline(
                                    new PolylineOptions().add(
                                            new LatLng(src.latitude, src.longitude),
                                            new LatLng(dest.latitude,dest.longitude)
                                    ).width(10).color(Color.RED).geodesic(true)
                            );
                        }



                    }

                    @Override
                    public void onFailure(Call<route_detail> call, Throwable t) {
                        Toast.makeText(MapActivity.this,"error",Toast.LENGTH_SHORT).show();
                    }
                });

                SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapActivity.this);
          }
});


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        
    }
}
