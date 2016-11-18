package com.ta.finalexam.Adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.ta.finalexam.R;

/**
 * Created by Veteran Commander on 9/21/2016.
 */
public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    Activity activity;
    TextView title;
    TextView snippet;

    public CustomInfoWindow(Activity activity) {
        this.activity = activity;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        View content = activity.getLayoutInflater().inflate(R.layout.detail_maker,null);
        title = (TextView)content.findViewById(R.id.tvtitle);
        snippet = (TextView)content.findViewById(R.id.tvsnippet);
        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());
        return content;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
