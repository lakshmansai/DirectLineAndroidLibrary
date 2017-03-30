package com.mc.lakshman.botframewokchatinterface;

import com.mc.lakshman.botframewokchatinterface.Model.Activities;
import com.mc.lakshman.botframewokchatinterface.Model.ConversationInit;
import com.mc.lakshman.botframewokchatinterface.Model.From;
import com.mc.lakshman.botframewokchatinterface.Model.SendMessage;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Lakshman on 3/30/2017.
 */

public interface DirectlineAPI {
    String BASE_URL="https://directline.botframework.com/v3/directline/";

  //  ("Authorization", basicAuth)
    @POST("conversations")
    Call<ConversationInit> getConversationId();
    @POST("conversations/{id}/activities")
    Call<From> sendChatMessage(@Path("id")String conversationId,@Body SendMessage sendMessage);

}
