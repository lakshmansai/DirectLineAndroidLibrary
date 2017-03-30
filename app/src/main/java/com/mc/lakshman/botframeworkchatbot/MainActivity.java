package com.mc.lakshman.botframeworkchatbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mc.lakshman.botframewokchatinterface.ChatView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String botName="DocBot";
        final String directlinePrimaryKey="DLfYFUt_9nM.cwA.6Zg.gg0rVd6ZKtZeCWfSw1XmjqmSpzn-KT-7K42oqlYAtjk";
        View v = new ChatView(this,botName,directlinePrimaryKey);
        setContentView(v);
    }
}
