package com.mc.lakshman.botframewokchatinterface.Model;

/**
 * Created by Lakshman on 3/30/2017.
 */

public class SendMessage {
    String type,text;
    From from;

    public SendMessage(String type,  From from,String text) {
        this.type = type;
        this.text = text;
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
