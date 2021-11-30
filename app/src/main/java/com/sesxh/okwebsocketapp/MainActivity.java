package com.sesxh.okwebsocketapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sesxh.okwebsocket.OkWebSocket;
import com.sesxh.okwebsocket.WebSocketInfo;
import com.sesxh.okwebsocket.annotation.WebSocketStatus;
import com.sesxh.okwebsocket.config.Config;
import com.sesxh.okwebsocket.log.Logger;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="OkHttp WebSocket 测试";

    private String url="ws://47.93.97.68:2348";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkWebSocket.init(new Config.Builder(getApplicationContext())
                .debug(false)
                .pingInterval(10, TimeUnit.SECONDS)
                .reconnectInterval(10, TimeUnit.SECONDS)
                .reconnectInterval(10, TimeUnit.SECONDS)
                .build());

        findViewById(R.id.setup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressWarnings("checkResult")
            public void onClick(View v) {
                websocketConnect();
            }
        });

        findViewById(R.id.client_btn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isSocketConn){
                            return;
                        }
                        OkWebSocket.asyncSend(url,"{\"type\":\"qrcode\",\"device_id\":\"DJEF9BD9A12\"}").subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull Boolean aBoolean) {
                                Log.e(TAG, "asyncSend：" + aBoolean);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e(TAG, "asyncSend：" + e.toString());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                    }
                }
        );
    }



    private boolean isSocketConn = false;

    @SuppressLint("CheckResult")
    private void websocketConnect() {
        OkWebSocket.get(url).subscribe(new Consumer<WebSocketInfo>() {
            @Override
            public void accept(WebSocketInfo webSocketInfo) throws Exception {
                Log.e(TAG, "客户端收到消息：" + webSocketInfo.toString());
                switch(webSocketInfo.getStatus()){
                    case WebSocketStatus.STATUS_CONNECTED://连接成功
                        isSocketConn = true;
                        break;
                    case WebSocketStatus.STATUS_RE_CONNECTED://重连成功
                        isSocketConn = true;
                        break;
                    case WebSocketStatus.STATUS_ON_CLOSED://断开连接
                        isSocketConn = false;
                        break;
                    case WebSocketStatus.STATUS_ON_FAILURE://连接异常
                        isSocketConn = false;
                        break;
                    case WebSocketStatus.STATUS_ON_REPLY://收到服务端消息

                        break;
                    default:
                        break;
                }

            }
        });
    }
}