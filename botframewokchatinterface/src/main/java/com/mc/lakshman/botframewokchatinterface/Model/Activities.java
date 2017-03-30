package com.mc.lakshman.botframewokchatinterface.Model;

/**
 * Created by Lakshman on 3/30/2017.
 */
public class Activities
    {
        private String id;

        private String text;

        private String channelId;

        private Conversation conversation;

        private From from;

        private String type;

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getText ()
        {
            return text;
        }

        public void setText (String text)
        {
            this.text = text;
        }

        public String getChannelId ()
        {
            return channelId;
        }

        public void setChannelId (String channelId)
        {
            this.channelId = channelId;
        }

        public Conversation getConversation ()
        {
            return conversation;
        }

        public void setConversation (Conversation conversation)
        {
            this.conversation = conversation;
        }

        public From getFrom ()
        {
            return from;
        }

        public void setFrom (From from)
        {
            this.from = from;
        }

        public String getType ()
        {
            return type;
        }

        public void setType (String type)
        {
            this.type = type;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [id = "+id+", text = "+text+", channelId = "+channelId+", conversation = "+conversation+", from = "+from+", type = "+type+"]";
        }
    }

