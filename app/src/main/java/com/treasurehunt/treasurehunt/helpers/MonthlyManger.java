package com.treasurehunt.treasurehunt.helpers;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.treasurehunt.treasurehunt.fragments.MonthlyModeFragment;
import com.treasurehunt.treasurehunt.model.Clue;
import com.treasurehunt.treasurehunt.ui.MonthlyMode;

import java.util.Stack;

/**
 * Created by Fady on 2017-02-23.
 */

public class MonthlyManger {
    Clue clue;
    Handler handler = new Handler();
    public static int x = 0;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 1000);
            x++;
        }
    };
    //////////////////////////////////////////////////////////////////////////////////////////
    //All of this should be in a class called Monthly MonthlyManger and this fragment will hold a MonthlyManager Object.
    //nader
    public Stack clueStack = new Stack();
    public boolean finalClue = false;

    public int correctCounter = 0;
    public boolean correctStreak = false;

    //Tony
    double currentScore = 200; //supposed to be in Player Class
    long totalTime = 24668l;   //supposed to be here in Monthly Mode - Calculated by a method that sums all Times.

    //Andre
    public boolean blueBuffOn = false;
    public int clueScore = 200;             // from clue class
    public boolean timeStreak = true;       //from time streak
    public double timeTakenSec = 30.25;     //from timer
/////////////////////////////////////////////////////////////////////////////////////////////


    // Created by Fady 2/2/2017
    public void getClue(int ClueId) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Clue");
        reference.child(String.valueOf(ClueId)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clue = dataSnapshot.getValue(Clue.class);
                String x = clue.getDescription();
                MonthlyModeFragment.show_View(clue);
                runnable.run();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

     // Created by Fady 2/2/2017
    public void checkAnswer(String Answer, Context context) {
        if (clue.getAnswer().toString().equals(Answer)) {
            {
                Toast.makeText(context, "True Answer Time Is " + x, Toast.LENGTH_SHORT).show();
                handler.removeCallbacks(runnable);
            }
        } else {
            Toast.makeText(context, "wrong answer", Toast.LENGTH_SHORT).show();
        }
    }

    //created by Nader on 3/2/2017
    public void CorrectStreakCounter(String state)
    {


        //Everytime a clue is solved it adds 1 to the counter.
        //When the counter is at 3 which is the maximum, the streak is TRUE
        //if a clue isn't solved or salved incorrectly, the counter goes back to zero

        switch (state)
        {
            case "correct":
                correctCounter++;
                break;
            case "false":
                correctCounter = 0;
                break;
            case "timeOut":
                correctCounter--;
                break;
            default:
                break;
        }//switch

        return;
    }//CheckCorrectStreak()


    //created by Tony Magdy 5/2/2017
    public boolean CheckScoreThreshold(int x)
    {
        if(currentScore>=x)
        {
            return true;
        }

        return false;
    }//CheckScoreThreshold() end

    //andre 15/2/2017
    public void BlueBuff()
    {
        if (CheckScoreThreshold(300))
        {
            blueBuffOn = true;
        }
    }
    //andre 22/2/2017
    public double CalculateClueReward()
    {
        double scoreEnd = 0;
        scoreEnd = clueScore + (1/300) * timeTakenSec;

        if(correctStreak)
        {
            scoreEnd += 100;
        }

        if(timeStreak)
        {
            scoreEnd += 100;
        }

        return scoreEnd;
    }

    //created by Nader on 3/2/2017
    public void CheckFinalClue()
    {
        //checks if the current clue is the final one
        //Call this method after popping the stack of clues.
        //If the stack is empty then the clue popped was the last one.

        if (clueStack.isEmpty())
            finalClue = true;

        return;
    }//CheckFinalClue()


    //created by Nader on 3/2/2017
    public void CheckCorrectStreak()
    {
        //Everytime a clue is solved it adds 1 to the counter.
        //When the counter is at 3 which is the maximum, the streak is TRUE
        //if a clue isn't solved or salved incorrectly, the counter goes back to zero

        if(correctCounter == 3)
            correctStreak = true;

        return;
    }//CheckCorrectStreak()

    //Created by Tony 15/2/2017
    public void GreenBuff() {
        if (CheckScoreThreshold(300)) {
            totalTime = totalTime - 15;
        }
    }//GreenBuff() method end

}
