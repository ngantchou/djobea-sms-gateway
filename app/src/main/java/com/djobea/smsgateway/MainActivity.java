
package com.djobea.smsgateway;

import android.Manifest;
import android.content.Intent;
import android.os.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.app.role.RoleManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        ActivityCompat.requestPermissions(this, new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.POST_NOTIFICATIONS
        }, 1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            RoleManager rm = getSystemService(RoleManager.class);
            if (!rm.isRoleHeld(RoleManager.ROLE_SMS)) {
                startActivityForResult(rm.createRequestRoleIntent(RoleManager.ROLE_SMS), 2);
            }
        }

        ContextCompat.startForegroundService(
            this, new Intent(this, SMSGatewayService.class));

        finish();
    }
}
