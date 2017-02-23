package com.treasurehunt.treasurehunt.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kerolos on 07/12/2016.
 */

public class StoryRegion {

    private int id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private ArrayList<HashMap<String, Double>> regionBorder;

    public StoryRegion(int id, String name, String description, String startDate, String endDate, ArrayList<HashMap<String, Double>> regionBorder) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.regionBorder = regionBorder;
    }


    public StoryRegion() {
    }

    public String getStartDate() {
        return startDate;
    }

    public String getDescription() {
        return description;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ArrayList<HashMap<String, Double>> getRegionBorder() {
        return regionBorder;
    }
}


//    public void updateStoryRegion() {
//        int id = 1;
//        String startDate = "20/10/1994";
//        String endDate = "20/10/1994";
//
//        HashMap<String, Double> h1 = new HashMap<>();
//        h1.put("latitude", 30.119942);
//        h1.put("longitude", 31.605011);
//
//        HashMap<String, Double> h2 = new HashMap<>();
//        h2.put("latitude", 30.118479);
//        h2.put("longitude", 31.605439);
//
//        HashMap<String, Double> h3 = new HashMap<>();
//        h3.put("latitude", 30.119750);
//        h3.put("longitude", 31.608186);
//
//        ArrayList<HashMap<String, Double>> latLngs = new ArrayList<>();
//        latLngs.add(h1);
//        latLngs.add(h2);
//        latLngs.add(h3);
//
//        StoryRegion storyRegion = new StoryRegion(id, startDate, endDate, latLngs);
//        firebase.child("StoryRegion").setValue(storyRegion);
//    }

