
package com.djobea.smsgateway;

import android.app.*;
import android.content.*;
import android.os.*;
import android.telephony.*;
import androidx.core.app.NotificationCompat;
import com.google.firebase.firestore.*;

public class SMSGatewayService extends Service {

    FirebaseFirestore db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, notification());
        db.collection("sms_queue")
          .whereEqualTo("status","pending")
          .addSnapshotListener((snap,e)->{
            if(snap==null) return;
            for(DocumentChange d:snap.getDocumentChanges())
                if(d.getType()==DocumentChange.Type.ADDED)
                    send(d.getDocument());
          });
        return START_STICKY;
    }

    void send(DocumentSnapshot d){
        String phone=d.getString("to");
        String msg=d.getString("body");
        SmsManager.getDefault().sendTextMessage(phone,null,msg,null,null);
        db.collection("sms_queue").document(d.getId())
          .update("status","sent");
    }

    Notification notification(){
        NotificationChannel c=new NotificationChannel(
            "djobea","Djobea Gateway",
            NotificationManager.IMPORTANCE_LOW);

        getSystemService(NotificationManager.class)
          .createNotificationChannel(c);

        return new NotificationCompat.Builder(this,"djobea")
          .setContentTitle("Djobea SMS Gateway")
          .setContentText("Listening for Firestore messages")
          .setSmallIcon(android.R.drawable.sym_action_email)
          .build();
    }

    @Override public IBinder onBind(Intent i){return null;}
}
