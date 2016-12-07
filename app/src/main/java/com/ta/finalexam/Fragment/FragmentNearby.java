package com.ta.finalexam.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ta.finalexam.Activity.MainActivity;
import com.ta.finalexam.Adapter.CustomInfoWindow;
import com.ta.finalexam.Bean.NearbyBean.DataNearby;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.R;
import com.ta.finalexam.api.NearbyResponse;
import com.ta.finalexam.api.Request.NearbyRequest;

import java.util.List;

import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.DebugLog;

import static java.lang.String.valueOf;


/**
 * Created by Veteran Commander on 10/17/2016.
 */

public class FragmentNearby extends HeaderFragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
                                                                GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mMap;

    GoogleApiClient mGoogleApiClient;

    Location mLastLocation;

    CustomInfoWindow newInfo;

    LatLng userpostion;

    double mlong = 0, lat = 0;

    View view;

    int countpost = 0;

    List<DataNearby> dataNearbyList;



//    @BindView(R.id.map)
//    SupportMapFragment mapFragment;

    public static FragmentNearby newInstance() {
        FragmentNearby newFragment = new FragmentNearby();
        return newFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nearby_screen;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        }
    }

    @Override
    protected int getRightIcon() {
        return HeaderOption.RIGHT_NO_OPTION;
    }

    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_BACK;
    }

    @Override
    public String getScreenTitle() {
        return "Nearby";
    }

    @Override
    protected boolean isStartWithLoading() {
        return dataNearbyList == null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

//        if (dataNearbyList.size() != 0){
//
//
//        }





    }

    private Bitmap resizeMarker(int ResID){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ResID);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 40, 70, true);
        return resized;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            mlong = mLastLocation.getLongitude();
        }


        NearbyRequest nearbyRequest = new NearbyRequest(String.valueOf(lat),String.valueOf(mlong));
        nearbyRequest.setRequestCallBack(new ApiObjectCallBack<NearbyResponse>() {
            @Override
            public void onSuccess(NearbyResponse data) {
                initialResponseHandled();
                dataNearbyList = data.data;
                for ( int i = 0; i < dataNearbyList.size(); i++){
                    newInfo = new CustomInfoWindow(getActivity());
                    mMap.setInfoWindowAdapter(newInfo);
                    LatLng postlocation = new LatLng(Double.valueOf(dataNearbyList.get(i).image.lat),
                            Double.valueOf(dataNearbyList.get(i).image._long));
                    MarkerOptions mMarker = new MarkerOptions().position(postlocation);
                    mMarker.icon(BitmapDescriptorFactory.fromBitmap(resizeMarker(R.drawable.map_pin)));
                    mMap.addMarker(mMarker.title(dataNearbyList.get(i).user.username)
                            .snippet(dataNearbyList.get(i).image.caption));

                    userpostion = new LatLng(lat,mlong);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userpostion, 15));
                }
            }

            @Override
            public void onFail(int failCode, String message) {
                DebugLog.e(message);
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

}
