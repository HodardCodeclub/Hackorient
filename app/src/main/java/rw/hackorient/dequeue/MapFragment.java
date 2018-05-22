package rw.hackorient.dequeue;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


public class MapFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final String TAG = "MapFragment";
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int[] COLORS = new int[]{R.color.colorPrimaryDark,R.color.colorPrimary,R.color.colorAccent,R.color.colorRouteLine};
    HackorientApi mHackorientApi;
    ArrayList<LatLng> waypoints;
    Observable<ResponseBody> waypointsObservable;
    private GoogleMap mMap;
    private MapView mapView;
    private GoogleApiClient apiClient;
    public LatLng mStartingPoint;
    private List<Polyline> polylines;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        waypoints = new ArrayList<>();
        mHackorientApi = HackorientApi.getInstance();
        InitBusStops();
    }

    private void InitBusStops() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map_fragment, container, false);
        try {
            MapsInitializer.initialize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(googleMap -> {
            mMap = googleMap;
            mMap.getUiSettings().setMapToolbarEnabled(false);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            //some code for getting  last known locations

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener((Activity) getContext(), location -> {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
//                            Toasty.info(getContext(),"I GOT IT!",Toast.LENGTH_LONG,true).show();
                            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
                        }
                    });

//                            MarkerOptions options = new MarkerOptions();
//                            options.position(waypoints.get(a));
//                            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
//                            options.alpha(0.7f);
//                            options.title("BUS STOP :"+a);
//                            mMap.addMarker(options);
                            //drawing route on map
            drawRoutesOnMap();
            //checks if dragged marker is starting points marker and set new location to starting points
            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    mStartingPoint = marker.getPosition();
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                    //checks if waypoints is greater than destinations only
                    if(waypoints.size() > 1){
                        //override starting point LatLong
                        waypoints.add(0,mStartingPoint);
                        drawRoutesOnMap();
                    }
                }
            });
            InitLastlocation();
            AccessGoogleAPIClient();
        });
        requestPermission();

        return view;
    }

    private void drawRoutesOnMap() {

        if(polylines != null && polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

//        Routing routing = new Routing.Builder()
//                .travelMode(AbstractRouting.TravelMode.DRIVING)
//                .withListener(new RoutingListener() {
//                    @Override
//                    public void onRoutingFailure(RouteException e) {
//                        Log.e(TAG, "onRoutingFailure: "+e.getMessage());
//                    }
//
//                    @Override
//                    public void onRoutingStart() {
//                        Log.i(TAG, "onRoutingStart ..");
//                    }
//
//                    @Override
//                    public void onRoutingSuccess(ArrayList<Route> route, int i) {
//                        //checks if polylines is not initialized and initialize it
//                        if(polylines == null){
//                            polylines = new ArrayList<>();
//                        }
//
//                        CameraUpdate center = CameraUpdateFactory.newLatLng(waypoints.get(0));
//                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
//                        mMap.moveCamera(center);
//                        //add route(s) to the map.
//                        for (int j = 0; j <route.size(); j++) {
//
//                            //In case of more than 5 alternative routes
//                            int colorIndex = j % COLORS.length;
//
//                            PolylineOptions polyOptions = new PolylineOptions();
//                            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
//                            polyOptions.width(10 + j * 3);
//                            polyOptions.addAll(route.get(j).getPoints());
//                            Polyline mPolyline = mMap.addPolyline(polyOptions);
//                            polylines.add(mPolyline);
//                            Log.d(TAG,"Route "+ (j+1) +": distance - "+ route.get(j).getDistanceValue()+": duration - "+ route.get(j).getDurationValue());
//                        }
//                        mMap.animateCamera(zoom);
//                        // End marker
//                        MarkerOptions options = new MarkerOptions();
//                        options.position(waypoints.get(route.size()));
//                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.destination_marker));
//                        mMap.addMarker(options);
//                    }
//
//                    @Override
//                    public void onRoutingCancelled() {
//                        Log.i(TAG, "onRoutingCancelled .....");
//                    }
//                })
//                .waypoints(waypoints)
//                .key(getString(R.string.google_maps_key))
//                .build();
//        routing.execute();
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_map_pin);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public  void saveMapLastLocation(GoogleMap gmap){
        Hawk.put("last_known_location",gmap.getCameraPosition());
    }

    private void AccessGoogleAPIClient() {
        apiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        apiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, request, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if(polylines == null){
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                LatLng latLng = new LatLng(latitude, longitude);
                if(mStartingPoint == null){
                    mStartingPoint = latLng;
                }
                mMap.addMarker(new MarkerOptions().position(mStartingPoint).icon(BitmapDescriptorFactory .fromResource(R.drawable.starting_map_pin)).draggable(true));
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 16);
                mMap.animateCamera(update);
                //then its means no routes drawn yet the draw it
                if(waypoints.size() > 1){
                    //override starting point LatLong
                    waypoints.add(0,mStartingPoint);
                    drawRoutesOnMap();
                }

            }
        } else{
            Toast.makeText(getContext(), "Failed to retrieve your new location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        if(mMap != null){
            saveMapLastLocation(mMap);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        InitLastlocation();
    }

    private void InitLastlocation() {
        if(mMap != null){
            if(Hawk.contains("last_known_location")){
                CameraPosition mPosition = Hawk.get("last_known_location");
                CameraUpdate mCamera = CameraUpdateFactory.newCameraPosition(mPosition);
                mMap.moveCamera(mCamera);
            }
        }
    }

    @Override
    public void onDestroy() {
        if(mMap != null){
            saveMapLastLocation(mMap);
        }
        mapView.onDestroy();
        DisposableManager.dispose();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        if(mMap != null){
            saveMapLastLocation(mMap);
        }
        mapView.onLowMemory();
        DisposableManager.dispose();
        super.onLowMemory();
    }


    //permission request

    public void requestPermission() {
        Dexter.withActivity(getActivity()).withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.isAnyPermissionPermanentlyDenied())
                    showSettingsDialog();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).onSameThread().check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Need Permissions");
        builder.setMessage("Allow some permission for the application to run properly");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}

