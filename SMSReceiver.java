package com.example.smsforwarder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras(); 
        SmsMessage[] msgs;
        String sender;
        String message = "";

        if (bundle != null) {
            try {
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];

                for (int i = 0; i < msgs.length; i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sender = msgs[i].getOriginatingAddress();
                    message = msgs[i].getMessageBody();
                    
                    // Send to the forwarding number
                    String forwardToNumber = "+1234567890"; // The number you want to forward SMS to
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(forwardToNumber, null, "From: " + sender + "\nMessage: " + message, null, null);
                    
                    Toast.makeText(context, "SMS forwarded to: " + forwardToNumber, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
