package com.demoapp.messenger.messenger;

        import android.app.Notification;
        import android.app.NotificationManager;
        import android.content.ComponentName;
        import android.content.Context;
        import android.content.Intent;
        import android.content.ServiceConnection;
        import android.graphics.Color;
        import android.os.Build;
        import android.os.IBinder;
        import android.support.annotation.RequiresApi;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.SpannableString;
        import android.text.style.BackgroundColorSpan;
        import android.text.style.ForegroundColorSpan;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.EditText;
        import com.demoapp.messenger.R;

        import org.jivesoftware.smack.PacketListener;
        import org.jivesoftware.smack.XMPPConnection;
        import org.jivesoftware.smack.filter.MessageTypeFilter;
        import org.jivesoftware.smack.filter.PacketFilter;
        import org.jivesoftware.smack.packet.Message;
        import org.jivesoftware.smack.packet.Packet;
        import org.jivesoftware.smack.util.StringUtils;

        import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private ConnectXmpp mService;
    private View view;
    private boolean mBounded;
    private final ServiceConnection mConnection = new ServiceConnection() {

        @SuppressWarnings("unchecked")
        @Override
        public void onServiceConnected(final ComponentName name,
                                       final IBinder service) {
            mService = ((LocalBinder<ConnectXmpp>) service).getService();
            mBounded = true;
            Log.d(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(final ComponentName name) {
            mService = null;
            mBounded = false;
            Log.d(TAG, "onServiceDisconnected");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Click Handler for Login Button
    public void onClickLoginBtn(View view)
    {
       try {
           EditText userId = (EditText) findViewById(R.id.txtUser);
           EditText userPwd = (EditText) findViewById(R.id.txtPwd);
           String userName = userId.getText().toString();
           String passWord = userPwd.getText().toString();
           Intent intent = new Intent(getBaseContext(),ConnectXmpp.class );
           intent.putExtra("user",userName);
           intent.putExtra("pwd",passWord);
           intent.putExtra("code","0");
           startService(intent);



           //mService.connectConnection(intent);
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
    }

    public void disconnect(View view) {
        Intent intent = new Intent(getBaseContext(),ConnectXmpp.class );
        intent.putExtra("code","1");
        startService(intent);
    }

    public void joinchatroom(View view) {
        Intent intent = new Intent(getBaseContext(),ConnectXmpp.class );
        intent.putExtra("code","2");
        startService(intent);



    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void onEvent(Broadcast event){
        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notify = new Notification.Builder
                    (getApplicationContext()).setContentTitle("XMPP Notify").setContentText(event.getMessage()).
                    setContentTitle("Testing").setSmallIcon(R.drawable.common_signin_btn_icon_dark).build();
        }

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);



    }

    @Override
    public void onResume(){

        super.onResume();
        EventBus.getDefault().registerSticky(this);
        // Set title bar
    }


    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    public void sendmsg(View view) {

        Intent intent = new Intent(getBaseContext(),ConnectXmpp.class );
        intent.putExtra("code","3");
        startService(intent);


    }
}
