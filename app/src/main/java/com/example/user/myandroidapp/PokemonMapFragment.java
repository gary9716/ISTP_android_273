package com.example.user.myandroidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by user on 2016/9/2.
 */
public class PokemonMapFragment extends Fragment implements GeoCodingTask.GeoCodingResponse{

    SupportMapFragment mapFragment;
    GoogleMap map;
    GoogleApiClient apiClient;
    LocationRequest locationRequest;

    public static PokemonMapFragment newInstance() {

        Bundle args = new Bundle();

        PokemonMapFragment fragment = new PokemonMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View fragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_map, null);
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;

                    UiSettings mapUISettings = googleMap.getUiSettings();
                    mapUISettings.setZoomControlsEnabled(true);

                    (new GeoCodingTask(PokemonMapFragment.this)).execute("台北市羅斯福路四段一號");

                }
            });

            setHasOptionsMenu(true);
            setMenuVisibility(true);
        }

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.childFragmentContainer, mapFragment)
                .commit();
    }

    @Override
    public void callbackWithGeoCodingResult(LatLng latLng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("NTU")
                .snippet("National Taiwan University");

        map.moveCamera(cameraUpdate);
        map.addMarker(markerOptions);

    }
}
