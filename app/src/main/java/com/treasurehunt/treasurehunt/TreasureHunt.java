package com.treasurehunt.treasurehunt;

import android.support.multidex.MultiDexApplication;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;


public class TreasureHunt extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        //show data traffic throw firebase in the logcat
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}