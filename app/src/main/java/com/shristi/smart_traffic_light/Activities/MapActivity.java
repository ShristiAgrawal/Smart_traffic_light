package com.shristi.smart_traffic_light.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private Button search;
    private EditText address;
    GoogleMap map;
    private Marker marker;
    private int index,next;
    private float v;
    private double lat,lng;
    private Handler handler;
    private ArrayList<location> destinationsignals,hospitalsignals;
    private LatLng startPosition, endPosition;
    private int currhospital;
    private ArrayList<ArrayList<Double>> locations, hospitalWayPoints;
    private ArrayList<LatLng> list = new ArrayList<LatLng>();
    private ArrayList<LatLng> hospitalwaylist = new ArrayList<LatLng>();
    private RetrofitService service;
    PolylineOptions options;
    private Bitmap BitMapMarker,marker1,marker2;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_login);
        search = findViewById(R.id.search);
        address = findViewById(R.id.emergencyaddress);
        currhospital=0;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.car_marker);
        Bitmap b = bitmapdraw.getBitmap();
        BitMapMarker= Bitmap.createScaledBitmap(b, 110, 60, false);
        BitmapDrawable draw = (BitmapDrawable) getResources().getDrawable(R.drawable.red_abcd);
        Bitmap b2 = draw.getBitmap();
        marker1= Bitmap.createScaledBitmap(b2, 60, 60, false);
        BitmapDrawable draw2 = (BitmapDrawable) getResources().getDrawable(R.drawable.abcd);
        Bitmap b3 = draw2.getBitmap();
        marker2= Bitmap.createScaledBitmap(b3, 60, 60, false);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                service = RetrofitClient.getinstance().create(RetrofitService.class);

                Call<route_detail> call = service.getlocation(11.215659, 77.357261,
                        address.getText().toString(), "Token dbef64a307efc2df5a8cab4827a8a65833f1b5e6");

                call.enqueue(new Callback<route_detail>() {
                    @Override
                    public void onResponse(Call<route_detail> call, Response<route_detail> response) {
                        destinationsignals=response.body().getDestinationSignals();
                        hospitalsignals=response.body().getHospitalSignals();

                        locations = response.body().getLocations();
                        hospitalWayPoints = response.body()
                                .getHospitalWayPoints();
                        final LatLng latLng = new LatLng(locations.get(0).get(0), locations.get(0).get(1));
                        LatLng latLng2 = new LatLng(locations.get(locations.size() - 1).get(0), locations.get(locations.size() - 1).get(1));
                        LatLng latLng3 = new LatLng(hospitalWayPoints.get(hospitalWayPoints.size() - 1).get(0), hospitalWayPoints.get(hospitalWayPoints.size() - 1).get(1));


                        float zoom = 17;
                        // whatever
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
                        map.addMarker(new MarkerOptions().position(latLng).title("start").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        map.addMarker(new MarkerOptions().position(latLng2).title("dest").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        map.addMarker(new MarkerOptions().position(latLng3).title("hospilat").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                        for (int i = 0; i < locations.size(); i++) {
                            LatLng l = new LatLng(locations.get(i).get(0), locations.get(i).get(1));
                            list.add(l);


                        }
                        for (int i = 0; i < hospitalWayPoints.size(); i++) {
                            LatLng l = new LatLng(hospitalWayPoints.get(i).get(0), hospitalWayPoints.get(i).get(1));
                            hospitalwaylist.add(l);
                            //map.addMarker(new MarkerOptions().position(l).title(""));

                        }
                        Polyline bline = null;
                        for (int i = 0; i < list.size() - 1; i++) {
                            LatLng src = list.get(i);
                            LatLng dest = list.get(i + 1);


                            bline = map.addPolyline(
                                    new PolylineOptions().add(
                                            new LatLng(src.latitude, src.longitude),
                                            new LatLng(dest.latitude, dest.longitude)
                                    ).width(10).color(Color.BLUE).geodesic(true)
                            );
                        }
                        for(int i=0;i<destinationsignals.size();i++){
                            LatLng src = new LatLng(destinationsignals.get(i).getLat(),destinationsignals.get(i).getLng());
                            LatLng dest;
                            if(i!=destinationsignals.size()-1)
                            dest = new LatLng(destinationsignals.get(i+1).getLat(),destinationsignals.get(i+1).getLng());
                            else dest=list.get(list.size()-1);
                            map.addMarker(new MarkerOptions()
                                    .position(src)
                                    .icon(BitmapDescriptorFactory.fromBitmap(marker1))
                                    .rotation(90+getBearing(src,dest)));
                            map.addMarker(new MarkerOptions()
                                    .position(src)
                                    .icon(BitmapDescriptorFactory.fromBitmap(marker1))
                                    .rotation(270+getBearing(src,dest)));
                            map.addMarker(new MarkerOptions()
                                    .position(src)
                                    .icon(BitmapDescriptorFactory.fromBitmap(marker2))
                                    .rotation(getBearing(src,dest)));
                            map.addMarker(new MarkerOptions()
                                    .position(src)
                                    .icon(BitmapDescriptorFactory.fromBitmap(marker2))
                                    .rotation(180+getBearing(src,dest)));

                        }
//                        for(int i=0;i<hospitalsignals.size();i++){
//                            LatLng src = new LatLng(hospitalsignals.get(i).getLat(),hospitalsignals.get(i).getLng());
//                            LatLng dest;
//                            if(i!=hospitalsignals.size()-1)
//                                dest = new LatLng(hospitalsignals.get(i+1).getLat(),hospitalsignals.get(i+1).getLng());
//                            else dest=hospitalwaylist.get(hospitalwaylist.size()-1);
//                            map.addMarker(new MarkerOptions()
//                                    .position(src)
//                                    .icon(BitmapDescriptorFactory.fromBitmap(marker2))
//                                    .rotation(90+getBearing(src,dest)));
//                            map.addMarker(new MarkerOptions()
//                                    .position(src)
//                                    .icon(BitmapDescriptorFactory.fromBitmap(marker2))
//                                    .rotation(270+getBearing(src,dest)));
//                            map.addMarker(new MarkerOptions()
//                                    .position(src)
//                                    .icon(BitmapDescriptorFactory.fromBitmap(marker1))
//                                    .rotation(getBearing(src,dest)));
//                            map.addMarker(new MarkerOptions()
//                                    .position(src)
//                                    .icon(BitmapDescriptorFactory.fromBitmap(marker1))
//                                    .rotation(180+getBearing(src,dest)));
//
//                        }

                        // Animation
                        final ValueAnimator polylineAnimator = ValueAnimator.ofInt(0,100);
                        polylineAnimator.setDuration(2000);
                        polylineAnimator.setInterpolator(new LinearInterpolator());

                        final Polyline finalBline = bline;
                        polylineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                List<LatLng> points= finalBline.getPoints();
                                int percentValue=(int)valueAnimator.getAnimatedValue();
                                int size=points.size();
                                int newPoints=(int) (size*(percentValue/100.0f));
                                List<LatLng> p=points.subList(0,newPoints);
                                finalBline.setPoints(p);



                            }
                        });
                        polylineAnimator.start();
//

                        marker=map.addMarker(new MarkerOptions().position(latLng).flat(true)
                                .icon(BitmapDescriptorFactory.fromBitmap(BitMapMarker)));


                         handler=new Handler();
                        index=-1; next=-1;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(index<list.size()-1){
                                    index++;
                                    next=index+1;

                                }
                                if(index<list.size()-1){
                                    startPosition=list.get(index);
                                    endPosition=list.get(next);

                                }
                                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
                                valueAnimator.setDuration(3000);
                                valueAnimator.setInterpolator(new LinearInterpolator());


                                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        v=valueAnimator.getAnimatedFraction();
                                        lng=v*endPosition.longitude+(1-v)*startPosition.longitude;
                                        lat=v*endPosition.latitude+(1-v)*startPosition.latitude;
                                        if(currhospital<destinationsignals.size() && destinationsignals.get(currhospital).getLat()>lat && destinationsignals.get(currhospital).getLng()>lng)
                                        {
                                            service.turnlightsnormal(destinationsignals.get(currhospital).getId()).enqueue(new Callback() {
                                                @Override
                                                public void onResponse(Call call, Response response) {
                                                    Toast.makeText(MapActivity.this,"success",Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onFailure(Call call, Throwable t) {
                                                    Toast.makeText(MapActivity.this,"fail",Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                            currhospital++;
                                        }
                                        LatLng newPos=new LatLng(lat,lng);
                                        marker.setPosition(newPos);
                                        marker.setAnchor(0.5f,0.5f);
                                        marker.setRotation(getBearing(startPosition,newPos));
                                        map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                        .target(newPos).zoom(15.5f).build()));



                                    }
                                });
                                valueAnimator.start();
                                handler.postDelayed(this,3000);
                            }
                        },3000);


                         for (int i = 0; i < hospitalwaylist.size() - 1; i++) {
                            LatLng src = hospitalwaylist.get(i);
                            LatLng dest = hospitalwaylist.get(i + 1);

                             Polyline line = map.addPolyline(
                                    new PolylineOptions().add(
                                            new LatLng(src.latitude, src.longitude),
                                            new LatLng(dest.latitude, dest.longitude)
                                    ).width(10).color(Color.RED).geodesic(true)
                            );
                        }




                    }

                    @Override
                    public void onFailure(Call<route_detail> call, Throwable t) {
                        Toast.makeText(MapActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapActivity.this);



            }
        });


    }

    private float getBearing(LatLng startPosition, LatLng newPos) {
        double lat=Math.abs(startPosition.latitude-newPos.latitude);
        double lng=Math.abs(startPosition.longitude-newPos.longitude);
        if(startPosition.latitude<newPos.latitude && startPosition.longitude<newPos.longitude)
            return (float) (Math.toDegrees(Math.atan(lng/lat))+270);
        else if(startPosition.latitude>=newPos.latitude && startPosition.longitude<newPos.longitude)
            return (float) (90-Math.toDegrees(Math.atan(lng/lat))+180);
        else if(startPosition.latitude>=newPos.latitude && startPosition.longitude>=newPos.longitude)
            return (float) (90-Math.toDegrees(Math.atan(lng/lat))+180);
        else if(startPosition.latitude<newPos.latitude && startPosition.longitude>=newPos.longitude)
            return (float) (90-Math.toDegrees(Math.atan(lng/lat)));
        return -1;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;


    }
}
