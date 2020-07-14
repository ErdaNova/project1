

package com.example.myapplication.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class FragMap extends Fragment
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private MapView mapView = null;
    private UiSettings uiSettings;
    private static final String SELECTED_STYLE = "selected_style";
    Button style;
    Button marker_control;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private int count=0;
    private int count2=0;


    private static final String TAG=FragMap.class.getSimpleName();

    private GoogleMap map=null;

    private int mSelectedStyleId = R.string.style_label_default;


    private int mStyleIds[] = {
            R.string.style_label_retro,
            R.string.style_label_night,
            R.string.style_label_grayscale,
            R.string.style_label_default
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.frag_map, container, false);
        setHasOptionsMenu(true);
        mapView = (MapView)layout.findViewById(R.id.map);
        mapView.getMapAsync(this);
        style=(Button) layout.findViewById(R.id.button);
        style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedStyle(count);
                count=count+1;
            }
        });


        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
        if (savedInstanceState != null) {
            mSelectedStyleId = savedInstanceState.getInt(SELECTED_STYLE);
        }
    }





    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        final Geocoder g = new Geocoder(getContext());
        LatLng Vancouver = new LatLng(49.2827291, -123.1207375);
        List<Address> addresses = null;







        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Vancouver);
        markerOptions.title("벤쿠버");
        markerOptions.snippet("도시");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Vancouver));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        uiSettings = map.getUiSettings();
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableMyLocation();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        setSelectedStyle(0);
        setHasOptionsMenu(true);

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {



                try{
                    googleMap.clear();
                    List<Address> resultList = g.getFromLocation(point.latitude,point.longitude,10);
                    Log.d(TAG,resultList.get(1).getAddressLine(0));
                    MarkerOptions mOptions = new MarkerOptions();

                    mOptions.title("마커좌표");

                    mOptions.snippet(resultList.get(0).getAddressLine(0).toString());
                    mOptions.position(new LatLng(point.latitude,point.longitude));

                    googleMap.addMarker(mOptions);

                } catch (IOException e) {
                    googleMap.clear();
                    e.printStackTrace();
                    MarkerOptions mOptions = new MarkerOptions();

                    mOptions.title("마커좌표");
                    Double latitude = point.latitude;
                    Double longitude = point.longitude;


                    mOptions.snippet(latitude.toString()+','+longitude.toString());
                    mOptions.position(new LatLng(point.latitude,point.longitude));

                    googleMap.addMarker(mOptions);
                }


            }
        });
    }






    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
            }
        }
    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this.getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this.getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    private void setSelectedStyle(int count) {
        MapStyleOptions style;
        switch (count%4) {

            case 1:
                // Sets the night style via raw resource JSON.
                style = MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.mapstyle_retro);

                break;
            case 2:
                // Sets the grayscale style via raw resource JSON.
                style = MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.mapstyle_night);
                break;
            case 3:
                style = MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.mapstyle_grayscale);
                break;
            case 0:
                // Removes previously set style, by setting it to null.
                style = null;
                break;
            default:
                return;
        }
        map.setMapStyle(style);
    }




    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    public static FragMap newinstance() {
        FragMap fragMap = new FragMap();
        return fragMap;
    }

}