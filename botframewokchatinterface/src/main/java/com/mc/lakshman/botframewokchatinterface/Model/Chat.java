package com.mc.lakshman.botframewokchatinterface.Model;

import java.util.ArrayList;

/**
 * Created by Lakshman on 3/30/2017.
 */

public class Chat {
    private String watermark;

    private ArrayList<Activities> activities;

    public String getWatermark ()
    {
        return watermark;
    }

    public void setWatermark (String watermark)
    {
        this.watermark = watermark;
    }

    public ArrayList<Activities> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activities> activities) {
        this.activities = activities;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [watermark = "+watermark+", activities = "+activities+"]";
    }
}
