package com.treasurehunt.treasurehunt.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.treasurehunt.treasurehunt.R;
import com.treasurehunt.treasurehunt.helpers.MonthlyManger;
import com.treasurehunt.treasurehunt.helpers.VisualEffects;
import com.treasurehunt.treasurehunt.model.Clue;
import com.treasurehunt.treasurehunt.model.StoryRegion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MonthlyModeFragment extends Fragment {

    DatabaseReference storyRegionRef;
    private GoogleMap mMap;
    public static Button showClue, checkAnswer;
    public static LinearLayout layout;
    public static TextView clueDesc;
    EditText playerAnswer;
    MonthlyManger manager;
    Clue myclue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_monthly_mode, container, false);
        showClue = (Button) root.findViewById(R.id.Show_CLue);
        checkAnswer = (Button) root.findViewById(R.id.checkAnswer);
        layout = (LinearLayout) root.findViewById(R.id.clue_layout);
        clueDesc = (TextView) root.findViewById(R.id.Clue_Desc);
        playerAnswer = (EditText) root.findViewById(R.id.player_answer);
        manager = new MonthlyManger();
        return root;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment == null)
            Log.i("mapfrag", "null");
        else
            Log.i("mapfrag", "not null");

        storyRegionRef = FirebaseDatabase.getInstance().getReference("StoryRegion");

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.getUiSettings().setMapToolbarEnabled(false);
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);

                mMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                getContext(), (hour > 6 && hour < 18) ?
                                        R.raw.mapstyle_retro : R.raw.mapstyle_night));

                readStoryRegion();
            }
        });


        showClue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout.getVisibility() == View.VISIBLE) {
                    layout.setVisibility(View.GONE);
                    showClue.setText("Show Clue");

                } else {

                    manager.getClue(0);
                }
            }
        });
        checkAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerAnswer.getText().length() < 1) {
                    playerAnswer.setError("Required");
                } else {
                    //    Clue.CheckAnswer(playerAnswer.getText().toString(), getActivity(), 0, runnable, handler, x);
                    //      CheckAnswer();
                    manager.checkAnswer(playerAnswer.getText().toString(), getActivity());

                }
            }
        });


    }// end of onViewCreated

//    public void CheckAnswer() {
//        if (myclue.getAnswer().equals(playerAnswer.getText().toString())) {
//            handler.removeCallbacks(runnable);
//            Toast.makeText(getActivity(), "True Answer , Time : " + x, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getActivity(), "Please Try Again", Toast.LENGTH_SHORT).show();
//        }
//    }


//    public void get_specific_clue(int clue_id) {
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference("Clue");
//        reference.child(String.valueOf(clue_id)).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Clue clue = dataSnapshot.getValue(Clue.class);
//                show_View(clue);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }

    public static void show_View(Clue clue) {
        //  Toast.makeText(getActivity(), clue.getAnswer(), Toast.LENGTH_SHORT).show();
        MonthlyManger.x = 0;
        clueDesc.setText(clue.getDescription());
        layout.setVisibility(View.VISIBLE);
        showClue.setText("Hide Clue");
    }

    public void readStoryRegion() {

        try {
            final ArrayList<LatLng> regionBorder = new ArrayList<>();
            storyRegionRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    regionBorder.clear();
                    StoryRegion storyRegion = dataSnapshot.getValue(StoryRegion.class);

                    Log.i("appInfo ID", String.valueOf(storyRegion.getId()));
                    Log.i("appInfo startDate", storyRegion.getStartDate());
                    Log.i("appInfo endDate", storyRegion.getEndDate());

                    for (HashMap<String, Double> hashMap : storyRegion.getRegionBorder()) {
                        double lat = hashMap.get("latitude");
                        double lng = hashMap.get("longitude");

                        LatLng regionBorderLatLng = new LatLng(lat, lng);
                        regionBorder.add(regionBorderLatLng);
                    }
                    VisualEffects.drawStoryRegion(mMap, regionBorder);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("appInfo Error", databaseError.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}