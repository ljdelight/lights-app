package com.ljdelight.lights;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ljdelight.lights.db.LightsDBHelper;
import com.ljdelight.lights.generated.Center;
import com.ljdelight.lights.generated.Location;
import com.ljdelight.lights.generated.TaggedLocationWithMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends Activity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private final String TAG = getClass().getSimpleName();
    public final static String LOCATION_ID = "com.ljdelight.lights.LOCATION_ID";


    private GoogleMap mMap;
    private HashMap<String, String> mMarkerToLocation = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
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
    public void onMapReady(final GoogleMap googleMap) {
        double lat = 37.6889;
        double lng = -97.3361;
        int radius = 65000;

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnInfoWindowClickListener(this);
        Center center = new Center(new Location(lat, lng)).setRadiusInMeters(radius);

        LocationFetchTask task = new LocationFetchTask(
                this,
                new LocationFetchTask.Listener() {
                    @Override
                    public void onFetchFinished(List<TaggedLocationWithMeta> locations) {
                        LatLngBounds.Builder boundBuilder = new LatLngBounds.Builder();

                        for (TaggedLocationWithMeta tag : locations) {
                            //Log.d(TAG, "Found " + tloc.meta.comments.size() + " comments");

                            double lat = tag.tag.location.getLat();
                            double lng = tag.tag.location.getLng();
                            int numComments = tag.meta.comments.size();
                            int numVotes = tag.tag.votes;

                            LatLng loc = new LatLng(lat, lng);
                            MarkerOptions options = new MarkerOptions()
                                    .position(loc)
                                    .title(String.format(Locale.US, "%d votes %d comments", numVotes, numComments));
                            Marker marker = mMap.addMarker(options);
                            mMarkerToLocation.put(marker.getId(), Long.toString(tag.tag.uid));
                            boundBuilder.include(loc);
                        }
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundBuilder.build(), 50));
                    }
                }
        );
        task.execute(center);
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(this, OneLocationActivity.class);
        String locationId = mMarkerToLocation.get(marker.getId());
        Log.d(TAG, "Sending intent id=" + locationId);
        intent.putExtra(MapsActivity.LOCATION_ID, locationId);
        startActivity(intent);
    }
}
