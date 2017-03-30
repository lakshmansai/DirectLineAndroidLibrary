package com.mc.lakshman.botframewokchatinterface.Model;

/**
 * Created by Lakshman on 3/30/2017.
 */

public class Conversation {
    private String id;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+"]";
    }
}

