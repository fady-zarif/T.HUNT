package com.treasurehunt.treasurehunt.model;

/**
 * Created by Tony on 13/12/2016.
 */

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Clue {
    private int cluID;
    private String clueName;
    private String description;
    private String answer;
    private String hint;
    private double baseScore;
    private Location clueLatLng;
    private int treasurePoints;

    public Clue() {
    }

    public Clue(int cluID, String clueName, String description, String answer, String hint, double baseScore, Location clueLatLng) {
        this.cluID = cluID;
        this.clueName = clueName;
        this.description = description;
        this.answer = answer;
        this.hint = hint;
        this.baseScore = baseScore;
        this.clueLatLng = clueLatLng;
    }

    public int getCluID() {
        return cluID;
    }

    public String getClueName() {
        return clueName;
    }

    public String getDescription() {
        return description;
    }

    public String getAnswer() {
        return answer;
    }

    public String getHint() {
        return hint;
    }

    public double getBaseScore() {
        return baseScore;
    }

    public Location getClueLatLng() {
        return clueLatLng;
    }

    public static void CheckAnswer(final String Answer, final Context context, int Clue_id, final Runnable runnable, final Handler handler, final int x) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Clue");
        reference.child(String.valueOf(Clue_id)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Clue clue = dataSnapshot.getValue(Clue.class);
                if (clue.getAnswer().equals(Answer)) {
                    handler.removeCallbacks(runnable);
                    Toast.makeText(context, "True Answer , Time : " + x, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, " Please Try Again ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static boolean checkAnswer5(String Answer, Clue clue) {
        if (Answer.equals(clue.getAnswer().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public void ShowHint() {

    }

    public void GiveReward() {

    }

//
//    public static void addclue() {

}