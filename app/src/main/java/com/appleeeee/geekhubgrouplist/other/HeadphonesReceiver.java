package com.appleeeee.geekhubgrouplist.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.appleeeee.geekhubgrouplist.R;

public class HeadphonesReceiver extends BroadcastReceiver {

    private boolean isCreated = false;
    private static final String STATE = "state";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isCreated) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra(STATE, -1);
                switch (state) {
                    case 0:
                        Toast.makeText(context, R.string.headset_is_unplagged, Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(context,
                                R.string.headset_is_plugged, Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(context, R.string.no_idea, Toast.LENGTH_SHORT).show();
                }
            }
        }
        isCreated = true;
    }
}