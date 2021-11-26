package com.sesxh.okwebsocketapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sesxh.okwebsocket.OkWebSocket;
import com.sesxh.okwebsocket.WebSocketInfo;
import com.sesxh.okwebsocket.config.Config;

import io.reactivex.functions.Consumer;
import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="OkHttp WebSocket 测试";

    private WebSocket mWebSocket;
    private String url="ws://47.93.97.68:2348";
    private StringBuilder sb=new StringBuilder();
    private WebSocket clientSocket;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkWebSocket.init(new Config.Builder(getApplicationContext())
                .debug(false)
                .build());

        findViewById(R.id.setup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressWarnings("checkResult")
            public void onClick(View v) {
                OkWebSocket.get(url).subscribe(new Consumer<WebSocketInfo>() {
                    @Override
                    public void accept(WebSocketInfo webSocketInfo) throws Exception {
                        Log.e(TAG, "客户端收到消息：" + webSocketInfo.toString() + "当前线程：" + Thread.currentThread().getName());
                    }
                });
            }
        });

        findViewById(R.id.client_btn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id++;
                        OkWebSocket.send(url,"{\"type\":\"login\",\"device_id\":\"DJ2FAD86D1\"}").subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                Log.e(TAG, "accept  aBoolean："+aBoolean);
                            }
                        });
                    }
                }
        );

    }
}