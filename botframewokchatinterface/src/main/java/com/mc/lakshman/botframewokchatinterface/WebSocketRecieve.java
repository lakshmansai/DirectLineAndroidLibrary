package com.mc.lakshman.botframewokchatinterface;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by Lakshman on 3/30/2017.
 */

public class WebSocketRecieve extends WebSocketListener {
    String streamURL;
    MessageCallback messageCallback;
    WebSocketRecieve(String streamURL,MessageCallback messageCallback){
        this.streamURL=streamURL;
        this.messageCallback=messageCallback;
    }
    public void run() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0,  TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url(streamURL)
                .build();
        client.newWebSocket(request, this);

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        client.dispatcher().executorService().shutdown();
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d(this.getClass().getSimpleName(),text);
        messageCallback.messageRecieved(text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
      Log.d(this.getClass().getSimpleName(),bytes.hex());
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        System.out.println((webSocket.toString() + code + reason));
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }
}
