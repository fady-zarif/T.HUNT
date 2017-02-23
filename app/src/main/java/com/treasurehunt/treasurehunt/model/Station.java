package com.treasurehunt.treasurehunt.model;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fady on 2017-02-15.
 */

public class Station {



    public static void AffectRedReward(int StationId, final int userId) {

        final List<String> list = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Station");

        reference.child("S" + StationId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference reference1 = database.getReference("Reward");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    list.add(snapshot.getValue().toString());
                }
                reference1.child(String.valueOf(userId)).setValue(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
