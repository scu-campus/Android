package com.example.boghdady.campusapp;

import android.content.Context;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }
     class MyWindow implements GoogleMap.InfoWindowAdapter {
        private final View myContentsView;

        MyWindow(){
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_window, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            TextView tvTitle = (TextView)myContentsView.findViewById(R.id.title);
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = (TextView)myContentsView.findViewById(R.id.snippet);
            tvSnippet.setText(marker.getSnippet());
            return myContentsView;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }

    GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    int PLACE_PICKER_REQUEST = 1;
    private static final String TAG = MapsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));

        if (!success) {
            Log.e(TAG, "Style parsing failed.");
        }
        // Position the map's camera near Melbourne, Australia.
        LatLng MELBOURNE = new LatLng(30.623109, 32.2729409);
        Marker mark = mMap.addMarker(new MarkerOptions()
                .position(MELBOURNE));
        mMap.setInfoWindowAdapter(new MyWindow());
        mark.setTitle("Window");
        mark.setSnippet("My new Window");

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(MELBOURNE));

}
}
