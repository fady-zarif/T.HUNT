package com.treasurehunt.treasurehunt.helpers;

import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolygonOptions;
import java.util.ArrayList;

/**
 * Created by kerolos on 20/12/2016.
 */

public class VisualEffects {

    static public void drawStoryRegion(GoogleMap mMap, ArrayList<LatLng> regionBorder) {
        mMap.clear();

        LatLngBounds.Builder bld = new LatLngBounds.Builder();
        //draw a circle
        if (regionBorder.size() == 1) {
            mMap.addCircle(new CircleOptions()
                    .center(regionBorder.get(0))
                    .radius(600)
                    .strokeWidth(Color.argb(150, 20, 252, 252))
                    .strokeWidth(2)
                    .fillColor(Color.argb(35, 102, 255, 255)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(regionBorder.get(0), 15));

        }
        //if two points ware found draw a circle at it's center
        else if (regionBorder.size() == 2) {
            bld.include(regionBorder.get(0));
            bld.include(regionBorder.get(1));

            LatLngBounds bounds = bld.build();

            mMap.addCircle(new CircleOptions()
                    .center(bounds.getCenter())
                    .radius(600)
                    .strokeWidth(Color.argb(150, 20, 252, 252))
                    .strokeWidth(2)
                    .fillColor(Color.argb(35, 102, 255, 255)));

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15));
        } else if (regionBorder.size() > 2) {

            for (int i = 0; i < regionBorder.size(); i++) {
                bld.include(regionBorder.get(i));
            }

            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.addAll(regionBorder);
            polygonOptions.strokeColor(Color.argb(150, 20, 252, 252));
            polygonOptions.strokeWidth(2);
            polygonOptions.fillColor(Color.argb(35, 102, 255, 255));
            mMap.addPolygon(polygonOptions);
            LatLngBounds bounds = bld.build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15));
        }
    }

    static public void drawClueRegion(GoogleMap mMap, LatLng clueLatLng, double radius) {
        mMap.addCircle(new CircleOptions()
                .center(clueLatLng)
                .radius(radius)
                .strokeWidth(Color.argb(150, 20, 252, 252))
                .strokeWidth(2)
                .fillColor(Color.argb(35, 102, 255, 255)));
    }
}