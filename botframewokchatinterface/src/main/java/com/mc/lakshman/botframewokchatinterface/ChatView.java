package com.mc.lakshman.botframewokchatinterface;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mc.lakshman.botframewokchatinterface.Model.Activities;
import com.mc.lakshman.botframewokchatinterface.Model.Chat;
import com.mc.lakshman.botframewokchatinterface.Model.ConversationInit;
import com.mc.lakshman.botframewokchatinterface.Model.From;
import com.mc.lakshman.botframewokchatinterface.Model.SendMessage;
import com.mc.lakshman.botframewokchatinterface.chatadapter.ChatAdapter;
import com.mc.lakshman.botframewokchatinterface.chatadapter.ChatMessage;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mc.lakshman.botframewokchatinterface.DirectlineAPI.BASE_URL;

/**
 * Created by Lakshman on 3/30/2017.
 */

public class ChatView extends LinearLayout implements MessageCallback {
    private EditText messageET;
    private ListView messagesContainer;
    private ImageButton sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    public static String directlinePrimaryKey, botName, conversationId;
    DirectlineAPI directlineAPI;
    Call<From> sendMessage;
    ConversationInit conversationInit;
    Activity activityContext;
    From from;
    View view;
    Chat chat;
    public ChatView(Activity context, String botName, String directlinePrimaryKey) {
        super(context);
        activityContext = context;
        this.botName = botName;
        this.directlinePrimaryKey = directlinePrimaryKey;
        from = new From();
        Random rnd = new Random();
        int n =  rnd.nextInt(900000);
        from.setId("user"+n);
        initialize(context);
    }

    private void sendMessageToBot(String message) {
        sendMessage = directlineAPI.sendChatMessage(conversationId, new SendMessage("message",from,message));
        sendMessage.enqueue(new Callback<From>() {
            @Override
            public void onResponse(Call<From> call, retrofit2.Response<From> response) {
                System.out.println("Response" + response.body());
            }
            @Override
            public void onFailure(Call<From> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initStreaming() {
        try {
            WebSocketRecieve webSocketRecieve = new WebSocketRecieve(conversationInit.getStreamUrl(), ChatView.this);
            webSocketRecieve.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize(Context context) {
        view=inflate(context, R.layout.chatview, this);
        init();//build client initChat start message
    }

    public void buildDirectlineApi() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                String basicAuth = "Bearer " + directlinePrimaryKey;
                Request request = original.newBuilder()
                        .header("Authorization", basicAuth.trim())
                        .header("Content-Type", "application/json")
                        .header("Accept", " application/json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        directlineAPI = retrofit.create(DirectlineAPI.class);
        Log.d("ChatView", "Created");
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void sayHelloToClient() {

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hello");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);

        adapter = new ChatAdapter(activityContext, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for (int i = 0; i < chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }

    /*
    Add the bot response to chat window
     */
    private void AddResponseToChat(String botResponse) {
        ChatMessage message = new ChatMessage();
        //message.setId(2);
        message.setMe(false);
        message.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        message.setMessage(botResponse);
        displayMessage(message);
    }

    @Override
    public void messageRecieved(String s) {
     //   AddResponseToChat(s);
        Gson gson=new Gson();
        chat=gson.fromJson(s, Chat.class);
        if(chat!=null && chat.getWatermark()!=null) {
            System.out.println(chat.getActivities().get(0).getText());
            activityContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AddResponseToChat(chat.getActivities().get(0).getText());
                }
            });

        }
    }
    public void init(){
        initChat();
        buildDirectlineApi();
        Call<ConversationInit> call = directlineAPI.getConversationId();
        call.enqueue(new Callback<ConversationInit>() {
            @Override
            public void onResponse(Call<ConversationInit> call, retrofit2.Response<ConversationInit> response) {
                System.out.println("Conservation ID" + response.body().getConversationId());
                conversationId = response.body().getConversationId();
                conversationInit = response.body();
                initStreaming();
            }

            @Override
            public void onFailure(Call<ConversationInit> call, Throwable t) {
                System.out.println("Failure");
            }
        });
    }
    public void initChat(){

        messagesContainer = (ListView) view.findViewById(R.id.messagesContainer);
        messageET = (EditText) view.findViewById(R.id.messageEdit);
        sendBtn = (ImageButton) view.findViewById(R.id.chatSendButton);
        sayHelloToClient();
        sendBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);

                messageET.setText("");
                displayMessage(chatMessage);

                if (conversationId != "") {
                    sendMessageToBot(messageText);
                }
            }
        });
    }
}

