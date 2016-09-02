package com.example.user.myandroidapp;

import android.os.AsyncTask;

import com.example.user.myandroidapp.model.Utils;
import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;

/**
 * Created by user on 2016/9/2.
 */
public class GeoCodingTask extends AsyncTask<String, Void, double[]> {

    WeakReference<GeoCodingResponse> geoCodingResponseWeakReference;

    public GeoCodingTask(GeoCodingResponse geoCodingResponse) {
        geoCodingResponseWeakReference = new WeakReference<GeoCodingResponse>(geoCodingResponse);
    }

    @Override
    protected double[] doInBackground(String... params) {

        double[] latlng = Utils.getLatLngFromAddress(params[0]);

        return latlng;
    }

    @Override
    protected void onPostExecute(double[] latlng) {
        super.onPostExecute(latlng);
        if(latlng != null) {
            LatLng result = new LatLng(latlng[0], latlng[1]);
            GeoCodingResponse response = geoCodingResponseWeakReference.get();
            if(response != null) {
                response.callbackWithGeoCodingResult(result);
            }
        }
    }

    public interface GeoCodingResponse {

        void callbackWithGeoCodingResult(LatLng latLng);

    }

}
