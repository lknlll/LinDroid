package com.example.lindroidcode.websocket;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lindroidcode.R;
import com.example.lindroidcode.databinding.ActivityWebSocketBinding;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStart;
    private TextView tvOutput;
    private WebSocket mSocket;
    private ActivityWebSocketBinding mActivityWebSocketBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityWebSocketBinding = ActivityWebSocketBinding.inflate(getLayoutInflater());
        View view = mActivityWebSocketBinding.getRoot();
        setContentView(view);
        btnStart = mActivityWebSocketBinding.start;
        tvOutput = mActivityWebSocketBinding.output;
        btnStart.setOnClickListener(this);
    }

    private void start() {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                .build();

        Request request = new Request.Builder().url("wss://echo.websocket.org").build();
        EchoWebSocketListener socketListener = new EchoWebSocketListener();
        mOkHttpClient.newWebSocket(request, socketListener);
        mOkHttpClient.dispatcher().executorService().shutdown();
    }

    /**
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start://开始连接
                start();
                break;
        }
    }


    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            mSocket = webSocket;
            String openid = "1";
            //连接成功后，发送登录信息
            String message = "{\"type\":\"login\",\"user_id\":\""+openid+"\"}";
            mSocket.send(message);
            mSocket.send("welcome");
            mSocket.send(ByteString.decodeHex("adef"));
            mSocket.close(1000, "再见");
            output("连接成功！");
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            output("receive bytes:" + bytes.hex());
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            output("receive text:" + text);
            //收到服务器端发送来的信息后，每隔25秒发送一次心跳包
            final String message = "{\"type\":\"heartbeat\",\"user_id\":\"heartbeat\"}";
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mSocket.send(message);
                }
            },25000);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            output("closed:" + reason);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            output("closing:" + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            output("failure:" + t.getMessage());
        }
    }

    private void output(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvOutput.setText(tvOutput.getText().toString() + "\n\n" + text);
            }
        });
    }
}