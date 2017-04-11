package com.demoapp.messenger.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class ConnectXmpp extends Service {

    private String userName;
    private String passWord;
    private MyXMPP xmpp = new MyXMPP();

    public ConnectXmpp() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new LocalBinder<ConnectXmpp>(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
            if(intent != null){
            try {
                userName = intent.getStringExtra("user");
                passWord = intent.getStringExtra("pwd");
            }catch (Exception e){}
                String  code=intent.getStringExtra("code");
            if(code.equals("0")){
                xmpp.init(userName, passWord);
            xmpp.connectConnection();
            }else if(code.equals("1"))
                xmpp.disconnectConnection();
            else if(code.equals("2"))
                xmpp.joinChatRoom();
                else
                    xmpp.sendMsg();
        }

    return 0;
    }

    @Override
    public void onDestroy() {
        xmpp.disconnectConnection();
        super.onDestroy();
    }









}
