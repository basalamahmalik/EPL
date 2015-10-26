package epl.app.net.basalamah.malik.epl_1.BroadcastReceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.parse.ParsePushBroadcastReceiver;

import epl.app.net.basalamah.malik.epl_1.MainActivity;
import epl.app.net.basalamah.malik.epl_1.R;

/**
 * Created by Malik on 19-Oct-15.
 */
public class ParseBroadcast extends ParsePushBroadcastReceiver {

    final String DATA = "com.parse.Data";
    final String notificationTitle = "EPL";
    final String notificationContent ="NEW DATA";

    @Override

    public void onReceive(Context context, Intent intent) {

            String data = intent.getExtras().getString(DATA);

            Intent resultIntent = new Intent(context, MainActivity.class);

            resultIntent.putExtra("PARSE_NOTIFICATION_DATA",data);
            PendingIntent sender = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);




            //Customize your notification - sample code
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_logo)
                            .setContentTitle(notificationTitle)
                            .setContentText(notificationContent)
                            .setContentIntent(sender)
                            .setAutoCancel(true);


            int mNotificationId = 001;
            NotificationManager mNotifyMgr =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotifyMgr.notify(mNotificationId, builder.build());




    }
}
