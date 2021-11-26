## 简介
websocket封装

## 使用方法
  //websocket初始化
     OkWebSocket.init(new Config.Builder(getApplicationContext())
                .debug(false)
                .pingInterval(120, TimeUnit.SECONDS)//设定心跳连接
                .build());
  //连接
  OkWebSocket.get(url).subscribe(new Consumer<WebSocketInfo>() {
                    @Override
                    public void accept(WebSocketInfo webSocketInfo) throws Exception {
                        Log.e(TAG, "客户端收到消息：" + webSocketInfo.toString() + "当前线程：" + Thread.currentThread().getName());
                    }
                });
  //发送
  OkWebSocket.send(url,"{\"type\":\"login\",\"device_id\":\"DJ2FAD86D1\"}").subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.e(TAG, "accept  aBoolean："+aBoolean);
                    }
                });

