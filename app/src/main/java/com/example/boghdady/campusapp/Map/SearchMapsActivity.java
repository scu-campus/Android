package com.example.boghdady.campusapp.Map;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.boghdady.campusapp.NavigationDrawer.DoctorNavigationDrawer;
import com.example.boghdady.campusapp.R;
import com.example.boghdady.campusapp.Registeration.DoctorSignupActivity;
import com.example.boghdady.campusapp.Retrofit.Interfaces;
import com.example.boghdady.campusapp.Retrofit.Models;
import com.example.boghdady.campusapp.Retrofit.Responses;
import com.example.boghdady.campusapp.Retrofit.SentBody;
import com.example.boghdady.campusapp.helper.Constants;
import com.example.boghdady.campusapp.helper.SharedPref;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    final int MY_LOCATION_REQUEST_CODE = 25;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker marker;
    private LatLng searchedPlace;
    ProgressDialog pDialog;
    ArrayList<LatLng> MarkerPoints;


    private static boolean foundLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        GetLatLongAndFacultyNameFromRetrofit(4);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    //    here to request the missing permissions, and then overriding
                    //    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        addMarker(30.62116, 32.2684);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_LOCATION_REQUEST_CODE);
            // Show rationale and request permission.
        }

    }


    private void addMarker(double lat, double lng) {

        if (mMap != null) {
            LatLng pos = new LatLng(lat, lng);
            searchedPlace = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(pos).title("Marker in Sydney"));
            CameraPosition cameraPosition = CameraPosition.fromLatLngZoom(searchedPlace, 15);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mMap.moveCamera(cameraUpdate);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {

            buildGoogleApiClient();
            mGoogleApiClient.connect();

        }
    }

    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        //mLocationRequest.setSmallestDisplacement(0.1F);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        //remove previous current location Marker
        if (marker != null) {
            marker.remove();
        }

        double dLatitude = mLastLocation.getLatitude();
        double dLongitude = mLastLocation.getLongitude();
        marker = mMap.addMarker(new MarkerOptions().position(new LatLng(dLatitude, dLongitude))
                .title("My Location").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        if (searchedPlace != null) {

            drawRoute(new LatLng(dLatitude, dLongitude), searchedPlace);
        }
        if (!foundLocation) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLatitude, dLongitude), 8));
            foundLocation = true;
        }
    }




    private void drawRoute(LatLng origin, LatLng dest) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(origin).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(dest).title("Marker in Sydney"));
        // Getting URL to the Google Directions API
        String url = getUrl(origin, dest);
        Log.d("onMapClick", url.toString());
        FetchUrl FetchUrl = new FetchUrl();

        // Start downloading json data from Google Directions API
        FetchUrl.execute(url);
    }







    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }




        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


//--------------------------------------------------------------------------------------------------------------------------------------------------

    void GetLatLongAndFacultyNameFromRetrofit(int ID){

                    pDialog = new ProgressDialog(SearchMapsActivity.this);
                    pDialog.setMessage(getString(R.string.please_wait));
                    pDialog.setCancelable(false);
                    pDialog.show();

                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    Interfaces.GetPlacesLocation getPlaceLocation=retrofit.create(Interfaces.GetPlacesLocation.class);
                    Call<Responses.LocationPlacesResponse> call=getPlaceLocation.getPlacesLoaction(new SentBody.InsertLocationID(ID));
                    call.enqueue(new Callback<Responses.LocationPlacesResponse>() {
                        @Override
                        public void onResponse(Call<Responses.LocationPlacesResponse> call, Response<Responses.LocationPlacesResponse> response) {

                            try {
                                if (response.body().getSuccess()==1){

                                    ArrayList<Models.LocationModel> placeLocation = response.body().getLocation();

                                    String FacultyName = placeLocation.get(0).getFaculty_Name();
                                    double lat = placeLocation.get(0).getLatitude();
                                    double lang= placeLocation.get(0).getLongitude();

                                    Toast.makeText(SearchMapsActivity.this,FacultyName +"  "+ lat+"  "+ lang , Toast.LENGTH_SHORT).show();

                                }
                                if (response.body().getSuccess()==0){
                                    Toast.makeText(SearchMapsActivity.this,R.string.wrong , Toast.LENGTH_LONG).show();
                                    pDialog.dismiss();
                                }
                                pDialog.dismiss();
                            }catch (Exception e){
                                e.getMessage();
                                pDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<Responses.LocationPlacesResponse> call, Throwable t) {
                            try {
                                Toast.makeText(SearchMapsActivity.this, R.string.check_your_internet, Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();
                            }catch (Exception e){
                                pDialog.dismiss();
                            }
                        }
                    });
    }

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    void AutoCompleteText(ArrayList<String> placeLocation){

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
    //    String[] countries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, placeLocation);
        textView.setAdapter(adapter);
    }
}
